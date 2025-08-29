package org.apitest.apiprism.service;

import lombok.RequiredArgsConstructor;
import org.apitest.apiprism.enums.RestMethod;
import org.apitest.apiprism.intf.ApiPrismInterface;
import org.apitest.apiprism.rest.RestInvocationHelper;
import org.apitest.apiprism.transferobjects.InputPayload;
import org.springframework.stereotype.Service;

/**
 *
 */

@Service
@RequiredArgsConstructor
public class ApiPrismService implements ApiPrismInterface {

    private final RestInvocationHelper restInvocationHelper;

    /**
     *
     * @param inputPayload {@link InputPayload}
     * @return Response Object {@link Object}
     */
    @Override
    public Object makeRestCall(final InputPayload inputPayload) {
        return switch (inputPayload.getMethod()) {
            case RestMethod.GET -> restInvocationHelper.getCall(inputPayload);
            case RestMethod.POST -> restInvocationHelper.postCall(inputPayload);
            case RestMethod.PUT -> restInvocationHelper.putCall(inputPayload);
            case RestMethod.DELETE -> restInvocationHelper.deleteCall(inputPayload);
        };
    }
}
