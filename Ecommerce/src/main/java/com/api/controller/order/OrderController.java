package com.api.controller.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.LocalUser;
import com.model.WebOrder;
import com.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderservice;
	
	@GetMapping
	public List<WebOrder> getorders(@AuthenticationPrincipal LocalUser user){
		return orderservice.getorders(user);
		
	}

}
