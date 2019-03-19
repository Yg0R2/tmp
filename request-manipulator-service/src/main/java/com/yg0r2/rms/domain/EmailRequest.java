package com.yg0r2.rms.domain;

import java.util.UUID;

public interface EmailRequest {

    UUID getRequestId();

    RequestContext getRequestContext();

}
