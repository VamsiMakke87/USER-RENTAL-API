package com.olik.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.olik.dto.ProductDTO;
import com.olik.entity.Product;
import com.olik.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	RestTemplate restTemplate = new RestTemplate();
	private static final String ORDER_URI = "http://localhost:8080/order/getOrders/";

	@PostMapping("/start")
	public ResponseEntity<?> start() {
		productService.start();

		return new ResponseEntity<>("Application Started", HttpStatus.OK);

	}

	@GetMapping("/category")
	public ResponseEntity<?> getByCategory(@RequestParam String category) {
		List li = productService.findByCategory(category);
		if (li.size() == 0)
			return new ResponseEntity<>("No Products found!", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(li, HttpStatus.ACCEPTED);
	}

	@GetMapping("/getProducts")
	public ResponseEntity<?> getProducts(@RequestParam String category,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) throws Exception {
		
		if(startTime.isAfter(endTime))
			return new ResponseEntity<>("Start time must be before end time",HttpStatus.NOT_FOUND);
		if(startTime.isBefore(LocalDateTime.now()))
			return new ResponseEntity<>("Start time must be after current time",HttpStatus.NOT_FOUND);
		
		List<Map<String,Object>> orders=(List<Map<String, Object>>) restTemplate.getForEntity(ORDER_URI+category, List.class).getBody();
		
		return new ResponseEntity<>(productService.getAvailableProducts(category, startTime, endTime, orders), HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}/{startTime}/{endTime}")
	public ResponseEntity<?> calculateCost(@PathVariable long id,@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime startTime,@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime){
		
		Product p=productService.getProductById(id);
		return new ResponseEntity<>(new ProductDTO(p,productService.calculateCost(startTime, endTime, p.getCostPerHour())),HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable long id) {
		Product p = productService.getProductById(id);
		if (p == null)
			return new ResponseEntity<>("No Products Found!", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(new ProductDTO(p,0), HttpStatus.ACCEPTED);
	}

}
