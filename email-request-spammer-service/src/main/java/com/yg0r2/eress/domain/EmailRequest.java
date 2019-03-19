package com.yg0r2.eress.domain;

import java.util.UUID;

public interface EmailRequest {

    UUID getRequestId();

    RequestContext getRequestContext();

}
