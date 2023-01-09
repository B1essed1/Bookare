package com.example.bookare.controllers;

import com.example.bookare.models.AddressDto;
import com.example.bookare.models.result.ApiResponse;
import com.example.bookare.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping(path = "/regions")
    public ResponseEntity<ApiResponse> getRegions(){
        ApiResponse apiResponse = addressService.getRegions();
        return apiResponse.isSuccess()?ResponseEntity.status(HttpStatus.OK).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping(path = "/districts")
    public ResponseEntity<ApiResponse> getDistricts(@RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.getDistricts(addressDto);
        return apiResponse.isSuccess()?ResponseEntity.status(HttpStatus.OK).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping(path = "/quarters")
    public ResponseEntity<ApiResponse> getQuarters(@RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.getQuarters(addressDto);
        return apiResponse.isSuccess()?ResponseEntity.status(HttpStatus.OK).body(apiResponse):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
