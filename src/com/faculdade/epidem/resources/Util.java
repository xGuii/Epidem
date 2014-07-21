package com.faculdade.epidem.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.util.Log;

import com.faculdade.epidem.model.Coordenada;
import com.faculdade.epidem.model.Opcao;

public class Util {

	public static final String TAG = "Util";
	
	public Util() {
	}
	
	/**
	 * inputStreamToString: Converte um InputStream para uma cadeia de caracteres (String)
	 * @param stream
	 * @return String
	 */
	public static String inputStreamToString(InputStream stream){
		String line = "";
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		try {
			while((line = reader.readLine()) != null){
				builder.append(line);
			}
		} catch (IOException e) {
			
		}
		
		return builder.toString();
	}

	/**
	 * jsonStringToCoordinates: Converte um objeto Json para uma lista de coordenadas
	 * @param jString
	 * @return ArrayList<Coordenada>
	 */
	public static ArrayList<Coordenada> jsonStringToCoordinates(String jString){
		ArrayList<Coordenada> coordenadas = new ArrayList<Coordenada>();
		
		JSONObject jObject;
		try {
			jObject = new JSONObject(jString);
			JSONArray jArray = jObject.getJSONArray("coordenada");
			
			// Grava o numero de entradas no log
			Log.i(TAG, "Entradas: " + jArray.length());
			
			for(int i = 0; i < jArray.length(); i++){
				JSONObject jsonObject = jArray.getJSONObject(i);
				
				Coordenada coordenada = new Coordenada();
				coordenada.setDescricao(jsonObject.getString("descricao"));
				coordenada.setLatitude(Float.parseFloat(jsonObject.getString("latitude")));
				coordenada.setLongitude(Float.parseFloat(jsonObject.getString("longitude")));
				coordenada.setId(Integer.parseInt(jsonObject.getString("id")));
				coordenada.setTipoTitle(jsonObject.getString("tipoTitle"));
				
				coordenadas.add(coordenada);
			}
		} catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage(), e);
		}
		
		return coordenadas;
	}
	
	/**
	 * jsonStringToOpcao: Converte um objeto Json para uma lista de opcoes
	 * @param jString
	 * @return ArrayList<Opcao>
	 */
	public static ArrayList<Opcao> jsonStringToOpcao(String jString){
		ArrayList<Opcao> opcoes = new ArrayList<Opcao>();
		JSONObject jObject;
		try {
			jObject = new JSONObject(jString);
			JSONArray jArray = jObject.getJSONArray("opcao");
			
			// Grava o numero de entradas no log
			Log.i(TAG, "Entradas: " + jArray.length());
			
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonObject = jArray.getJSONObject(i);
				
				Opcao opcao = new Opcao();
				opcao.setTitulo(jsonObject.getString("titulo"));
				opcao.setId(Integer.parseInt(jsonObject.getString("id")));
				
				opcoes.add(opcao);
				
			}
			
		} catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage(), e);
		}
		
		return opcoes;
	}
	
	/**
	 * getBaseUri: Obtem a uri do webservice
	 * @param path
	 * @return URI
	 */
	public static URI getBaseUri(String path){
		//http://10.0.1.19:3390/WebService/rest/coordenada
		return URI.create("http://192.168.0.111:3390/WebService/"+path);
	}

	/**
	 * showProgressDialog: Cria um modal de progresso enquanto as acoes estao em andamento
	 * @param dialog
	 * @param context
	 * @param message
	 */
	public static ProgressDialog showProgressDialog(Context context, String message){
		WallpaperManager wp = WallpaperManager.getInstance(context);
		
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage(message);
		dialog.setProgressDrawable(wp.getDrawable());
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(false);
		dialog.show();
		
		return dialog;
	}
	
}
