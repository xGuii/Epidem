package com.faculdade.epidem.interfaces;

import java.net.URI;

import org.apache.http.HttpResponse;

public interface IWebService {
	public HttpResponse doPostResponse(URI uri);
	public HttpResponse doGetResponse(URI uri);
	public boolean post(URI uri);
	public String get(URI uri);
}
