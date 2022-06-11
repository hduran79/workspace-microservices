package com.votre.microservices.shopping.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votre.microservices.shopping.client.ICustomerClient;
import com.votre.microservices.shopping.client.IProductClient;
import com.votre.microservices.shopping.config.MicroServicesBusinessException;
import com.votre.microservices.shopping.dto.InvoiceDTO;
import com.votre.microservices.shopping.entity.Invoice;
import com.votre.microservices.shopping.entity.InvoiceItem;
import com.votre.microservices.shopping.model.CustomerDTO;
import com.votre.microservices.shopping.model.ProductDTO;
import com.votre.microservices.shopping.repository.IInvoiceItemsRepository;
import com.votre.microservices.shopping.repository.IInvoiceRepository;

import lombok.RequiredArgsConstructor;

/**
 * @RequiredArgsConstructor de esta forma se inyecta la dependencia
 *                          IProductRepository al constructor
 * @author hduran
 *
 */

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements IInvoiceService {

	Invoice invoiceDB = null;

	@Autowired
	IInvoiceRepository invoiceRepository;

	@Autowired
	IInvoiceItemsRepository invoiceItemsRepository;

	@Autowired
	ICustomerClient customerClient;

	@Autowired
	IProductClient productClient;

	@Override
	public List<Invoice> findInvoiceAll() {
		return invoiceRepository.findAll();
	}

	/**
	 * 
	 */
	@Override
	public Invoice createInvoice(InvoiceDTO invoice) {

		invoiceDB = invoiceRepository.findByNumberInvoice(invoice.getNumberInvoice());

		if (invoiceDB != null) {
			return invoiceDB;
		}

		invoice.setState("CREATED");

		List<InvoiceItem> items = invoice.getItems();

		items.stream().map(c -> {
			Double price = c.getPrice();
			Double quantity = c.getQuantity();

			Double subamount = 0D;

			if (price > 0 && quantity > 0) {
				subamount = quantity * price;
			}

			c.setSubTotal(subamount);
			return c;

		}).collect(Collectors.toList());

		invoiceDB = invoiceRepository.save(Invoice.builder().description(invoice.getDescription())
				.state(invoice.getState()).numberInvoice(invoice.getNumberInvoice()).createAt(invoice.getCreateAt())
				.customerId(invoice.getCustomerId()).items(invoice.getItems()).build());

		invoiceDB.getItems().forEach(i -> {
			productClient.updateStockProduct(i.getProductId(), i.getQuantity() * -1);
		});

		return invoiceDB;
	}

	@Override
	public Invoice updateInvoice(InvoiceDTO invoice) {

		invoiceDB = getInvoice(invoice.getId());

		if (invoiceDB == null) {
			return null;
		}

		invoiceDB.setCustomerId(invoice.getCustomerId());
		invoiceDB.setDescription(invoice.getDescription());
		invoiceDB.setNumberInvoice(invoice.getNumberInvoice());
		invoiceDB.getItems().clear();
		invoiceDB.setItems(invoice.getItems());

		return invoiceRepository.save(invoiceDB);
	}

	@Override
	public Invoice deleteInvoice(Long id) {

		invoiceDB = getInvoice(id);

		if (null == invoiceDB) {
			throw new MicroServicesBusinessException("001", "El producto no existe !!!");
		}

		return invoiceRepository.save(Invoice.builder().state("DELETED").build());
	}

	@Override
	public Invoice getInvoice(Long id) {

		Invoice invoice = invoiceRepository.findById(id).orElse(null);

		if (null != invoice) {
			CustomerDTO customer = customerClient.getCustomer(invoice.getCustomerId()).getBody();
			invoice.setCustomer(customer);

			List<InvoiceItem> lisItems = invoice.getItems().stream().map(it -> {
				ProductDTO product = productClient.getProduct(it.getProductId()).getBody();
				it.setProduct(product);
				return it;
			}).collect(Collectors.toList());

			invoice.setItems(lisItems);
		}

		return invoice;

	}

}
