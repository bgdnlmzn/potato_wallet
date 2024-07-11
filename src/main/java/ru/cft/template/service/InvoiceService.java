package ru.cft.template.service;

import org.springframework.security.core.Authentication;
import ru.cft.template.dto.CreateInvoiceRequest;
import ru.cft.template.dto.InvoiceDto;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {
    InvoiceDto createInvoice(Authentication authentication, CreateInvoiceRequest body);
    InvoiceDto payInvoice(Authentication authentication, UUID invoiceId);
    InvoiceDto cancelInvoice(Authentication authentication, UUID invoiceId);
    InvoiceDto getInvoiceInfo(UUID invoiceId);
    List<InvoiceDto> getAllSentInvoices(Authentication authentication);
    List<InvoiceDto> getAllReceivedInvoices(Authentication authentication);
}
