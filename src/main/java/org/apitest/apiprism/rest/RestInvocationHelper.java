package org.apitest.apiprism.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apitest.apiprism.constants.RestConstants;
import org.apitest.apiprism.httpclient.RestClient;
import org.apitest.apiprism.transferobjects.InputPayload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestInvocationHelper {

    private final RestClient restClient;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Method to call GET Call
     * @param inputPayload {@link InputPayload}
     * @return Response Object {@link Object}
     */
    public Object getCall(final InputPayload inputPayload){
        try(CloseableHttpClient httpClient = restClient.customClient()){
            HttpGet httpGet = new HttpGet(inputPayload.getUri());

            httpGet.setHeader("Accept", RestConstants.JSON_CONTENT_TYPE);
            httpGet.setHeader("User-Agent", RestConstants.JAVA_USER_AGENT);
            long startTime = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(httpGet);
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
            return restClient.readResponse(response);
        } catch (Exception e){
            return e.getMessage();
        }
    }

    /**
     * Method to call POST Call
     * @param inputPayload {@link InputPayload}
     * @return Response Object {@link Object}
     */
    public Object postCall(final InputPayload inputPayload){
        try(CloseableHttpClient httpClient = restClient.customClient()){
            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(inputPayload.getPayload()));
            HttpPost httpPost = new HttpPost(inputPayload.getUri());
            httpPost.setHeader("User-Agent", RestConstants.JAVA_USER_AGENT);
            httpPost.setHeader("Content-Type",RestConstants.JSON_CONTENT_TYPE);
            httpPost.setEntity(entity);
            long startTime = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(httpPost);
            long endTime = System.currentTimeMillis();
            System.out.println("Response Time : "+ (endTime - startTime));
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
