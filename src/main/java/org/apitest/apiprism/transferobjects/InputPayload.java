package org.apitest.apiprism.transferobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputPayload {
    @JsonProperty("uri")
    @NotBlank(message = "Api Uri cannot be empty")
    private String uri;

    @JsonProperty(value = "method", defaultValue = "GET")
    private RestMethod method;

    @JsonProperty("payload")
    private Map<String,Object> payload;

}

enum RestMethod{
    GET,
    POST,
    PUT,
    DELETE
}
