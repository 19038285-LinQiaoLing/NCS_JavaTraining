package Main;

import java.util.Comparator;

import model.Order;

public class sortAction implements Comparator<Order>
{
	@Override
	public int compare(Order o1, Order o2)
	{
		if(o1.getAction().equalsIgnoreCase(o2.getAction()))
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
}
