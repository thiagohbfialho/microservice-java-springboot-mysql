package com.canadafood.payments.http;

import com.canadafood.payments.model.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("order-ms")
public interface OrderClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/orders/{id}/paid")
    void updatePayment(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/orders/{id}")
    OrderDto getOrder(@PathVariable Long id);

}
