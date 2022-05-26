INSERT INTO tlb_invoices (id, number_invoice, description, customer_id, create_at, state) VALUES(2, '0001', 'invoice office items', 1, NOW(),'CREATED');

INSERT INTO tbl_invoce_items ( invoice_id, product_id, quantity, price ) VALUES(2, 1 , 1, 178.89);
INSERT INTO tbl_invoce_items ( invoice_id, product_id, quantity, price)  VALUES(2, 2 , 2, 12.5);
INSERT INTO tbl_invoce_items ( invoice_id, product_id, quantity, price)  VALUES(2, 3 , 1, 40.06);