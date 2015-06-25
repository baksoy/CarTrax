package com.thinkful.cartrax.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 * Created by neville on 25/06/15.
 */
public class CartraxObjectMapper extends ObjectMapper {

    public CartraxObjectMapper() {
        super();
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));

    }
}
