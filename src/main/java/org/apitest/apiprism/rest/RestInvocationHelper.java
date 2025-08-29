package org.apitest.apiprism.rest;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apitest.apiprism.httpclient.RestClient;
import org.apitest.apiprism.transferobjects.InputPayload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestInvocationHelper {

    private final RestClient restClient;

    public Object getCall(InputPayload inputPayload){
        try(CloseableHttpClient httpClient = restClient.customClient()){
            HttpGet httpGet = new HttpGet(inputPayload.getUri());

            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("User-Agent", "Java-Client");
            HttpResponse response = httpClient.execute(httpGet);

            return restClient.readResponse(response);
        } catch (Exception e){
            return e.getMessage();
        }
    }
}
