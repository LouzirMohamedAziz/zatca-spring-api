package com.zatca.invoice.zatcaspringapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zatca.invoice.zatcaspringapi.Models.InvoiceRequest;

@Repository
public interface InvoiceRequestRepository extends JpaRepository<InvoiceRequest, Long> {
    
}
