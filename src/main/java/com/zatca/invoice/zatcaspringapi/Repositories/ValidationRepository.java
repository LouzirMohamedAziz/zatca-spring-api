package com.zatca.invoice.zatcaspringapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zatca.invoice.zatcaspringapi.Models.Validation;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {
    
}
