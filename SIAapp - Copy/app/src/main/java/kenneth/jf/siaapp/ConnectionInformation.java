package kenneth.jf.siaapp;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Kenneth on 12/10/2016.
 */

public class ConnectionInformation {

    protected String data;
    protected String url;
    protected RestTemplate restTemplate;
    protected boolean isAuthenticated;
    protected HttpHeaders headers = new HttpHeaders();
    public void setHeaders(HttpHeaders h){
        this.headers = h;
    }
    public HttpHeaders getHeaders(){
        return this.headers;
    }
    public void setIsAuthenticated(boolean t){
        this.isAuthenticated = t;
    }
    public boolean getAuthenticated(){

        return isAuthenticated;
    }
    public String getData() {
        return data;
    }
    public RestTemplate getRestTemplate() {

        return restTemplate;
    }
    public void setData(String data) {
        this.data = data;
    }
    public void setRestTemplate(RestTemplate rest){

        this.restTemplate = rest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private static final ConnectionInformation holder = new ConnectionInformation();
    public static ConnectionInformation getInstance() {return holder;}
}
