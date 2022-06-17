package Main;
import java.util.Comparator;

import model.Order;
	public class sortPrice implements Comparator<Order>
	{
		@Override
		public int compare(Order o1, Order o2)
		{
			if(o1.getOrderCost() > o2.getOrderCost())
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
	}
