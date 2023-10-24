package com.hong.service;

import com.hong.entity.sub.Sauce;
import com.hong.repository.sub.material.SauceRepo;
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
