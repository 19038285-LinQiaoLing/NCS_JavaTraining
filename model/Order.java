package model;
import java.time.*;
import java.util.Objects;

public class Order {
	String name;
	String Category;
	int orderCost;
	LocalDate date;
	String Action;
	
	
	


	public Order(String name, String category, int orderCost, LocalDate date, String action) {
		super();
		this.name = name;
		Category = category;
		this.orderCost = orderCost;
		this.date = date;
		Action = action;
	}


	public String getCategory() {
		return Category;
	}


	public void setCategory(String category) {
		Category = category;
	}


	public int getOrderCost() {
		return orderCost;
	}


	public void setOrderCost(int orderCost) {
		this.orderCost = orderCost;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public String getAction() {
		return Action;
	}


	public void setAction(String action) {
		Action = action;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public int hashCode() {
		return Objects.hash(Action, Category, date, name, orderCost);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(Action, other.Action) && Objects.equals(Category, other.Category)
				&& Objects.equals(date, other.date) && Objects.equals(name, other.name) && orderCost == other.orderCost;
	}


	@Override
	public String toString() {
		return "Order [name=" + name + ", Category=" + Category + ", orderCost=" + orderCost + ", date=" + date
				+ ", Action=" + Action + "]";
	}

	
}
