package com.olik.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.olik.dto.ProductDTO;
import com.olik.entity.Product;
import com.olik.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	ProductRepo productRepo;

	public List<Product> start() {
		List<Product> products = new ArrayList();
		products.add(new Product("Cycle 1", "cycle1-img-url", 200, "Cycle"));
		products.add(new Product("Cycle 2", "cycle2-img-url", 150, "Cycle"));
		products.add(new Product("Cycle 3", "cycle3-img-url", 250, "Cycle"));
		products.add(new Product("Racket 1", "racket1-img-url", 100, "Racket"));
		products.add(new Product("Racket 2", "racket2-img-url", 200, "Racket"));
		products.add(new Product("Racket 3", "racket3-img-url", 150, "Racket"));
		products.add(new Product("Football 1", "football1-img-url", 250, "Football"));
		products.add(new Product("Football 2", "football2-img-url", 350, "Football"));
		products.add(new Product("Football 3", "football3-img-url", 275, "Football"));
		for (Product product : products) {
			addProduct(product);
		}
		return products;
	}

	public Set<ProductDTO> getAvailableProducts(String category, LocalDateTime startTime, LocalDateTime endTime,
			List<Map<String, Object>> orders) {
		Set<ProductDTO> set = new TreeSet<>(new Comparator<>() {
			@Override
			public int compare(ProductDTO p1, ProductDTO p2) {
				return (int) (p1.getProductId() - p2.getProductId());
			}
		});

		for (Product p : findByCategory(category)) {
			set.add(new ProductDTO(p, calculateCost(startTime, endTime, p.getCostPerHour())));
		}

		for (Map<String, Object> map : orders) {
			LocalDateTime start = LocalDateTime.parse(map.get("startTime") + "");
			LocalDateTime end = LocalDateTime.parse(map.get("endTime") + "");
			long productId = Long.parseLong(map.get("productId") + "");
			if (!((start.isBefore(startTime) && end.isBefore(startTime))
					|| (start.isAfter(endTime) && end.isAfter(endTime)))) {
				set.remove(new ProductDTO(productId));
			}
		}

		return set;
	}

	public double calculateCost(LocalDateTime startTime, LocalDateTime endTime, double costPerHour) {
		long duration = Duration.between(startTime, endTime).toHours();
		long minutes = Duration.between(startTime, endTime).toMinutes();
		if (minutes % 60 != 0)
			duration++;
		double cost = costPerHour * duration;
		return cost;
	}

	public Product addProduct(Product product) {
		return productRepo.save(product);
	}

	public ResponseEntity<String> order(Product product) {
		return null;
	}

	public List<Product> findByCategory(String category) {
		return productRepo.findByCategory(category);
	}

	public void refresh() {

	}

	public Product getProductById(long id) {
		Optional<Product> opt = productRepo.findById(id);
		Product p = null;
		if (opt.isPresent() && !opt.isEmpty())
			p = opt.get();
		return p;
	}

}
