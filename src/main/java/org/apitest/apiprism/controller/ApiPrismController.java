package org.apitest.apiprism.controller;

import org.apitest.apiprism.service.ApiPrismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiPrismController {
    @Autowired
    private ApiPrismService apiPrismService;

    @GetMapping("/api/prism")
    public String index() {
        return apiPrismService.index();
    }
}
