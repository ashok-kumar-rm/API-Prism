package org.apitest.apiprism.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apitest.apiprism.intf.ApiPrismInterface;
import org.apitest.apiprism.transferobjects.InputPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ApiPrismController {

    private final ApiPrismInterface apiPrismService;

    @GetMapping("/prism")
    public String index() {
        return apiPrismService.index();
    }

    @PostMapping("/test")
    public ResponseEntity<Object> test(@Valid @RequestBody InputPayload inputPayload){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Object response = objectMapper.writeValueAsString(apiPrismService.makeRestCall(inputPayload));
            log.info("Tested Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while testing api with path {}, ERROR : {}", inputPayload.getUri(), e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
