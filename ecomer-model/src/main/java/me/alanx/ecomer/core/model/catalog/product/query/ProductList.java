package me.alanx.ecomer.core.model.catalog.product.query;

import java.util.ArrayList;
import java.util.List;

import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.common.EntityList;

public class ProductList extends EntityList {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7267292601646149482L;
	private List<Product> products = new ArrayList<Product>();
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}


}
