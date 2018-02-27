package com.smurf.SmurfShop.test.utils;

import java.io.File;
import java.util.Map;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestApiHelper {
	public static RestResponse performGetRequest(String url, Map<String,String> headers){
		CloseableHttpResponse response=null;
		try{
			HttpGet get=new HttpGet(url);
			if(headers !=null){
				for (String str : headers.keySet()){
					get.addHeader(str, headers.get(str));
				}
			}
			CloseableHttpClient client = HttpClientBuilder.create().build();
			response = client.execute(get);
			ResponseHandler<String> body = new BasicResponseHandler();
			RestResponse restResponse =new RestResponse(response
					.getStatusLine().getStatusCode(),
					body.handleResponse(response));
			client.close();
			response.close();
			return restResponse;
		}catch(Exception e){
			if (e instanceof HttpResponseException){
				return new RestResponse(response.getStatusLine().getStatusCode(),e.getMessage());
			}
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	public static RestResponse performPostRequest(String url, String fileName, Map<String,String>headers){
		CloseableHttpResponse response=null;
		HttpPost post = new HttpPost(url);
		if(headers !=null){
			for (String str : headers.keySet()){
				post.addHeader(str, headers.get(str));
			}
		}
		File file =new File(fileName);
		FileEntity data=new FileEntity(file,ContentType.APPLICATION_JSON);
		post.setEntity(data);
		try{
			CloseableHttpClient client = HttpClientBuilder.create().build();
			response = client.execute(post);
			ResponseHandler<String> handler = new BasicResponseHandler();
			return new RestResponse(response.getStatusLine().getStatusCode(),handler.handleResponse(response));
			
		}
		catch(Exception e){
			if (e instanceof HttpResponseException){
				return new RestResponse(response.getStatusLine().getStatusCode(),e.getMessage());
			}
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	public static RestResponse performDeleteRequest(String url) {
		CloseableHttpResponse response = null;
		HttpDelete delete = new HttpDelete(url);
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			response = client.execute(delete);
			ResponseHandler<String> handler = new BasicResponseHandler();
			return new RestResponse(response.getStatusLine().getStatusCode(),
					handler.handleResponse(response));

		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				return new RestResponse(response.getStatusLine()
						.getStatusCode(), e.getMessage());
			}
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestResponse performPutRequest(String url, String fileName, Map<String,String>headers){
		CloseableHttpResponse response=null;
		HttpPut put = new HttpPut(url);
		if(headers !=null){
			for (String str : headers.keySet()){
				put.addHeader(str, headers.get(str));
			}
		}
		File file =new File(fileName);
		FileEntity data=new FileEntity(file,ContentType.APPLICATION_JSON);
		put.setEntity(data);
		try{
			CloseableHttpClient client = HttpClientBuilder.create().build();
			response = client.execute(put);
			ResponseHandler<String> handler = new BasicResponseHandler();
			return new RestResponse(response.getStatusLine().getStatusCode(),handler.handleResponse(response));
			
		}
		catch(Exception e){
			if (e instanceof HttpResponseException){
				return new RestResponse(response.getStatusLine().getStatusCode(),e.getMessage());
			}
			throw new RuntimeException(e.getMessage(),e);
		}
	}
}
