package org.apitest.apiprism.service;

import org.apitest.apiprism.intf.ApiPrismInterface;
import org.springframework.stereotype.Service;

@Service
public class ApiPrismService implements ApiPrismInterface {

    @Override
    public String index() {
        return "Prism Service";
    }
}
