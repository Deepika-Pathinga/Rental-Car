 package com.rental.car;

public class CarRevenue implements Comparable<CarRevenue> {
	public String sVin;
	public float fCarRevenue;
	
	public CarRevenue(String vin, float carRevenue) {
		this.sVin=vin;
		this.fCarRevenue=carRevenue;
	}

	@Override
	public int compareTo(CarRevenue o) {
		// TODO Auto-generated method stub
		return (int) ((float)this.fCarRevenue-o.fCarRevenue);
	}
	

}
