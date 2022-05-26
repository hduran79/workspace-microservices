package com.votre.microservices.shopping.service;

import java.util.List;

import com.votre.microservices.shopping.dto.InvoiceDTO;
import com.votre.microservices.shopping.entity.Invoice;

public interface IInvoiceService {

	public List<Invoice> findInvoiceAll();

	public Invoice createInvoice(InvoiceDTO invoice);

	public Invoice updateInvoice(InvoiceDTO invoice);

	public Invoice deleteInvoice(Long id);

	public Invoice getInvoice(Long id);

}
