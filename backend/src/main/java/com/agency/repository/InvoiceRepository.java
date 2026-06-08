package com.agency.repository;

import com.agency.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByClientId(Long clientId);
    List<Invoice> findByStatus(Invoice.InvoiceStatus status);
    Invoice findByInvoiceNumber(String invoiceNumber);
}
