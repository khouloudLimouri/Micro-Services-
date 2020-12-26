package org.sid.billingservice.web;

import org.sid.billingservice.entites.Bill;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repository.BillRepsitory;
import org.sid.billingservice.repository.ProductRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingRestController {
    private BillRepsitory billRepsitory;
    private ProductRepository productRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;

    public BillingRestController(BillRepsitory billRepsitory, ProductRepository productRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billRepsitory = billRepsitory;
        this.productRepository = productRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

    @GetMapping(path ="/fullBill/{id}")
    public Bill getBill(@PathVariable(name="id") Long id){
    Bill bill = billRepsitory.findById(id).get();
        Customer customer= customerRestClient.getCustomerById(bill.getCustomerID());
    bill.setCustomer(customer);
    bill.getProductItems().forEach(pi->{
        Product product=productItemRestClient.getProductById(pi.getProductID());
        pi.setProduct(product);
    });
    return bill;

    }
}
