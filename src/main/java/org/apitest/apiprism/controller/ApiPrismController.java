package org.apitest.apiprism.controller;

import lombok.RequiredArgsConstructor;
import org.apitest.apiprism.intf.ApiPrismInterface;
import org.apitest.apiprism.service.ApiPrismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiPrismController {

    private final ApiPrismInterface apiPrismService;

    @GetMapping("/api/prism")
    public String index() {
        return apiPrismService.index();
    }
}
