package org.apitest.apiprism.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apitest.apiprism.enums.RestMethod;
import org.apitest.apiprism.executor.RestExecutor;
import org.apitest.apiprism.transferobjects.InputPayload;
import org.apitest.apiprism.transferobjects.PrismResponse;
import org.apitest.apiprism.transferobjects.ResponseDatum;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class RestInvocationHelper {

    private final RestExecutor restExecutor;

    /**
     * Method to create Thread Pool to execute GET API in multi-thread
     *
     * @param inputPayload {@link InputPayload}
     * @return {@link PrismResponse}
     */
    public PrismResponse getCall(final InputPayload inputPayload) {
        log.info("Input payload : {} for GET API", inputPayload.toString());
        List<ResponseDatum> responseLines = new ArrayList<>();
        try (ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000)) {{
            allowCoreThreadTimeOut(true);
        }}) {
            CountDownLatch countDownLatch = new CountDownLatch(inputPayload.getNoOfHits());
            long start = System.currentTimeMillis();
            for (int i = 0; i < inputPayload.getNoOfHits(); i++) {
                threadPoolExecutor.execute(() -> responseLines.add(restExecutor.executeGetApi(inputPayload, countDownLatch)));
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("Error in count down latch of get call, ERROR : {}", e.getMessage());
            }
            long end = System.currentTimeMillis();
            return PrismResponse.builder()
                    .uri(inputPayload.getUri())
                    .protocol("REST")
                    .method(RestMethod.GET.name())
                    .responseLines(responseLines)
                    .totalExecutionTimeMs(end - start)
                    .hostDetails(getHostDetails(inputPayload.getUri())).build();
        } catch (Exception e) {
            return PrismResponse.builder()
                    .uri(inputPayload.getUri())
                    .responseLines(List.of(ResponseDatum.builder().responseData(e.getMessage()).build()))
                    .hostDetails(getHostDetails(inputPayload.getUri())).build();
        }
    }

    /**
     * Method to create Thread Pool to execute POST API in multi-thread
     *
     * @param inputPayload {@link InputPayload}
     * @return {@link PrismResponse}
     */
    public PrismResponse postCall(final InputPayload inputPayload) {
        log.info("Input payload : {} for POST API", inputPayload.toString());
        List<ResponseDatum> responseLines = new ArrayList<>();
        try (ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000)) {{
            allowCoreThreadTimeOut(true);
        }}) {
            CountDownLatch countDownLatch = new CountDownLatch(inputPayload.getNoOfHits());
            long start = System.currentTimeMillis();
            for (int i = 0; i < inputPayload.getNoOfHits(); i++) {
                threadPoolExecutor.execute(() -> responseLines.add(restExecutor.executePostApi(inputPayload, countDownLatch)));
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error("Error in count down latch of post call, ERROR : {}", e.getMessage());
            }
            long end = System.currentTimeMillis();
            return PrismResponse.builder()
                    .uri(inputPayload.getUri())
                    .protocol("REST")
                    .method(RestMethod.POST.name())
//                    .requestHeaders(httpPost.getAllHeaders())
                    .responseLines(responseLines)
                    .totalExecutionTimeMs(end - start)
                    .hostDetails(getHostDetails(inputPayload.getUri())).build();
        } catch (Exception e) {
            return PrismResponse.builder()
                    .uri(inputPayload.getUri())
                    .responseLines(List.of(ResponseDatum.builder().responseData(e.getMessage()).build()))
                    .hostDetails(getHostDetails(inputPayload.getUri())).build();
        }
    }

    public Object deleteCall(final InputPayload inputPayload) {
        return null;
    }

    public Object putCall(final InputPayload payload) {
        return null;
    }

    private static Map<String, Object> getHostDetails(final String uriString) {
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
        } catch (Exception e) {
            hostDetails.put("errorMessage", e.getMessage());
        }
        return hostDetails;
    }
}
