package com.subway.service;

import com.subway.entity.Sauce;
import com.subway.repository.material.SauceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SauceService {

    private final SauceRepo sauceRepo;


    public List<Sauce> getAllSauceInfo() {

        return sauceRepo.findAll();
    }

}
