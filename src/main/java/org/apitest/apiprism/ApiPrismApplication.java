package org.apitest.apiprism;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApiPrismApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiPrismApplication.class, args);
	}

    @GetMapping("/api/test")
    public ResponseEntity<String> apiPrism() {
        return new ResponseEntity<>("API Prism is UP !", HttpStatus.OK);
    }

}
