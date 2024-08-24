package com.telusko.joblisting.exception.code;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ErrorNames {
    MALFORMED_JSON_REQUEST("Malformed JSON Request"),
    ENTITY_NOT_FOUND("Entity Not Found"),
    VALIDATION_ERROR("Validation Error"),
    FAILED_TO_SAVE_IN_DB("Failed to save"),
    DUPLICATE_FOUND("Duplicate Found");

    private String name;

    ErrorNames(String name) {
        this.name = name;
    }
}
