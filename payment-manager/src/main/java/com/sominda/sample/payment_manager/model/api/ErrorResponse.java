package com.sominda.sample.payment_manager.model.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;
    private String description;
}
