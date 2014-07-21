package com.faculdade.epidem.resources;

import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import android.os.AsyncTask;
import android.util.Log;

import com.faculdade.epidem.dao.HttpClientSingleton;
import com.faculdade.epidem.interfaces.IWebService;

public class WebService extends AsyncTask<Void, Object, Object> implements IWebService {
	public static final String TAG = "WebService operation";
	public ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
	
	public WebService() {
		
	}
	
	/**
	 * doPostResponse: metood para verificar resposta e enviar valores e mensagens ao servidor
	 * @param uri
	 * @return HttpResponse
	 */
	@Override
	public final HttpResponse doPostResponse(URI uri){
		HttpResponse response = null;
		try{
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			response = HttpClientSingleton.getHttpClientInstace().execute(httpPost);
		} catch(Exception e) {
			Log.i(TAG, "Falha a obter resposta: " + e.getLocalizedMessage(), e);
		}
		return response;
	}
	
	/**
	 * doGetResponse: metodo para verificar e obter resposta Http do servidor
	 * @param uri
	 * @return HttpResponse
	 */
	@Override
	public final HttpResponse doGetResponse(URI uri){
		HttpResponse response = null;
		try{
			HttpGet httpGet = new HttpGet(uri);
			response = HttpClientSingleton.getHttpClientInstace().execute(httpGet);
		} catch(Exception e) {
			Log.i(TAG, "Falha a obter resposta: " + e.getLocalizedMessage(), e);
		}
		return response;
	}
	
	/**
	 * Metodo de requisicao post
	 * @param uri
	 * @return boolean
	 */
	@Override
	public boolean post(URI uri){
		boolean result = false;
		HttpResponse response = doPostResponse(uri);
		
		if(response != null){
			try{
				HttpEntity entity = response.getEntity();
				result = Boolean.parseBoolean(Util.inputStreamToString(entity.getContent()));
			} catch (Exception e){
				Log.i(TAG, e.getLocalizedMessage(), e);
			}
		}
		
		return result;
	}
	
	/**
	 * Metodo de requisicao get
	 * @param uri
	 * @return String
	 */
	@Override
	public String get(URI uri){
		String result = "";
		HttpResponse response = doGetResponse(uri);
		
		if(response != null){
			try{
				HttpEntity entity = response.getEntity();
				result = Util.inputStreamToString(entity.getContent());
			} catch (Exception e){
				Log.i(TAG, e.getLocalizedMessage(), e);
			}
		}
		
		return result;
	}

	@Override
	protected Object doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}


}
