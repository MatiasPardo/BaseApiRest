package org.conexion;

import java.util.HashMap;
import java.util.Map;

public class Pruebas {

	public static void main(String[] args) {
		Conexion con = new Conexion("https://www.dolarsi.com/api/", "");
		Map<String,String> params = new HashMap<String,String>();
		params.put("type", "valoresprincipales");
		System.out.print(con.get("api.php", params));
	}

}
