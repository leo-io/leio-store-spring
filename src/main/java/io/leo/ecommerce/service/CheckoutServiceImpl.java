package io.leo.ecommerce.service;

import io.leo.ecommerce.dao.CustomerRepository;
import io.leo.ecommerce.dto.Purchase;
import io.leo.ecommerce.dto.PurchaseResponse;
import io.leo.ecommerce.entity.Order;
import io.leo.ecommerce.entity.OrderItem;
import io.leo.ecommerce.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;
    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
       //retrieve the order info from the DTO
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //populate order with order items
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with billing and shipping Address
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();
        String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByEmail(theEmail);

        if (customerFromDB != null){
            customer = customerFromDB;
        }


        customer.add(order);


       // save to DB

        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);



    }

    private String generateOrderTrackingNumber() {
        //generate a random UUID - Universally Unique Identifier

    return UUID.randomUUID().toString();

    }
}
