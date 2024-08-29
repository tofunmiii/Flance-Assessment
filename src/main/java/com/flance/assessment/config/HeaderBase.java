package com.flance.assessment.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HeaderBase {
    private String sourceCode;
    private String requestId;
    private String requestType;
    private String responseCode;
    private String responseMessage;
}

