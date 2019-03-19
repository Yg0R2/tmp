package com.yg0r2.rms.service;

import com.yg0r2.rms.domain.EmailRequest;

public class EmailServiceRequestFactory<T extends EmailRequest> {

    private final ResourcesUtils<T> resourcesUtils;
    private final String resourceJsonPathPattern;

    public EmailServiceRequestFactory(ResourcesUtils<T> resourcesUtils, String resourceJsonPathPattern) {
        this.resourcesUtils = resourcesUtils;
        this.resourceJsonPathPattern = resourceJsonPathPattern;
    }

    public T create() {
        return resourcesUtils.read(resourceJsonPathPattern);
    }

}
