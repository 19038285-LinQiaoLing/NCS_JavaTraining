package Main;

import org.w3c.dom.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.*;

import service.IOrderService;
import exceptions.InvalidCategryException;
import model.Order;

public class MainClass implements IOrderService {
	public static void main(String[] args) {

		Scanner newScanner = new Scanner(System.in);
		MainClass main = new MainClass();

		int no = 0;
		while(no !=9) {
			menu();
			System.out.println("Please enter your choice > ");
			int option = newScanner.nextInt();			

			if(option ==1) {

				Map<String, List<Order>> ordermap = main.createOrderMapByUser();
				
				for(String key:ordermap.keySet()) {
					System.out.println(key +": "+ordermap.get(key)+"\n");
				}

			}else if(option ==2) {

				System.out.println(main.getAllOrders());

			}else if(option ==3) {

				System.out.println("Enter the category that you want to see >");
				String cat = newScanner.next();
				try {
					System.out.println(main.getAllOrdersByCategory(cat));
				} catch (InvalidCategryException e) {
					System.out.print(e);
				}

			}else if(option ==4) {

				System.out.println("Enter the category cost that you want to see >");
				String cat = newScanner.next();
				try {
					System.out.println(main.getTotalOrderCost(cat));
				} catch (InvalidCategryException e) {
					System.out.print(e);
				}

			}else if(option ==5) {

				main.getAllCanceledOrder();

			}else if(option ==6) {

				System.out.println("Enter the Action that you want to see >");
				String cat = newScanner.next();
				List<Order> finalList = main.filterOrders(cat);
				System.out.println("Sort by Action");
				System.out.println("================================================================================================");
				for(int i = 0; i< finalList.size();i++) {
					System.out.println(finalList.get(i));
					System.out.println("--------------------------------------------------------------------------------------------");

				}

			}else if(option ==7) {

				List<Order> finalList = main.getOrdersBasedOnOrderValue();
				System.out.println("Sort by Price");
				System.out.println("================================================================================================");
				for(int i = 0; i< finalList.size();i++) {
					System.out.println(finalList.get(i));
					System.out.println("--------------------------------------------------------------------------------------------");

				}



			}else if (option == 8) {
				main.generatePDFReports();
			}else if(option ==9) {
				System.out.print("GoodBye");
				break;
			}
		}

	}

	public static void menu() {
		System.out.println("====================================");
		System.out.println("Ordering Service System");
		System.out.println("====================================");
		System.out.println("1) Create Order Map");
		System.out.println("2) View All Order");
		System.out.println("3) Search Order by Category");
		System.out.println("4) View Total Order Cost by Category");
		System.out.println("5) View Cancelled Orders");
		System.out.println("6) Filter Order");
		System.out.println("7) View Order by Order Value");
		System.out.println("8) Generate PDF Report");
		System.out.println("9) Exit");
		System.out.println("====================================");
	}

	@Override
	public List<Order> getAllOrdersByCategory(String category) throws InvalidCategryException {
		int count = 0;
		List<Order> tempList = getAllOrders();
		List<Order> finalList = new ArrayList<>();
		for(int x = 0; x<tempList.size();x++) {
			if(tempList.get(x).getCategory().equalsIgnoreCase(category)) {
				count++;
				finalList.add(tempList.get(x));
			}
		}

		if(count == 0) {
			throw new InvalidCategryException("Invalid Category");
		}else {
			for(int y = 0; y<finalList.size();y++) {
				System.out.println(finalList.get(y));
			}
		}		
		return finalList;
	}

	@Override
	public int getTotalOrderCost(String category) throws InvalidCategryException {
		int cost = 0;
		int count = 0;
		List<Order> tempList = getAllOrders();
		List<Order> finalList = new ArrayList<>();
		for(int x = 0; x<tempList.size();x++) {
			if(tempList.get(x).getCategory().equalsIgnoreCase(category)&& tempList.get(x).getAction().equalsIgnoreCase("Delivered")) {
				cost = cost+tempList.get(x).getOrderCost();
				count++;
				finalList.add(tempList.get(x));
			}
		}

		if(count == 0) {
			throw new InvalidCategryException("Invalid Category");
		}else {

			System.out.println("The toal cost for category "+category+" is: $"+cost);
		}
		return cost;
	}

	@Override
	public List<Order> getAllCanceledOrder() {
		int count = 0;
		List<Order> tempList = getAllOrders();
		List<Order> finalList = new ArrayList<>();
		for(int x = 0; x<tempList.size();x++) {
			if(tempList.get(x).getAction().equalsIgnoreCase("Cancelled")) {
				count++;
				finalList.add(tempList.get(x));
			}
		}

		if(count == 0) {
			System.out.println("No Cancelled Orders");
		}else {
			for(int y = 0; y<finalList.size();y++) {
				System.out.println(finalList.get(y));
			}
		}		
		return finalList;
	}

	@Override
	public Map<String, List<Order>> createOrderMapByUser() {
		List<Order> tempList = getAllOrders();
		Map<String, List<Order>> newMap = new HashMap<String, List<Order>>();
		
		for(int i=0; i<tempList.size();i++) {
			List<Order> orderList = new ArrayList();
			String key1 = tempList.get(i).getName();
			for(int x =0; x<tempList.size();x++) {
				if(key1.equalsIgnoreCase(tempList.get(x).getName())) {
					orderList.add(new Order(tempList.get(x).getName(), tempList.get(x).getCategory(), tempList.get(x).getOrderCost(), tempList.get(x).getDate(), tempList.get(x).getAction()));
				}
			}
			
			newMap.put(key1, orderList);
			
		}
		return newMap;
	}

	@Override
	public List<Order> filterOrders(String action) {
		// TODO Auto-generated method stub

		sortAction sortbyAction = new sortAction();
		List<Order> finalList = new ArrayList<>();
		finalList = getAllOrders();
		Collections.sort(finalList, sortbyAction);

		return finalList;
	}

	@Override
	public List<Order> getOrdersBasedOnOrderValue() {
		sortPrice sortbyPrice = new sortPrice();
		List<Order> finalList = new ArrayList<>();
		finalList = getAllOrders();
		Collections.sort(finalList, sortbyPrice);

		return finalList;
	}

	@Override
	public boolean generatePDFReports() {

		int colCount = 0;

		try(BufferedReader br = new BufferedReader(new FileReader("Order.csv"))){
			String line;
			while((line = br.readLine())!=null) {
				colCount++;
				String[] value = line.split(",");
			}

			try {
				PDDocument document = new PDDocument();
				PDPage firstpage = new PDPage();
				document.addPage(firstpage);				

				document.save("C:\\Java\\eclipse\\Repositiory\\midTerm_exam\\Order.pdf");
				System.out.println("PDF Created");
				document.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return false;

	}

	public List<Order> getAllOrders(){
		List<Order> finalList = new ArrayList<>();
		List<Order> orderList = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader("Order.csv"))){
			String line;
			while((line = br.readLine())!=null) {
				line = line.trim();
				String[] value = line.split(",");

				DateTimeFormatter df = new DateTimeFormatterBuilder()
						// case insensitive to parse JAN and FEB
						.parseCaseInsensitive()
						// add pattern
						.appendPattern("d-MMM-yy")
						// create formatter (use English Locale to parse month names)
						.toFormatter(Locale.ENGLISH);
				LocalDate localDate = LocalDate.parse(value[3], df);
				Order newOrder = new Order(value[0].toString(), value[1].toString(), Integer.parseInt(value[2]),localDate, value[4].toString());
				finalList.add(newOrder);
				orderList.clear();

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print(e);;
		}


		return finalList;
	}

}
