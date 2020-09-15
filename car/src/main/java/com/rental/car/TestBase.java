package com.rental.car;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class TestBase {
	
	public static int iport = 8088;
	public WireMockServer wireMockServer;
	
	@BeforeSuite
	public void setUpWireMockServer() {
		wireMockServer = new WireMockServer(iport);
		//WireMock wiremock1 = new WireMock("localhost",9999);
		wireMockServer.start();
		
	}
	
	@AfterSuite
	public void tearDown() {
		wireMockServer.stop();
	}
}
