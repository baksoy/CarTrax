package com.thinkful.cartrax.services.impl;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.thinkful.cartrax.services.CartraxContract;
import com.thinkful.cartrax.services.VehicleService;
import com.thinkful.contract.dto.VehicleMakeDto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by neville on 25/06/15.
 */
public class VehicleServiceImpl implements VehicleService {

    private static final VehicleService INSTANCE = new VehicleServiceImpl();

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    private VehicleServiceImpl() {
        restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList();
        httpMessageConverters.add(jacksonMessageConverter());
        restTemplate.setMessageConverters(httpMessageConverters);
    }

    public static VehicleService getInstance() {
        return INSTANCE;
    }

    @Override
    public VehicleMakeDto createVehicleMake(String make) {
        VehicleMakeDto makeRequestDto = new VehicleMakeDto();
        makeRequestDto.setName(make);

        HttpEntity<VehicleMakeDto> payload = new HttpEntity<VehicleMakeDto>(makeRequestDto);
        ResponseEntity<VehicleMakeDto> response = restTemplate.exchange(
                        CartraxContract.ENDPOINT + "/vehicle-make",
                        HttpMethod.POST,
                        payload,
                        VehicleMakeDto.class);

        return response.getBody();
    }


    @Override
    public List<VehicleMakeDto> getVehicleMakes() {
        ResponseEntity<VehicleMakeDto[]> response = restTemplate.exchange(
                CartraxContract.ENDPOINT + "/vehicle-makes",
                HttpMethod.GET,
                null,
                VehicleMakeDto[].class);

        return Arrays.asList(response.getBody());
    }

    private HttpMessageConverter jacksonMessageConverter()
    {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper());

        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.TEXT_HTML);

        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        return mappingJackson2HttpMessageConverter;
    }

    private ObjectMapper objectMapper() {
        if (objectMapper == null) {
            objectMapper = new CartraxObjectMapper();
            objectMapper.registerModule(new JodaModule());
        }
        return objectMapper;
    }
}
