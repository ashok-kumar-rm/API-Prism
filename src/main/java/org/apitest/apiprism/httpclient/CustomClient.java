package org.apitest.apiprism.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class CustomClient {

    private RequestConfig getRequestConfig() {
        return RequestConfig.custom().setConnectTimeout(30*1000).build();
    }

    public CloseableHttpClient customClient() throws Exception {
        try {
            return HttpClients.custom().setDefaultRequestConfig(getRequestConfig()).build();
        }catch (Exception e){
            throw new Exception("Error in Client");
        }
    }

    public String readResponse(HttpResponse response) throws IOException {
        BufferedReader bufferReader = null;
        StringBuilder result = new StringBuilder();
        try {
            bufferReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
                result.append(line);
            }
        } finally {
            if (bufferReader != null)
                bufferReader.close();
        }
        return result.toString();
    }
}
