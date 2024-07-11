package ru.cft.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.dto.CreateInvoiceRequest;
import ru.cft.template.dto.InvoiceDto;
import ru.cft.template.service.InvoiceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("potato")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping("/invoices")
    public InvoiceDto createInvoice(Authentication authentication, @RequestBody CreateInvoiceRequest body) {
        return invoiceService.createInvoice(authentication, body);
    }

    @PostMapping("/invoices/{invoiceId}/pay")
    public InvoiceDto payInvoice(@PathVariable UUID invoiceId, Authentication authentication) {
        return invoiceService.payInvoice(authentication, invoiceId);
    }

    @PostMapping("/invoices/{invoiceId}/cancel")
    public InvoiceDto cancelInvoice(@PathVariable UUID invoiceId, Authentication authentication) {
        return invoiceService.cancelInvoice(authentication, invoiceId);
    }

    @GetMapping("/invoices/{invoiceId}")
    public InvoiceDto getInvoiceInfo(@PathVariable UUID invoiceId) {
        return invoiceService.getInvoiceInfo(invoiceId);
    }

    @GetMapping("/invoices/sent")
    public List<InvoiceDto> getAllSentInvoices(Authentication authentication) {
        return invoiceService.getAllSentInvoices(authentication);
    }

    @GetMapping("/invoices/received")
    public List<InvoiceDto> getAllReceivedInvoices(Authentication authentication) {
        return invoiceService.getAllReceivedInvoices(authentication);
    }
}
