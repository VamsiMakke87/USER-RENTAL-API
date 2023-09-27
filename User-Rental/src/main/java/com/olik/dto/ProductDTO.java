package com.olik.dto;

import com.olik.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	
	private long productId;

	private String productName;

	private String image;

	private double costPerHour;

	private String category;

	private double totalCost;
	
	public ProductDTO(Product product,double totalCost){
		this.productId=product.getProductId();
		this.productName=product.getProductName();
		this.image=product.getImage();
		this.costPerHour=product.getCostPerHour();
		this.category=product.getCategory();
		this.totalCost=totalCost;
	}

	public ProductDTO(long productId) {
		this.productId=productId;
	}

}
