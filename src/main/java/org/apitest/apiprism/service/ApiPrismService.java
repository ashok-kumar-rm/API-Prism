package org.apitest.apiprism.service;

import lombok.RequiredArgsConstructor;
import org.apitest.apiprism.intf.ApiPrismInterface;
import org.apitest.apiprism.rest.RestInvocationHelper;
import org.apitest.apiprism.transferobjects.InputPayload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiPrismService implements ApiPrismInterface {

    private final RestInvocationHelper restInvocationHelper;

    @Override
    public String index() {
        return "Prism Service";
    }

    @Override
    public Object makeRestCall(InputPayload inputPayload) {
        return restInvocationHelper.getCall(inputPayload);
    }
}
