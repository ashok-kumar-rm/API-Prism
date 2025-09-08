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
import org.apitest.apiprism.transferobjects.PrismResponse;
import org.apitest.apiprism.transferobjects.ResponseDatum;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

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
    public PrismResponse postCall(final InputPayload inputPayload){
        try(CloseableHttpClient httpClient = restClient.customClient()){
            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(inputPayload.getPayload()));
            HttpPost httpPost = new HttpPost(inputPayload.getUri());
            httpPost.setHeader("User-Agent", "Java-Client");
            httpPost.setHeader("Content-Type","application/json");
            httpPost.setEntity(entity);
            List<ResponseDatum> responseLines = new ArrayList<>();
            for(int i=0;i<inputPayload.getNoOfHits();i++) {
                long startTime = System.currentTimeMillis();
                HttpResponse response = httpClient.execute(httpPost);
                long endTime = System.currentTimeMillis();
                responseLines.add(ResponseDatum.builder()
                        .status(response.getStatusLine())
                        .responseData(restClient.readResponse(response))
                        .responseTimeMs(endTime - startTime)
                        .responseHeaders(response.getAllHeaders())
                        .build());
            }

            return PrismResponse.builder()
                    .uri(inputPayload.getUri())
                    .protocol("REST")
                    .method("POST")
                    .requestHeaders(httpPost.getAllHeaders())
                    .responseLines(responseLines)
                    .hostDetails(getHostDetails(inputPayload.getUri())).build();
        } catch (Exception e){

            return PrismResponse.builder()
                    .uri(inputPayload.getUri())
                    .responseLines(List.of(ResponseDatum.builder().responseData(e.getMessage()).build()))
                    .hostDetails(getHostDetails(inputPayload.getUri())).build();
        }
    }

    public Object deleteCall(final InputPayload inputPayload){
        return null;
    }

    public Object putCall(final InputPayload payload){
        return null;
    }

    public static Map<String, Object> getHostDetails(final String uriString){
        Map<String, Object> hostDetails = new HashMap<>();
        try {
            URI uri = URI.create(uriString);
            String host = uri.getHost();
            hostDetails.put("host", host);
            InetAddress[] inetAddresses = InetAddress.getAllByName(host);
            List<String> ipAddresses = Arrays.stream(inetAddresses)
                    .map(InetAddress::getHostAddress)
                    .collect(Collectors.toList());

            hostDetails.put("ipAddresses", ipAddresses);

            // Get the fully qualified domain name
            String canonicalHostname = inetAddresses[0].getCanonicalHostName();
            hostDetails.put("canonicalHostName", canonicalHostname);
        }  catch (Exception e){
            hostDetails.put("errorMessage", e.getMessage());
        }
        return hostDetails;
    }
}
