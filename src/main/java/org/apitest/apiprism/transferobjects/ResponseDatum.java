package org.apitest.apiprism.transferobjects;

import lombok.Builder;
import lombok.Data;
import org.apache.http.Header;
import org.apache.http.StatusLine;

@Data
@Builder(toBuilder = true)
public class ResponseDatum {

    private StatusLine status;
    private String responseData;
    private long responseTimeMs;
    private Header[] responseHeaders;

}
