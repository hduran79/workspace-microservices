package com.votre.microservices.shopping.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.votre.microservices.shopping.dto.InvoiceDTO;
import com.votre.microservices.shopping.entity.Invoice;
import com.votre.microservices.shopping.service.IInvoiceService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "invoices", produces = MediaType.APPLICATION_JSON_VALUE)
public class InvoiceController {

    private final AtomicLong counter = new AtomicLong();
    private static final String template = "Hello %s";

    @Autowired
    private IInvoiceService service;

    @GetMapping(value = "/test")
    public InvoiceDTO prodcut(@RequestParam(value = "name", defaultValue = "World") String name) {
        return InvoiceDTO.builder().id(counter.incrementAndGet()).message(String.format(template, name)).build();
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Invoice>> listAllInvoices() {
        return ResponseEntity.ok(service.findInvoiceAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Invoice> getInvoices(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getInvoice(id));
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody @Valid InvoiceDTO invoice) {
        return ResponseEntity.ok(service.createInvoice(invoice));
    }

    @PutMapping
    public ResponseEntity<Invoice> updateInvoice(@RequestBody InvoiceDTO product) {
        return ResponseEntity.ok(service.updateInvoice(product));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.deleteInvoice(id));
    }

    @CircuitBreaker(name = "customerMS", fallbackMethod = "fallBackGetCustomer")
    @GetMapping(value = "hystrix/{id}")
    public ResponseEntity<Invoice> getInvoicesHystrix(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getInvoice(id));
    }

    private ResponseEntity<Invoice> fallBackGetCustomer(@PathVariable("id") Long id, RuntimeException e) {
        return ResponseEntity.ok(Invoice.builder().id(id).state("none").build());
    }

}
