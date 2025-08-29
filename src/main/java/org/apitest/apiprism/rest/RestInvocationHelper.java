package org.apitest.apiprism.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apitest.apiprism.httpclient.RestClient;
import org.apitest.apiprism.transferobjects.InputPayload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestInvocationHelper {

    private final RestClient restClient;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Object getCall(final InputPayload inputPayload){
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
    public Object postCall(final InputPayload inputPayload){
        try(CloseableHttpClient httpClient = restClient.customClient()){
            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(inputPayload.getPayload()));
            HttpPost httpPost = new HttpPost(inputPayload.getUri());
            httpPost.setHeader("User-Agent", "Java-Client");
            httpPost.setHeader("Content-Type","application/json");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            return restClient.readResponse(response);
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public Object deleteCall(final InputPayload inputPayload){
        return null;
    }

    public Object putCall(final InputPayload payload){
        return null;
    }
}
