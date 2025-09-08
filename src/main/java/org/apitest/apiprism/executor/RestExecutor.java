package org.apitest.apiprism.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apitest.apiprism.constants.RestConstants;
import org.apitest.apiprism.httpclient.RestClient;
import org.apitest.apiprism.transferobjects.InputPayload;
import org.apitest.apiprism.transferobjects.ResponseDatum;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 *
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class RestExecutor {

    private final RestClient restClient;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Method to execute Post call in Thread
     *
     * @param inputPayload {@link InputPayload}
     * @param latch        {@link CountDownLatch}
     * @return {@link ResponseDatum}
     */
    public ResponseDatum executePostApi(InputPayload inputPayload, CountDownLatch latch) {
        try (CloseableHttpClient httpClient = restClient.customClient()) {
            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(inputPayload.getPayload()));
            HttpPost httpPost = new HttpPost(inputPayload.getUri());
            httpPost.setHeader("User-Agent", RestConstants.JAVA_USER_AGENT);
            httpPost.setHeader("Content-Type", RestConstants.JSON_CONTENT_TYPE);
            httpPost.setEntity(entity);
            long startTime = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(httpPost);
            long endTime = System.currentTimeMillis();
            return ResponseDatum.builder()
                    .status(response.getStatusLine())
                    .responseData(restClient.readResponse(response))
                    .responseTimeMs(endTime - startTime)
                    .responseHeaders(response.getAllHeaders())
                    .build();
        } catch (Exception e) {
            log.error("Error while executing post api, ERROR : {}", e.getMessage());
            return null;
        } finally {
            latch.countDown();
        }
    }

    /**
     * Method to execute Get call in Thread
     *
     * @param inputPayload {@link InputPayload}
     * @param latch        {@link CountDownLatch}
     * @return {@link ResponseDatum}
     */
    public ResponseDatum executeGetApi(InputPayload inputPayload, CountDownLatch latch) {
        try (CloseableHttpClient httpClient = restClient.customClient()) {
            HttpGet httpGet = new HttpGet(inputPayload.getUri());
            httpGet.setHeader("Accept", RestConstants.JSON_CONTENT_TYPE);
            httpGet.setHeader("User-Agent", RestConstants.JAVA_USER_AGENT);
            long startTime = System.currentTimeMillis();
            HttpResponse response = httpClient.execute(httpGet);
            long endTime = System.currentTimeMillis();
            return ResponseDatum.builder()
                    .status(response.getStatusLine())
                    .responseData(restClient.readResponse(response))
                    .responseTimeMs(endTime - startTime)
                    .responseHeaders(response.getAllHeaders())
                    .build();
        } catch (Exception e) {
            log.error("Error while executing get api, ERROR : {}", e.getMessage());
            return null;
        } finally {
            latch.countDown();
        }
    }
}
