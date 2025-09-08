package org.apitest.apiprism.transferobjects;

import lombok.Builder;
import lombok.Data;
import org.apache.http.Header;


import java.util.List;
import java.util.Map;

@Data
@Builder
public class PrismResponse {
    private String uri;
    private Map<String, Object> hostDetails;
    private String protocol;
    private String method;
    private List<ResponseDatum> responseLines;
    private Header[] requestHeaders;
    private long totalExecutionTimeMs;
    private AuditInfo auditInfo;

}

@Data
@Builder
class AuditInfo {
    private long apiRequestTime;
    private long apiResponseTime;
}
