package ru.cft.template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.cft.template.dto.CreateInvoiceRequest;
import ru.cft.template.dto.InvoiceDto;
import ru.cft.template.entity.Invoice;
import ru.cft.template.entity.Transaction;
import ru.cft.template.entity.User;
import ru.cft.template.entity.Wallet;
import ru.cft.template.enums.InvoiceStatus;
import ru.cft.template.enums.TransStatus;
import ru.cft.template.enums.TransType;
import ru.cft.template.exception.*;
import ru.cft.template.mapper.InvoiceMapper;
import ru.cft.template.repository.InvoiceRepository;
import ru.cft.template.repository.TransactionRepository;
import ru.cft.template.repository.UserRepository;
import ru.cft.template.repository.WalletRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public InvoiceDto createInvoice(Authentication authentication, CreateInvoiceRequest body) {
        User sender = userServiceImpl.getUserByAuthentication(authentication);

        User receiver = userRepository.findByMobilePhone(body.mobilePhone())
                .orElseThrow(() -> new UserNotFoundException("Получатель не найден."));

        if (sender.getId().equals(receiver.getId())) {
            throw new SelfInvoiceException("Вы не можете выставить счет самому себе.");
        }

        Invoice outgoingInvoice = new Invoice();
        outgoingInvoice.setAmount(body.amount());
        outgoingInvoice.setComment(body.comment());
        outgoingInvoice.setIssueDate(LocalDateTime.now());
        outgoingInvoice.setStatus(InvoiceStatus.NOTPAID);
        outgoingInvoice.setSender(sender);
        outgoingInvoice.setReceiver(receiver);

        outgoingInvoice = invoiceRepository.save(outgoingInvoice);


        return InvoiceMapper.toDto(outgoingInvoice);
    }
    public InvoiceDto payInvoice(Authentication authentication, UUID invoiceId) {
        User payer = userServiceImpl.getUserByAuthentication(authentication);
        Wallet payerWallet = payer.getWallet();

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Счет не найден."));

        if (invoice.getStatus() == InvoiceStatus.PAID) {
            throw new IllegalArgumentException("Счет уже оплачен.");
        }

        if (payerWallet.getAmount() < invoice.getAmount()) {
            throw new InsufficientFundsException("Недостаточно средств на кошельке плательщика.");
        }

        User receiver = invoice.getReceiver();
        Wallet receiverWallet = receiver.getWallet();

        payerWallet.setAmount(payerWallet.getAmount() - invoice.getAmount());
        receiverWallet.setAmount(receiverWallet.getAmount() + invoice.getAmount());

        invoice.setStatus(InvoiceStatus.PAID);

        walletRepository.save(payerWallet);
        walletRepository.save(receiverWallet);
        invoiceRepository.save(invoice);

        Transaction transaction = new Transaction();
        transaction.setAmount(invoice.getAmount());
        transaction.setReceiverPhone(Long.valueOf(receiver.getMobilePhone()));
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setSenderWallet(payerWallet);
        transaction.setReceiverWallet(receiverWallet);
        transaction.setStatus(TransStatus.SUCCESSFUL);
        transaction.setType(TransType.OUTGOING);

        transactionRepository.save(transaction);

        return InvoiceMapper.toDto(invoice);
    }

    @Override
    public InvoiceDto cancelInvoice(Authentication authentication, UUID invoiceId) {
        User sender = userServiceImpl.getUserByAuthentication(authentication);

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Счет не найден."));

        if (!invoice.getSender().getId().equals(sender.getId())) {
            throw new PermissionDeniedException("У вас нет разрешения на отмену этого счета.");
        }

        invoice.setStatus(InvoiceStatus.CANCELED);

        invoice = invoiceRepository.save(invoice);

        return InvoiceMapper.toDto(invoice);
    }

    @Override
    public InvoiceDto getInvoiceInfo(UUID invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Счет не найден."));
        return InvoiceMapper.toDto(invoice);
    }

    @Override
    public List<InvoiceDto> getAllSentInvoices(Authentication authentication) {
        User sender = userServiceImpl.getUserByAuthentication(authentication);
        List<Invoice> invoices = invoiceRepository.findBySender(sender);
        return invoices.stream().map(InvoiceMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDto> getAllReceivedInvoices(Authentication authentication) {
        User receiver = userServiceImpl.getUserByAuthentication(authentication);
        List<Invoice> invoices = invoiceRepository.findByReceiver(receiver);
        return invoices.stream().map(InvoiceMapper::toDto).collect(Collectors.toList());
    }


}
