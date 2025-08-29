package org.apitest.apiprism.intf;

import org.apitest.apiprism.transferobjects.InputPayload;

public interface ApiPrismInterface {
    String index();
    Object makeRestCall(InputPayload inputPayload);

}
