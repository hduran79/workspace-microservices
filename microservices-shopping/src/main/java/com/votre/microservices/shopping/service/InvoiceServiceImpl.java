package com.votre.microservices.shopping.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.votre.microservices.shopping.client.ICustomerClient;
import com.votre.microservices.shopping.client.IProductsClient;
import com.votre.microservices.shopping.config.MicroServicesBusinessException;
import com.votre.microservices.shopping.dto.CustomerDTO;
import com.votre.microservices.shopping.dto.InvoiceDTO;
import com.votre.microservices.shopping.dto.InvoiceItemDTO;
import com.votre.microservices.shopping.dto.ProductDTO;
import com.votre.microservices.shopping.entity.Invoice;
import com.votre.microservices.shopping.entity.InvoiceItem;
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
	IProductsClient productClient;
	
	@Override
	public List<Invoice> findInvoiceAll() {
		return invoiceRepository.findAll();
	}

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
		
		System.out.println("CustomerID: " + invoice.getCustomerId());
		
		invoiceDB = invoiceRepository.save(Invoice.builder()
				.description(invoice.getDescription())
				.state(invoice.getState())
				.numberInvoice(invoice.getNumberInvoice())
				.createAt(invoice.getCreateAt())
				.customerId(invoice.getCustomerId())
				.items(invoice.getItems())
				.build());
		
        invoiceDB.getItems().forEach( invoiceItem -> {
            productClient.updateStockProduct( invoiceItem.getProductId(), invoiceItem.getQuantity() * -1);
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

//	@Override
//	public Invoice getInvoice(Long id) {
//		return invoiceRepository.findById(id).orElse(null);
//	}
	
	@Override
    public Invoice getInvoice(Long id) {

        Invoice eInvoice = invoiceRepository.findById(id).orElse(null);
        
        List<InvoiceItemDTO> invoiceItemDTO = eInvoice.getItems().stream().map(item -> 
        InvoiceItemDTO.builder()
        .id(item.getId())
        .quantity(item.getQuantity())
        .price(item.getPrice())
        .productId(item.getProductId())
        .subTotal(item.getSubTotal())
        .build())
        .collect(Collectors.toList());
        		
        InvoiceDTO invoice = InvoiceDTO.builder()
        		.id(eInvoice.getId())
        		.customerId(eInvoice.getCustomerId())
        		.description(eInvoice.getDescription())
        		.numberInvoice(eInvoice.getNumberInvoice())
        		.state(eInvoice.getState())
        		.createAt(eInvoice.getCreateAt())
        		.items(invoiceItemDTO)
        		.build();
        
        
        if (null != invoice ){
        	ResponseEntity<?> rEntityCustomer = customerClient.getCustomer(invoice.getCustomerId());
        	CustomerDTO customer = (CustomerDTO) rEntityCustomer.getBody();
            invoice.setCustomer(customer);
            
            List<InvoiceItem> listItem=invoice.getItems().stream().map(invoiceItem -> {
            	
            	ResponseEntity<ProductDTO> rEntityProduct = productClient.getProduct(invoiceItem.getProductId());
                
                ProductDTO product = rEntityProduct.getBody();
                
                invoiceItem.setProduct(product);
                return invoiceItem;
            }).collect(Collectors.toList());
            invoice.setItems(listItem);
        }
        return invoice ;
    }

}
