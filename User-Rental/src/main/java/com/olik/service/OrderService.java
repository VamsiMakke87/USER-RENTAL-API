package com.olik.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.olik.entity.Order;
import com.olik.repo.OrderRepo;

@Service
public class OrderService {
	
	@Autowired
	OrderRepo orderRepo;
	
	
//	public List<Order> getOrders(LocalDateTime startTime,LocalDateTime endTime){
//		return orderRepo.findByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(endTime, startTime);
//	}


	public List<Order> getAllOrders() {
		return (List<Order>) orderRepo.findAll();
	}


	public Order orderItem(Order order) {
		return orderRepo.save(order);
	}


	public List<Order> findByCategory(String category) {
		return orderRepo.findByCategory(category);
	}





	

}
