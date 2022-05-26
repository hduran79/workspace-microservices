package com.votre.microservices.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.votre.microservices.shopping.entity.Invoice;

/**
 * @Repository: un mecanismo para encapsular el comportamiento de
 *              almacenamiento, recuperación y búsqueda que emula una colección
 *              de objetos JpaRepository mapea la entidad Product
 */

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {

	public List<Invoice> findByCustomerId(Long customerId );
    public Invoice findByNumberInvoice(String numberInvoice);

}
