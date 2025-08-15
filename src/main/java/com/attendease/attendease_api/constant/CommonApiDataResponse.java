package com.attendease.attendease_api.constant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({"checkValidationFailed"})
@SuperBuilder
public class CommonApiDataResponse {

    private String message;

    @Builder.Default
    private Boolean checkValidationFailed = false;

    private String validationMessage;
}
