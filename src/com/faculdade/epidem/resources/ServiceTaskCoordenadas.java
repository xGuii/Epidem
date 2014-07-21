package com.faculdade.epidem.resources;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.faculdade.epidem.interfaces.OnTaskCompleted;
import com.faculdade.epidem.model.Coordenada;

public class ServiceTaskCoordenadas extends WebService {
	private static final String TAG = "ServiceTaskCoordenada";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String DESCRICAO = "descricao";
	private static final String TIPO = "tipo";

	private OnTaskCompleted listener;
	private String message = "Buscando dados...";
	private ProgressDialog dialog = null;
	private Coordenada coordenada = null;
	private Context context;
	
	public ServiceTaskCoordenadas() {
		
	}
	
	public ServiceTaskCoordenadas(Context context, String message, OnTaskCompleted listener){
		this.context = context;
		this.message = (message != "") ? message : this.message;
		this.listener = listener;
	}
	
	public ServiceTaskCoordenadas(Context context, String message, OnTaskCompleted listener, Coordenada coordenada) {
		this(context, message, listener);
		this.coordenada = coordenada;
	}
	
	private boolean postCoordenadas(){
		params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair(LATITUDE, String.valueOf(coordenada.getLatitude())));
		params.add(new BasicNameValuePair(LONGITUDE, String.valueOf(coordenada.getLongitude())));
		params.add(new BasicNameValuePair(DESCRICAO, coordenada.getDescricao()));
		params.add(new BasicNameValuePair(TIPO, String.valueOf(coordenada.getTipo())));
		
		boolean insert = post(Util.getBaseUri("coordenada"));
		
		return insert;
	}
	
	private ArrayList<Coordenada> getCoordenadas(){
		ArrayList<Coordenada> coordenadas = new ArrayList<Coordenada>();
		String JSONString = get(Util.getBaseUri("coordenada"));
		coordenadas = Util.jsonStringToCoordinates(JSONString);
		
		return coordenadas;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = Util.showProgressDialog(context, message);
	}
	
	@Override
	protected Object doInBackground(Void... params) {
		Object result = null;
		try {
			if(this.coordenada != null){
				result = postCoordenadas();
			} else {
				result = getCoordenadas();
			}
		} catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage(), e);
		}
		return result;
	}

	@Override
	protected void onPostExecute(Object result) {
		this.handleResponse(result);
		dialog.dismiss();
	}

	/**
	 * handleResponse: enviar os valores de resposta para a classe de origem
	 * @param response
	 */
	private void handleResponse(Object response){
		listener.onTaskCompleted(response);
	}

}
