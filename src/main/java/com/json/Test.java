package com.json;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;	
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {

	public static void main(String[] args) {

		File file = new File("src/main/resources/JSON/setup.txt");
		try {
			JSONParser parser = new JSONParser();
			// Use JSONObject for simple JSON and JSONArray for array of JSON.
			JSONObject data = (JSONObject) parser.parse(new FileReader(file.getAbsolutePath()));// path to the JSON
																								// file.
			System.out.println(data.toJSONString());

			URL url2 = new URL("http://localhost:8080/setup.html");
			HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");

			conn.setDoOutput(true);
			OutputStream outStream = conn.getOutputStream();
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
			outStreamWriter.write(data.toJSONString());
			outStreamWriter.flush();
			outStreamWriter.close();
			outStream.close();
			String response = null;

			System.out.println(conn.getResponseCode());
			System.out.println(conn.getResponseMessage());

			DataInputStream input = null;
			input = new DataInputStream(conn.getInputStream());
			while (null != ((response = input.readLine()))) {
				System.out.println(response);
			}
			input.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
}