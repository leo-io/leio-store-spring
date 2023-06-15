package io.leo.ecommerce.dto;

import io.leo.ecommerce.entity.Address;
import io.leo.ecommerce.entity.Customer;
import io.leo.ecommerce.entity.Order;
import io.leo.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
    private String name;
//add a string field
    private String email;


}
