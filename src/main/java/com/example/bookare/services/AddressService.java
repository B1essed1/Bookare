package com.example.bookare.services;

import com.example.bookare.models.AddressDto;
import com.example.bookare.models.result.ApiResponse;

public interface AddressService {
    ApiResponse getRegions();

    ApiResponse getDistricts(AddressDto addressDto);

    ApiResponse getQuarters(AddressDto addressDto);
}
