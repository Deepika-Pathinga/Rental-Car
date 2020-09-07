package com.rental.car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
//import java.util.ListIterator;

import org.testng.Assert;
import org.testng.annotations.Test;
//import org.hamcrest.comparator.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TestCases extends StubMappings {
	
	@Test(priority=1)
	public void getRequest_Returns_ListOfAllCars() {
		
		String sHostName ="http://localhost:8088";
		String URI = "/getcars";
		String URL = sHostName+URI;
		RestAssured.baseURI= URL;
		
		Response response = RestAssured.given().contentType("application/json").get();
		
		//System.out.println("Test case 1:");
		System.out.println("Response: "+response.asString());
		//System.out.println("Status Code: "+response.getStatusCode());
		String responseBody = response.getBody().asString();
		//System.out.println(responseBody);
		Assert.assertTrue(responseBody.contains("Tesla"));
	//	System.out.println("Test case 1 Completed");
		System.out.println("-----------------------------------------------------");
	}
	
	@Test(priority=2)
	public void getRequest_Returns_TeslaCarswithBlueColorandNotes() {
		
		//String sHostName ="http://localhost:8088";
		//String URI = "/getcars";
		//String URL = sHostName+URI;
		//RestAssured.baseURI= URL;
		
		String make = "Tesla";
		String color ="Blue";
		Response response = RestAssured.given().contentType("application/json").get();
		//System.out.println(response.asString());
		//System.out.println("Test case 2 started: Status Code "+response.getStatusCode());
		
		//System.out.println("Response Body: "+responseBody);
		List<String> limake = response.jsonPath().getList("Car.make");
		//System.out.println(limake);
		System.out.println("Question 1:");
		System.out.println("Printing the blue tesla car notes");
		
		int setIndex=0;
		int carnumber =0;
		for(int i=0; i<limake.size();i++) {
			if (limake.get(i).equalsIgnoreCase(make)) {
				setIndex=i;
				String carColor = response.jsonPath().getString("Car["+setIndex+"].metadata.Color");
				if(carColor.equalsIgnoreCase(color)) {
					//System.out.println("Printing the car make which is only "+limake.get(i));
					//System.out.println("Printing only "+make+" of color "+ carColor);
					//assertThat(carColor, containsString("Blue"));
					Assert.assertTrue(carColor.contains("Blue"));
					
					String carNotes = response.jsonPath().getString("Car["+setIndex+"].metadata.Notes");
					carnumber++;
					System.out.println(carnumber+" "+carNotes);
					
				}
			}
		}
		String responseBody = response.getBody().asString();
		Assert.assertTrue(responseBody.contains("Tesla"));
		Assert.assertTrue(limake.contains("Tesla"));
		//assertThat(limake, hasItem("Tesla"));
		System.out.println("Question 1 completed");
		System.out.println("-----------------------------------------------------");
		
	}
	
	@Test(priority=3)
	public void getRequest_Returns_LowestPerDayRentWithPriceAndWithPriceAfterDiscount() {
		
		//String sHostName ="http://localhost:8088";
		//String URI = "/getcars";
		//String URL = sHostName+URI;
		//RestAssured.baseURI= URL;
		
		Response response = RestAssured.given().contentType("application/json").get();
		
		System.out.println("Question 2:");
		//System.out.println(response.asString());
		//System.out.println(response.getStatusCode());
		//String responseBody = response.getBody().asString();
		//List<String> limake = response.jsonPath().getList("Car.make");
		//List<String> limodel = response.jsonPath().getList("Car.model");
		//List<String> livin = response.jsonPath().getList("Car.vin");
		
		List<Float> liPerDayRent = response.jsonPath().getList("Car.perdayrent");
		System.out.println("Printing per day rental of all cars Discount and Price: "+liPerDayRent);
		System.out.println();
		//System.out.println("Printing make, model, vin no and per day rental of all cars: "+limake+" "+limodel+ " "+livin+" "+ liPerDayRent );
		ArrayList<PerDayRentPrice> alPerDayRentPrice = new ArrayList<>();
		ArrayList<PerDayRentPriceWithDiscount> alPerDayRentPriceWithDiscount = new ArrayList<>();
		//int setIndex=0;
		for(int i=0; i<liPerDayRent.size();i++) {
			
			String vinumber = response.jsonPath().getString("Car["+i+"].vin");
			Float perDayRentPrice = response.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
			Float perDayRentDiscount = response.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
			Float perDayRentPriceAndDiscount = (perDayRentPrice-(perDayRentPrice*perDayRentDiscount/100));
			alPerDayRentPrice.add(new PerDayRentPrice(vinumber, perDayRentPrice));
			alPerDayRentPriceWithDiscount.add(new PerDayRentPriceWithDiscount(vinumber, perDayRentPriceAndDiscount));
						
		}
		Collections.sort(alPerDayRentPrice);
		
		System.out.println("Printing lowest per day rental of all cars with sorted price: ");		
		Iterator<PerDayRentPrice> itPrice = alPerDayRentPrice.iterator();
		while(itPrice.hasNext()) {
			PerDayRentPrice obj = (PerDayRentPrice) itPrice.next();
			System.out.println("Vin: "+obj.sVin+" and "+"Price sorted: "+obj.fPrice);
		}
		System.out.println();
		
		
		Collections.sort(alPerDayRentPriceWithDiscount);
		
		System.out.println("Printing lowest per day rental of all cars with sorted discount price: ");		
		Iterator<PerDayRentPriceWithDiscount> itDiscount = alPerDayRentPriceWithDiscount.iterator();
		while(itDiscount.hasNext()) {
			PerDayRentPriceWithDiscount obj = (PerDayRentPriceWithDiscount) itDiscount.next();
			System.out.println("Vin: "+obj.sVin+" and "+" Discount Price sorted: "+obj.fPriceAfterDiscount);
		}	
		System.out.println();

		//asserThat(Float.valueOf((float)130.0), comparesEqualTo(alPerDayRentPrice.get(0).fPrice));
		
		System.out.println("Question 2 completed");
		System.out.println("-----------------------------------------------------");
		
	}
	
	@Test(priority = 4)
	public void getRequest_Returns_HightestRevenueForLastYear() {
		//String sHostName ="http://localhost:8088";
		//String URI = "/getcars";
		//String URL = sHostName+URI;
	//	RestAssured.baseURI= URL;
		
		Response response = RestAssured.given().contentType("application/json").get();
		
		System.out.println("Question 3:");
		ArrayList<CarRevenue> alCarRevenue = new ArrayList<>();
		List<Float> licarmetrics = response.jsonPath().get("Car.metrics");
		System.out.println(licarmetrics);
		for(int i=0;i<licarmetrics.size();i++) {
			String vinumber = response.jsonPath().getString("Car["+i+"].vin");
			Float perDayRentPrice = response.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
			Float perDayRentDiscount = response.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
			Float perDayRentPriceAndDiscount = (perDayRentPrice-(perDayRentPrice*perDayRentDiscount/100));
			
			Float yoyMaintenanceCost = response.jsonPath().getFloat("Car["+i+"].metrics.yoymaintenancecost");
			Float depreciation = response.jsonPath().getFloat("Car["+i+"].metrics.depreciation");
			int yeartodate = response.jsonPath().getInt("Car["+i+"].metrics.rentalcount.yeartodate");
			
			Float carRevenue =((yeartodate*perDayRentPriceAndDiscount)-(yoyMaintenanceCost-depreciation));
			alCarRevenue.add(new CarRevenue(vinumber, carRevenue));
			
			Collections.sort(alCarRevenue,Collections.reverseOrder());
			System.out.println("Highest revenue car in the last year: ");
			
			Iterator<CarRevenue> itRevenue = alCarRevenue.iterator();
			while(itRevenue.hasNext()) {
				CarRevenue obj = (CarRevenue) itRevenue.next();
				System.out.println("Vin: "+obj.sVin+" and "+" Highest Car Revenue sorted: "+obj.fCarRevenue);
			}
					
		}
		System.out.println("Question 3 completed");
		System.out.println("-----------------------------------------------------");			
	}
	
}