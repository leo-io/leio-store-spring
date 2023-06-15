package io.leo.ecommerce.service;

import io.leo.ecommerce.dto.Purchase;
import io.leo.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
}
