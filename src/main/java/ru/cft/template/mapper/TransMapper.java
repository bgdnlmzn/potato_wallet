package ru.cft.template.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.dto.TransDto;
import ru.cft.template.dto.TransferViaPhoneRequest;
import ru.cft.template.dto.TransferViaWalletRequest;
import ru.cft.template.entity.Transaction;
import ru.cft.template.entity.Wallet;
import ru.cft.template.enums.TransStatus;

import java.time.LocalDateTime;

@Component
public class TransMapper {
    public static Transaction mapTransRequest(TransferViaPhoneRequest body, Wallet senderWallet, Wallet receiverWallet){
        Transaction trans = new Transaction();
        trans.setAmount(body.amount());
        trans.setReceiverPhone(Long.valueOf(body.mobilePhone()));
        trans.setTransactionDate(LocalDateTime.now());
        trans.setSenderWallet(senderWallet);
        trans.setReceiverWallet(receiverWallet);
        trans.setStatus(TransStatus.FAILED);
        return trans;
    }
    public static Transaction mapTransWalletRequest(TransferViaWalletRequest body, Wallet senderWallet, Wallet receiverWallet){
        Transaction trans = new Transaction();
        trans.setAmount(body.amount());
        //trans.setReceiverPhone(Long.valueOf(body.mobilePhone()));
        trans.setTransactionDate(LocalDateTime.now());
        trans.setSenderWallet(senderWallet);
        trans.setReceiverWallet(receiverWallet);
        trans.setStatus(TransStatus.FAILED);
        return trans;
    }

    public static TransDto mapTransDto(Transaction trans){
        return new TransDto(
                trans.getId(),
                trans.getAmount(),
                trans.getType(),
                trans.getStatus(),
                trans.getTransactionDate()
        );
    }
}
