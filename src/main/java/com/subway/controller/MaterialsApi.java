package com.subway.controller;

import com.subway.dto.response.BreadResponse;
import com.subway.dto.response.ExtraOptionResponse;
import com.subway.dto.response.SandwichBaseResponse;
import com.subway.sevice.MaterialFindService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materials")
@Tag(name = "materials", description = "레시피 구성품")
@RequiredArgsConstructor
public class MaterialsApi {

    private final MaterialFindService materialFindService;

    @Operation(summary = "샌드위치 메뉴", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/sandwich-bases")
    public ResponseEntity<?> getSandwichBase() {

        SandwichBaseResponse allSandwichBase = materialFindService.getAllSandwichBases();

        return new ResponseEntity<>(allSandwichBase,HttpStatus.OK);
    }

    @Operation(summary = "샌드위치 빵", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/breads")
    public ResponseEntity<?> getBreads() {

        BreadResponse allBread = materialFindService.getAllBreads();

        return new ResponseEntity<>(allBread,HttpStatus.OK);
    }

    @Operation(summary = "추가옵션", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/extra-options")
    public ResponseEntity<?> getExtraOptions() {

        ExtraOptionResponse allExtraOptions = materialFindService.getAllExtraOptions();

        return new ResponseEntity<>(allExtraOptions,HttpStatus.OK);
    }

}
