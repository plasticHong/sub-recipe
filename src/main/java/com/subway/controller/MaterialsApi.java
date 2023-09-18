package com.subway.controller;

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
    @RequestMapping(method = RequestMethod.GET, value = "/sandwich-base")
    public ResponseEntity<?> getSandwichBase() {

        SandwichBaseResponse allSandwichBase = materialFindService.getAllSandwichBase();

        return new ResponseEntity<>(allSandwichBase,HttpStatus.OK);
    }

}
