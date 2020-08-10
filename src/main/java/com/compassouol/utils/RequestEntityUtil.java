package com.compassouol.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class RequestEntityUtil<T> {

    public static HashMap getValuesFromFilter(String filter) throws Exception {
        try {
            return filter == null || filter.isEmpty() || filter.equals("{}") ?
                    new HashMap() :
                    new ObjectMapper().readValue(filter, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new Exception("Invalid filter. Example: { \"id\" : 1 }");
        }
    }
}
