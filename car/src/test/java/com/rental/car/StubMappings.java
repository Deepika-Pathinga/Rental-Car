package com.rental.car;

import org.testng.annotations.BeforeTest;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class StubMappings extends TestBase {
	@BeforeTest
	public void getMappingsRequestAndResponse() {
		wireMockServer.stubFor(
              get(urlEqualTo("/getcars"))
              .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
              .willReturn(
            		  aResponse()
            		  .withStatus(200)
            		  .withHeader("Content-Type", "application/json; charset=UTF-8")
            		  .withBodyFile("RentalCarData.json")
            		  ) );
		
	}

}
