package com.olik.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long productId;

	private String productName;

	private String image;

	private double costPerHour;

	private String category;

//	private String status;
	

	public Product(String productName, String image, double costPerHour, String category) {
		super();
		this.productName = productName;
		this.image = image;
		this.costPerHour = costPerHour;
		this.category = category;
	}


	public Product(long productId) {
		this.productId=productId;
	}

}
