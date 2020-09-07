package com.rental.car;

public class PerDayRentPriceWithDiscount implements Comparable<PerDayRentPriceWithDiscount> {
	public String sVin;
	//public float fPrice;
    public float fPriceAfterDiscount;
	
	public PerDayRentPriceWithDiscount(String vin, float priceAfterDiscount) {
		this.sVin=vin;
		this.fPriceAfterDiscount=priceAfterDiscount;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int compareTo(PerDayRentPriceWithDiscount o) {
		// TODO Auto-generated method stub
		return (int) ((float) this.fPriceAfterDiscount -o.fPriceAfterDiscount);
	}
}
