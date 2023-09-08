package com.example.contoller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "api", description = "관리자/이벤트 관리")

public class MyController {

    @Operation(summary = "이름", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/name")
    public ResponseEntity<?> getName() {
        Map<Object, Object> res = new HashMap<>();

        res.put("name", "son hong hun");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
