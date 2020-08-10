package com.compassouol.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseEntityUtil {

    public static ResponseEntity<?> responseHandler(Object resultData) {
        return responseHandler(resultData, null, HttpStatus.OK);
    }

    public static ResponseEntity<?> responseHandler(Object resultData, HttpStatus status) {
        return responseHandler(resultData, null, status);
    }

    public static ResponseEntity<?> responseHandler(Object resultData, Long id, HttpStatus status) {
        if (resultData == null)  return ResponseEntity.noContent().build();

        if (resultData instanceof List) {
            if (((List) resultData).isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                resultData = id == null ? resultData : ((List) resultData).get(0);
            }
        }

        return ResponseEntity.status(status).body(resultData);
    }
}
