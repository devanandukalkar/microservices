package com.devlearn.orderservice.service;

import com.devlearn.orderservice.dto.OrderLineItemsDto;
import com.devlearn.orderservice.dto.OrderRequest;
import com.devlearn.orderservice.model.Order;
import com.devlearn.orderservice.model.OrderLineItems;
import com.devlearn.orderservice.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private IOrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItems(orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto).toList())
                .build();
        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .skuCode(orderLineItemsDto.getSkuCode())
                .build();
    }
}
