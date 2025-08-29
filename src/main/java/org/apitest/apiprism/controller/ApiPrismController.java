package org.apitest.apiprism.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apitest.apiprism.intf.ApiPrismInterface;
import org.apitest.apiprism.transferobjects.InputPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 */

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
@Slf4j
public class ApiPrismController {

    private final ApiPrismInterface apiPrismService;

    /**
     * Api used to invoke Rest APIs
     * @param inputPayload {@link InputPayload}
     * @return Response Object {@link ResponseEntity<Object>}
     */
    @PostMapping("/test")
    public ResponseEntity<Object> makeCall(@Valid @RequestBody final InputPayload inputPayload){
        try {
            Object response = apiPrismService.makeRestCall(inputPayload);
            log.info("Tested Successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while testing api with path {}, ERROR : {}", inputPayload.getUri(), e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
