package qcm.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	public static String accessToken;
	private static HttpContext httpContext;

	private static void setToken(HttpRequestBase request) {
		request.setHeader("Authorization", "Bearer " + accessToken);
	}

	private static HttpContext getHttpContext() {
		if (httpContext == null) {
			CookieStore cookieStore = new BasicCookieStore();
			httpContext = new BasicHttpContext();
			httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
		}
		return httpContext;
	}

	private static String sendHTML(HttpEntityEnclosingRequestBase request, Map<String, Object> params) throws ClientProtocolException, IOException {
		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

			for (Entry<String, Object> entry : params.entrySet()) {
				String value = null;
				if (entry.getValue() != null)
					value = (String) entry.getValue().toString();
				postParameters.add(new BasicNameValuePair(entry.getKey(), value));
			}
			setToken(request);
			request.setEntity(new UrlEncodedFormEntity(postParameters, "utf-8"));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			result = httpClient.execute(request, responseHandler, getHttpContext());
		} finally {
			httpClient.close();
		}
		return result;
	}

	public static String getHTML(String urlToRead) throws ClientProtocolException, IOException {
		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet getRequest = new HttpGet(urlToRead);
			setToken(getRequest);
			HttpResponse response;
			response = httpClient.execute(getRequest, getHttpContext());

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, HTTP.UTF_8);
				}
			}
		} finally {
			httpClient.close();
		}
		return result;
	}

	public static String postHTML(String urlToRead, Map<String, Object> params) throws ClientProtocolException, IOException {
		System.out.println("POST:" + urlToRead);
		return sendHTML(new HttpPost(urlToRead), params);
	}

	public static String putHTML(String urlToRead, Map<String, Object> params) throws ClientProtocolException, IOException {
		return sendHTML(new HttpPut(urlToRead), params);
	}

	public static String deleteHTML(String urlToRead) throws ClientProtocolException, IOException {
		String result = "";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpDelete deleteRequest = new HttpDelete(urlToRead);
			setToken(deleteRequest);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			result = httpClient.execute(deleteRequest, responseHandler, getHttpContext());
		} finally {
			httpClient.close();
		}
		return result;
	}
}
