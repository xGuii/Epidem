package com.faculdade.epidem.resources;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.faculdade.epidem.interfaces.OnTaskCompleted;
import com.faculdade.epidem.model.Opcao;

public class ServiceTaskOpcoes extends WebService {
	private static final String TAG = "ServiceTaskCoordenada";

	private OnTaskCompleted listener;
	private String message = "Buscando dados...";
	private ProgressDialog dialog = null;
	private Context context;

	public ServiceTaskOpcoes() {
		
	}
	
	public ServiceTaskOpcoes(Context context, String message, OnTaskCompleted listener){
		this.context = context;
		this.message = (message != "") ? message : this.message;
		this.listener = listener;		
	}
	
	private ArrayList<Opcao> getOpcoes(){
		ArrayList<Opcao> opcoes = new ArrayList<Opcao>();
		String JSONString = get(Util.getBaseUri("opcao"));
		opcoes = Util.jsonStringToOpcao(JSONString);
		
		return opcoes;
	}


	@Override
	protected Object doInBackground(Void... params) {
		ArrayList<Opcao> result = new ArrayList<Opcao>();
		try {
			result = getOpcoes();
		} catch (Exception e) {
			Log.i(TAG, e.getLocalizedMessage(), e);
		}
		return result;
	}
	

	@Override
	protected void onPreExecute() {
		dialog = Util.showProgressDialog(context, message);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	protected void onPostExecute(Object result) {
		this.handleResponse((ArrayList<Opcao>) result);
		dialog.dismiss();
	}
	
	/**
	 * handleResponse: enviar os valores de resposta para a classe de origem
	 * @param response
	 */
	private void handleResponse(ArrayList<Opcao> response){
		listener.onTaskCompleted(response);
	}

}
