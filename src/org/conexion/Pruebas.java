package org.conexion;


import java.util.HashMap;
import java.util.Map;
import org.conexion.ConectorCloudERP;
import com.google.gson.JsonObject;

public class Pruebas {

	public static void main(String[] args) {
		pruebaGet();
	
//		pruebaPost();
	}

	private static void pruebaPost() {
		ConectorCloudERP con = new ConectorCloudERP("http://localhost:8091/api/email/", "");
		Map<String,String> params = new HashMap<String,String>();
		con.isProduction(true);
//		params.put("esquema", "MoraPanty");
		JsonObject jsonEmail = new JsonObject();
		jsonEmail.addProperty("smtpHost", "smtp.gmail.com");
		jsonEmail.addProperty("smtpPort", 587);
		jsonEmail.addProperty("mailUser", "matiaseze98@gmail.com");
		jsonEmail.addProperty("mailUserPassword", "whtmpbkgsvlrbcuf");
		jsonEmail.addProperty("isSMTPStartTLSEnable", true);
		jsonEmail.addProperty("isSMTPHostTrusted", true);
		jsonEmail.addProperty("fromEmail", "matiaseze98@gmail.com");
		jsonEmail.addProperty("toEmail", "matiasgoni@clouderp.com.ar");
		jsonEmail.addProperty("ccEmail", "");
		jsonEmail.addProperty("subject", "Prueba Microservicio 2");
		jsonEmail.addProperty("content", "Prueba Microservicio 2");
		jsonEmail.addProperty("htmlContent", false);
		jsonEmail.addProperty("schema", "MoraPanty");
		jsonEmail.addProperty("filename", "IA-Algoritmos_Geneticos_1C2022.pdf");	
		ResponseCloudERP resp = con.post("send", params, jsonEmail.toString());
		System.out.println(resp.getBodyString());
		System.out.println(" Envio correcto: " + resp.getSuccessful());
		
//		JsonObject jsonResp = new Gson().fromJson(resp, JsonObject.class);
//		System.out.println(jsonResp);
	}

	private static void pruebaGet() {
		ConectorCloudERP con = new ConectorCloudERP("https://www.dolarsi.com/api/", "");
		Map<String,String> params = new HashMap<String,String>();
		params.put("type", "valoresprincipales");
		System.out.print(con.get("api.php", params));
	}

}
