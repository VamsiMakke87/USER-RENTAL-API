package com.olik.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.olik.entity.Order;
import com.olik.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	RestTemplate restTemplate = new RestTemplate();

	private static final String ORDER_URI = "http://localhost:8080/product/";

	@GetMapping("/getAllOrders")
	public ResponseEntity<?> getAllOrders() {
		return new ResponseEntity<>(orderService.getAllOrders(),HttpStatus.ACCEPTED);
	}

	@PostMapping("/orderItem")
	public ResponseEntity<?> orderItem(@RequestBody Order order) {
		if(order.getStartTime().isAfter(order.getEndTime()))
			return new ResponseEntity<>("Start time must be before end time",HttpStatus.NOT_FOUND);
		if(order.getStartTime().isBefore(LocalDateTime.now()))
			return new ResponseEntity<>("Start time must be after current time",HttpStatus.NOT_FOUND);
		ResponseEntity<?> product = restTemplate.getForEntity(ORDER_URI + order.getProductId()+"/"+ order.getStartTime()+"/"+order.getEndTime(),
				Object.class);
		if (product.getStatusCode().value() == HttpStatus.ACCEPTED.value()) {
			Map<String, Object> hm = (Map<String, Object>) product.getBody();
			order.setCategory(hm.get("category")+"");
			order.setTotalCost(Double.parseDouble(hm.get("totalCost")+""));
			orderService.orderItem(order);
		}
		return new ResponseEntity<>(product.getBody(), HttpStatus.ACCEPTED);
	}

	@GetMapping("/getOrders/{category}")
	public ResponseEntity<?> getProducts(@PathVariable String category){
		
		return new ResponseEntity<>(orderService.findByCategory(category),HttpStatus.ACCEPTED);
	}

}
