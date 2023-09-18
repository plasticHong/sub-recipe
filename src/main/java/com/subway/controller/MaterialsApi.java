package com.subway.controller;

import com.subway.dto.response.BreadResponse;
import com.subway.dto.response.CheeseResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materials")
@Tag(name = "materials", description = "레시피 구성품")
@RequiredArgsConstructor
public class MaterialsApi {

    private final MaterialFindService materialFindService;

    @Operation(summary = "샌드위치 메뉴", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/sandwich-bases")
    public ResponseEntity<?> getSandwichBase(@RequestParam String sortOption,
                                             @RequestParam String sortDirection) {

        SandwichBaseResponse allSandwichBase = materialFindService.getAllSandwichBases(sortOption,sortDirection);

        return new ResponseEntity<>(allSandwichBase,HttpStatus.OK);
    }

    @Operation(summary = "샌드위치 빵", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/breads")
    public ResponseEntity<?> getBreads(@RequestParam String sortOption,
                                       @RequestParam String sortDirection) {

        BreadResponse allBread = materialFindService.getAllBreads(sortOption,sortDirection);

        return new ResponseEntity<>(allBread,HttpStatus.OK);
    }

    @Operation(summary = "추가옵션", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/extra-options")
    public ResponseEntity<?> getExtraOptions(@RequestParam String sortOption,
                                             @RequestParam String sortDirection) {

        ExtraOptionResponse allExtraOptions = materialFindService.getAllExtraOptions(sortOption,sortDirection);

        return new ResponseEntity<>(allExtraOptions,HttpStatus.OK);
    }

    @Operation(summary = "치즈", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/cheeses")
    public ResponseEntity<?> getCheeses(@RequestParam String sortOption,
                                        @RequestParam String sortDirection) {

        CheeseResponse allCheeses = materialFindService.getAllCheeses(sortOption,sortDirection);

        return new ResponseEntity<>(allCheeses,HttpStatus.OK);
    }
}
