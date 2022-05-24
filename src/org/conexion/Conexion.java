package org.conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class Conexion {

	private String api_url_test;
	
	private String api_url_produccion;
	
	private String credential;

	private String apyKey;
	
	private String secretKey;
	
	public Conexion(String url_prod, String url_test){
		this.api_url_produccion = url_prod;
		this.api_url_test = url_test;
	}
	
	public String getApi_url_test() {
		return api_url_test;
	}
	public void setApi_url_test(String api_url_test) {
		this.api_url_test = api_url_test;
	}
	public String getApi_url_produccion() {
		return api_url_produccion;
	}
	public void setApi_url_produccion(String api_url_produccion) {
		this.api_url_produccion = api_url_produccion;
	}
	public String getCredential() {
		return credential;
	}
	public void setCredential(String credential) {
		this.credential = credential;
	}
	public boolean isInProduction() {
		return inProduction;
	}
	public void setInProduction(boolean inProduction) {
		this.inProduction = inProduction;
	}
	
	public String getApyKey() {
		return apyKey;
	}
	public void setApyKey(String apyKey) {
		this.apyKey = apyKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	private boolean inProduction = true;
	
	public void isProduction(boolean inProduction){
		this.inProduction = inProduction;
	}

	public String get(String path, Map<String, String> params){
		
		String response = null;
		try {
			response = crearConexion(prepareApiUrl(path,parametersToUrl(params)),"GET", null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	} 
	
	public String put(String path, Map<String, String> params, String body){
		StringBuffer bodyConCFE = new StringBuffer(body);
		String response = null;
		try {
			response = crearConexion(prepareApiUrl(path,parametersToUrl(params)), "PUT", bodyConCFE.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	} 
	
	public String post(String path, Map<String, String> params, String body){
		String response = null;
		try {
			response = crearConexion(prepareApiUrl(path,parametersToUrl(params)), "POST", body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	} 
	
	public String crearConexion(String url, String method, String body) throws IOException{
		
		URL urlFacturu = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) urlFacturu.openConnection();
		connection.setRequestMethod(method);
		connection.setDoOutput(true);
		connection.setDoInput(true);
		OutputStreamWriter writer = null;
		
		connection.setRequestProperty("Authorization", this.getCredenciales());
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		if (body != null && !body.isEmpty()) {
			writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(body);
			writer.flush();
			writer.close();
		}
		BufferedReader reader = null;
		reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String response = recorrerBuffered(reader);
		reader.close();
		connection.disconnect();
		return response;
	}
	
	private String getCredenciales() {
		if(this.inProduction){
			return this.apyKey + "/" + this.secretKey;
		}else{
			return this.credential;
		}
	}
	
	
	private static String recorrerBuffered(BufferedReader reader) {
		String auxLine;
		StringBuffer auxResponse = new StringBuffer();
		try {
			while ((auxLine = reader.readLine()) != null) {
				auxResponse.append(auxLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return auxResponse.toString();		
	}
	
	private String parametersToUrl(Map<String, String> params) {
		String query = "";
		for (String key : params.keySet()) {
			String value = params.get(key);
			query = query + key + "=" + value + "&";

		}
		return query;
	}
	
	private String prepareApiUrl(String path, String query) {
		return getURL() + path + "?" + query;
	}
	public String getURL() {
		if(this.inProduction){
			return this.api_url_produccion;
		}else{
			return this.api_url_test;
		}
	}


}
