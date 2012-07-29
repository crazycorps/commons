package com.jian.tools.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

public final class HttpClientUtils {

    private static final int STATUSCODE_200 = 200;
    private static final int TIME = 150;
    private static final int THOUSAND = 1000;
    
    public static final String DEFAULT_ENCODE="UTF-8";
    
    public enum RequestMethod{
    	GET,POST;
    }

    private HttpClientUtils() {
    }

    static class GzipDecompressingEntity extends HttpEntityWrapper {
        public GzipDecompressingEntity(final HttpEntity entity) {
            super(entity);
        }

        public InputStream getContent() throws IOException {
            InputStream wrappedin = wrappedEntity.getContent();
            return new GZIPInputStream(wrappedin);
        }

        public long getContentLength() {
            return -1;
        }
    }

    public static DefaultHttpClient getHttpClient() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setUserAgent(params, "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)");
        HttpClientParams.setCookiePolicy(params, CookiePolicy.BROWSER_COMPATIBILITY);
        HttpConnectionParams.setConnectionTimeout(params, TIME * THOUSAND);
        HttpConnectionParams.setSoTimeout(params, TIME * THOUSAND);
        DefaultHttpClient httpclient = new DefaultHttpClient(params);
        httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }
            }

        });
        httpclient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(final HttpResponse response, final HttpContext context) throws HttpException, IOException {
                HttpEntity entity = response.getEntity();
                Header ceheader = entity.getContentEncoding();
                if (ceheader != null) {
                    HeaderElement[] codecs = ceheader.getElements();
                    for (int i = 0; i < codecs.length; i++) {
                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                            return;
                        }
                    }
                }
            }

        });
        return httpclient;
    }

    public static String executeRequest(RequestMethod reqeustMethod,String url, List<NameValuePair> parameters,String encode) throws Exception{
    	DefaultHttpClient httpclient = HttpClientUtils.getHttpClient();
    	if(RequestMethod.GET.ordinal()==reqeustMethod.ordinal()){
    		return executeGetRequest(httpclient, url,parameters, encode);
    	}else{
    		return executePostRequest(httpclient, url, parameters, encode);
    	}
    }
    
    public static String executeRequest(HttpClient httpclient, RequestMethod reqeustMethod,String url, List<NameValuePair> parameters,String encode) throws Exception{
    	if(RequestMethod.GET.ordinal()==reqeustMethod.ordinal()){
    		return executeGetRequest(httpclient, url,parameters, encode);
    	}else{
    		return executePostRequest(httpclient, url, parameters, encode);
    	}
    }

    public static String executeGetRequest(HttpClient httpclient, String url, List<NameValuePair> parameters,String encode) throws Exception {
        InputStream input = null;
        
        if(parameters!=null){
        	url=concatUrlSerialParameters(url, parameters, true);
        }

        HttpGet get = new HttpGet(url);
        HttpResponse res = httpclient.execute(get);
        StatusLine status = res.getStatusLine();
        if (status.getStatusCode() != STATUSCODE_200) {
            throw new RuntimeException("50001");
        }
        if (res.getEntity() == null) {
            return null;
        }
        input = res.getEntity().getContent();
        
        return IOUtils.convertStreamToString(input, encode);
    }
    
    public static String executePostRequest(HttpClient httpclient, String url, List<NameValuePair> parameters,String encode) throws Exception {
        InputStream input = null;
       
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, encode);
        HttpPost post=new HttpPost(url);
        post.setEntity(entity);
        HttpResponse res = httpclient.execute(post);
        StatusLine status = res.getStatusLine();
        if (status.getStatusCode() != STATUSCODE_200) {
            throw new RuntimeException("request error status:"+status.getStatusCode());
        }
        if (res.getEntity() == null) {
            return null;
        }
        input = res.getEntity().getContent();
        
        return IOUtils.convertStreamToString(input, encode);
    }
    
    

	public static String concatUrlSerialParameters(String url,List<NameValuePair> parameters, boolean onlySerialValue) throws Exception{
		if(url==null){
			return null;
		}
		if(url.indexOf("?")<0){
			url+="?";
		}
		url+=concatSerialParameters(parameters, onlySerialValue);
		return url;
	}
    
    public static String concatSerialParameters(List<NameValuePair> parameters, boolean onlySerialValue) throws UnsupportedEncodingException {
        String str = "";
        for (int i = 0; i < parameters.size(); i++) {
            NameValuePair item = parameters.get(i);
            if (onlySerialValue) {
                str += item.getName() + "=" + URLEncoder.encode(item.getValue(), HttpClientUtils.DEFAULT_ENCODE);

            } else {
                str += item.getName() + "=" + item.getValue();
            }
            if (i < parameters.size() - 1) {
                str += "&";
            }
        }
        if (!onlySerialValue) {
            str = URLEncoder.encode(str, HttpClientUtils.DEFAULT_ENCODE);
        }
        return str;
    }
}
