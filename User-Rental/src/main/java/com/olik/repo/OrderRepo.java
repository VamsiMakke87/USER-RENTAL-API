package com.olik.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.olik.entity.Order;

public interface OrderRepo extends CrudRepository<Order, Long> {
	
//	List<Order> findByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(LocalDateTime endTime, LocalDateTime startTime);

	List<Order> findByCategory(String category);

}
