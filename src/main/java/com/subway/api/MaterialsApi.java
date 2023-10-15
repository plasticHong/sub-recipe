package com.subway.api;

import com.subway.dto.response.*;
import com.subway.service.MaterialFindService;
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
    public ResponseEntity<?> getSandwichBase(@RequestParam(required = false) String sortOption,
                                             @RequestParam(required = false) String sortDirection) {

        SandwichBaseResponse allSandwichBase = materialFindService.getAllSandwichBases(sortOption,sortDirection);

        return new ResponseEntity<>(allSandwichBase,HttpStatus.OK);
    }

    @Operation(summary = "샌드위치 빵", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/breads")
    public ResponseEntity<?> getBreads(@RequestParam(required = false) String sortOption,
                                       @RequestParam(required = false) String sortDirection) {

        BreadResponse allBread = materialFindService.getAllBreads(sortOption,sortDirection);

        return new ResponseEntity<>(allBread,HttpStatus.OK);
    }

    @Operation(summary = "추가옵션", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/extra-options")
    public ResponseEntity<?> getExtraOptions(@RequestParam(required = false) String sortOption,
                                             @RequestParam(required = false) String sortDirection) {

        ExtraOptionResponse allExtraOptions = materialFindService.getAllExtraOptions(sortOption,sortDirection);

        return new ResponseEntity<>(allExtraOptions,HttpStatus.OK);
    }

    @Operation(summary = "치즈", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/cheeses")
    public ResponseEntity<?> getCheeses(@RequestParam(required = false) String sortOption,
                                        @RequestParam(required = false) String sortDirection) {

        CheeseResponse allCheeses = materialFindService.getAllCheeses(sortOption,sortDirection);

        return new ResponseEntity<>(allCheeses,HttpStatus.OK);
    }

    @Operation(summary = "더블업/미트 추가", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/individual-meats")
    public ResponseEntity<?> getIndividualMeats(@RequestParam(required = false) String sortOption,
                                                @RequestParam(required = false) String sortDirection) {

        IndividualMeatResponse allIndividualMeats = materialFindService.getAllIndividualMeats(sortOption, sortDirection);

        return new ResponseEntity<>(allIndividualMeats,HttpStatus.OK);
    }

    @Operation(summary = "야채", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/veggies")
    public ResponseEntity<?> getVeggies(@RequestParam(required = false) String sortOption,
                                        @RequestParam(required = false) String sortDirection) {

        VeggieResponse allVeggies = materialFindService.getAllVeggies(sortOption, sortDirection);

        return new ResponseEntity<>(allVeggies,HttpStatus.OK);
    }

    @Operation(summary = "소스", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/sauces")
    public ResponseEntity<?> getSauces(@RequestParam(required = false) String sortOption,
                                       @RequestParam(required = false) String sortDirection) {

        SauceResponse allSauces = materialFindService.getAllSauces(sortOption, sortDirection);

        return new ResponseEntity<>(allSauces,HttpStatus.OK);
    }

}
