package ru.cft.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.cft.template.entity.Invoice;
import ru.cft.template.entity.User;

import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    List<Invoice> findBySender(User sender);
    List<Invoice> findByReceiver(User receiver);
}
