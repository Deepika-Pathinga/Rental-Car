package com.rental.car;

public class PerDayRentPrice implements Comparable<PerDayRentPrice>{

	public String sVin;
	public float fPrice;
	//public float fPriceAfterDiscount;
	
	public PerDayRentPrice(String vin, float price) {
		this.sVin=vin;
		this.fPrice=price;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int compareTo(PerDayRentPrice o) {
		// TODO Auto-generated method stub
		return (int) ((float) this.fPrice -o.fPrice);
	}

}
