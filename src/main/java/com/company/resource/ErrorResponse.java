package com.company.resource;

import lombok.Data;
import java.io.Serializable;

@Data
public class ErrorResponse implements Serializable {
    private String message;
}
