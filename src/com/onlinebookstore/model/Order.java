package com.onlinebookstore.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Order {
	private ArrayList<Item> orderList;
	private BigDecimal total;
	private String dateOrdered;
	private String shippingAddress;
	private String orderNum;

	public Order(ArrayList<Item> orderList, BigDecimal total,
			String dateOrdered, String shippingAddress, String orderNum) {
		this.orderList = orderList;
		this.total = total;
		this.dateOrdered = dateOrdered;
		this.shippingAddress = shippingAddress;
		this.orderNum = orderNum;
	}

	public ArrayList<Item> getOrderList() {
		return this.orderList;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public String getDateOrdered() {
		return this.dateOrdered;
	}

	public String getShippingAddress() {
		return this.shippingAddress;
	}

	public String getOrderNum() {
		return this.orderNum;
	}

	public void addItemOrdered(Item item) {
		this.orderList.add(item);
	}

	public void setShippingAddress(String address) {
		this.shippingAddress = address;
	}

	public String toString() {
		return this.total + " " + this.dateOrdered + " " + this.shippingAddress
				+ " " + this.orderNum + this.orderListToString();
	}

	public String orderListToString() {
		StringBuilder list = new StringBuilder();
		for (Item item : orderList) {
			list.append("\n").append(item.toString()).append("\n");
		}
		return list.toString();
	}
}
