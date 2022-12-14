package com.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.request.ShipmentRequest;
import com.request.ShipmentsRequest;
import com.response.BadRequestResponse;
import com.response.CarriersResponse;
import com.response.LabelResponse;
import com.response.ShipmentDetailsResponse;
import com.response.ShipmentResponse;
import com.response.VoidLabelResponse;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


@Service("ShipApiService")
public class ShiApiServiceImp implements ShipApiService  {

/*	@Override
	public CarriersResponse listAPICarriersAccounts() {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		CarriersResponse listResult = null;
		
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
				  .url("https://api.shipengine.com/v1/carriers")
				  .method("GET", null)
				  .addHeader("Host", "api.shipengine.com")
				  .addHeader("API-Key", "TEST_G+OAfjQ+8JjUHIKOXNwgz9DYDbsAPJeoc2oMpadZezk")
				  .build();
				try {
					//Response response = client.newCall(request).execute();
					ResponseBody responseBody = client.newCall(request).execute().body();
					listResult = gson.fromJson(responseBody.string(), CarriersResponse.class);
					//listResult = restTe
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		return listResult;
	}*/
	
	@Autowired
	public ShippingService shipService;
	
	@Override
	public CarriersResponse listAPICarriersAccounts() {
		// TODO Auto-generated method stub
		final String url = "https://api.shipengine.com/v1/carriers";
		CarriersResponse listResult = null;
		
		RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    headers.set("X-COM-PERSIST", "NO");
	    headers.set("X-COM-LOCATION", "USA");
	    headers.set("Host", "api.shipengine.com");
	    headers.set("API-Key", "TEST_G+OAfjQ+8JjUHIKOXNwgz9DYDbsAPJeoc2oMpadZezk");
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
	    
	    
	    //CarriersResponse result = restTemplate.getForObject(url, HttpMethod.GET, entity, CarriersResponse.class);
	    ResponseEntity<CarriersResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, CarriersResponse.class);
	    listResult = response.getBody();
		
		
		return listResult;
	}

	/*@Override
	public ShipmentResponse createShipment(ShipmentRequest shipReq) throws IOException {
		ShipmentResponse shipResponse =  new ShipmentResponse();	
		// TODO Auto-generated method stub
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				@SuppressWarnings("deprecation")
				RequestBody body = RequestBody.create(mediaType,shipReq.toString());
				Request request = new Request.Builder()
				  .url("https://api.shipengine.com/v1/labels")
				  .method("POST", body)
				  .addHeader("Host", "api.shipengine.com")
				  .addHeader("API-Key", "TEST_G+OAfjQ+8JjUHIKOXNwgz9DYDbsAPJeoc2oMpadZezk")
				  .addHeader("Content-Type", "application/json")
				  .build();
				Response response = client.newCall(request).execute();
				shipResponse.setResult("Y");
		return shipResponse;
	}	*/
	
	/*@Override
	public ShipmentResponse createShipment(ShipmentsRequest shipReq) throws IOException {
		ShipmentResponse shipResponse =  new ShipmentResponse();
		Gson gson = new Gson();
		
		String strShip = gson.toJson(shipReq);
		
		// TODO Auto-generated method stub
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				@SuppressWarnings("deprecation")
				RequestBody body = RequestBody.create(mediaType,strShip);
				Request request = new Request.Builder()
				  .url("https://api.shipengine.com/v1/labels")
				  .method("POST", body)
				  .addHeader("Host", "api.shipengine.com")
				  .addHeader("API-Key", "TEST_G+OAfjQ+8JjUHIKOXNwgz9DYDbsAPJeoc2oMpadZezk")
				  .addHeader("Content-Type", "application/json")
				  .build();
				Response response = client.newCall(request).execute();
				shipResponse.setResult("Y");
		return shipResponse;
	}
	*/
	
	@Override
	public ShipmentResponse createShipment(String shipReq, String packingslipNo, String user, String custshipAcc) throws IOException {
		ShipmentResponse shipResponse =  new ShipmentResponse();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		Boolean shipSave = true;
		ShipmentDetailsResponse shipDetails;
		//String bodyExample = "{\n  \"shipment\": {\n    \"service_code\": \"ups_ground\",\n    \"ship_to\": {\n      \"name\": \"Jane Doe\",\n      \"address_line1\": \"525 S Winchester Blvd\",\n      \"city_locality\": \"San Jose\",\n      \"state_province\": \"CA\",\n      \"postal_code\": \"95128\",\n      \"country_code\": \"US\",\n      \"address_residential_indicator\": \"yes\"\n    },\n    \"ship_from\": {\n      \"name\": \"John Doe\",\n      \"company_name\": \"Example Corp\",\n      \"phone\": \"555-555-5555\",\n      \"address_line1\": \"4009 Marathon Blvd\",\n      \"city_locality\": \"Austin\",\n      \"state_province\": \"TX\",\n      \"postal_code\": \"78756\",\n      \"country_code\": \"US\",\n      \"address_residential_indicator\": \"no\"\n    },\n    \"packages\": [\n      {\n        \"weight\": {\n          \"value\": 20,\n          \"unit\": \"ounce\"\n        },\n        \"dimensions\": {\n          \"height\": 6,\n          \"width\": 12,\n          \"length\": 24,\n          \"unit\": \"inch\"\n        }\n      }\n    ]\n  }\n}";

		
		
		final String url = "https://api.shipengine.com/v1/labels";
		
		try {
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("X-COM-PERSIST", "NO");
		    headers.set("X-COM-LOCATION", "USA");
		    headers.set("Host", "api.shipengine.com");
		    headers.set("API-Key", "TEST_G+OAfjQ+8JjUHIKOXNwgz9DYDbsAPJeoc2oMpadZezk");
			
			HttpEntity<String> requestEntity = 
				     new HttpEntity<String>(shipReq, headers);
			
		    ResponseEntity<LabelResponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, LabelResponse.class);
			
			shipResponse.setResult("Y");
			shipResponse.setLabelResponse(response.getBody());
			shipDetails = shipService.saveShipment(shipResponse, packingslipNo, user, custshipAcc);
	        if  (shipDetails.getResult()) {
	        	shipResponse.setShipDetails(shipDetails);
	        	System.out.println("YESSSS!!!");
	        }
			
			
		} catch (HttpStatusCodeException e)  {
			System.out.println("LA CAGASTE!!!" + e.getResponseBodyAsString());
			String responseString = e.getResponseBodyAsString();
			ObjectMapper mapper = new ObjectMapper();
			BadRequestResponse badResponse =  mapper.readValue(responseString,
					BadRequestResponse.class);
			shipResponse.setResult("N");
			shipResponse.setBadRequest(badResponse);
			
		}
		
		

		return shipResponse;
	}

	@Override
	public VoidLabelResponse cancelLabel(String apiLabelId, String apiShippingId, long shippingId) {
		// TODO Auto-generated method stub
		VoidLabelResponse voidLabel = new VoidLabelResponse();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		final String url = "https://api.shipengine.com/v1/labels/"+apiLabelId+"/void";
		
		try {
			
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.set("X-COM-PERSIST", "NO");
		    headers.set("X-COM-LOCATION", "USA");
		    headers.set("Host", "api.shipengine.com");
		    headers.set("API-Key", "TEST_G+OAfjQ+8JjUHIKOXNwgz9DYDbsAPJeoc2oMpadZezk");
		    HttpEntity<String> entity = new HttpEntity<String>(headers);
		    ResponseEntity<VoidLabelResponse> response = restTemplate.exchange(url, HttpMethod.PUT, entity, VoidLabelResponse.class);
		    voidLabel = response.getBody();
		    if (voidLabel.getApproved())
		    	shipService.cancelShipment(shippingId);
		    System.out.println("Label Cancelled" + voidLabel.getMessage());
		    
		} catch (Exception e) {
			System.out.println("ERROR Canceling Label..." + e.getCause());
		}
		
		
		return voidLabel;
	}
	
	

}
