package com.votre.microservices.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.votre.microservices.shopping.entity.InvoiceItem;

@Repository
public interface IInvoiceItemsRepository extends  JpaRepository<InvoiceItem,Long> {

}
