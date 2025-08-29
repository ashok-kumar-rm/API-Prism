package org.apitest.apiprism.intf;

import org.apitest.apiprism.transferobjects.InputPayload;

/**
 *
 */

public interface ApiPrismInterface {
    /**
     *
     * @param inputPayload {@link InputPayload}
     * @return Response Object {@link Object}
     */
    Object makeRestCall(final InputPayload inputPayload);
}
