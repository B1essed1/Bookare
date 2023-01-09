package com.example.bookare.services.ServicesImpl;

import com.example.bookare.models.AddressDto;
import com.example.bookare.models.result.ApiResponse;
import com.example.bookare.repositories.DistrictsRepository;
import com.example.bookare.repositories.QuartersRepository;
import com.example.bookare.repositories.RegionsRepository;
import com.example.bookare.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final RegionsRepository regionsRepository;

    private final DistrictsRepository districtsRepository;

    private final QuartersRepository quartersRepository;

    @Override
    public ApiResponse getRegions() {
        return new ApiResponse(null, regionsRepository.findAll(), true);
    }

    @Override
    public ApiResponse getDistricts(AddressDto addressDto) {
        return new ApiResponse(null, districtsRepository.findAllByRegionsId(addressDto.getId()), true);
    }

    @Override
    public ApiResponse getQuarters(AddressDto addressDto) {
        return new ApiResponse(null, quartersRepository.findAllByDistrictsId(addressDto.getId()), true);
    }
}
