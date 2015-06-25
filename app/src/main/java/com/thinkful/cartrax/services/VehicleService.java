package com.thinkful.cartrax.services;

import com.thinkful.contract.dto.VehicleMakeDto;

import java.util.List;

/**
 * Created by neville on 25/06/15.
 */
public interface VehicleService {

    VehicleMakeDto createVehicleMake(String make);

    List<VehicleMakeDto> getVehicleMakes();
}
