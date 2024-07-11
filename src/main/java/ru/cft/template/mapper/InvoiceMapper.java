package ru.cft.template.mapper;

import org.springframework.stereotype.Component;
import ru.cft.template.dto.InvoiceDto;
import ru.cft.template.entity.Invoice;

@Component
public class InvoiceMapper {
    public static InvoiceDto toDto(Invoice invoice) {
        return new InvoiceDto(
                invoice.getId(),
                invoice.getAmount(),
                invoice.getReceiver().getId(),
                invoice.getSender().getId(),
                invoice.getComment(),
                invoice.getStatus(),
                invoice.getIssueDate()
        );
    }
}
