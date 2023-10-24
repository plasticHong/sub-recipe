package com.hong.api.sub;

import com.hong.entity.sub.Sauce;
import com.hong.service.ImageStoreService;
import com.hong.service.MemberInfoService;
import com.hong.eum.S3Location;
import com.hong.service.SauceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "api", description = "관리자/이벤트 관리")
@RequiredArgsConstructor
public class MyController {

    private final SauceService sauceService;
    private final MemberInfoService memberInfoService;
    private final ImageStoreService imageStoreService;

    @Operation(summary = "이름", description = "")
    @RequestMapping(method = RequestMethod.POST, value = "/test")
    public ResponseEntity<?> test(@RequestPart MultipartFile file) {

        String path = imageStoreService.saveImgToS3(file, S3Location.PROFILE);
        System.out.println("path = " + path);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "이름", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/name")
    public ResponseEntity<?> getName() {
        Map<Object, Object> res = new HashMap<>();

        res.put("name", "son hong hun");
        System.out.println("get-name!");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "이름", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/nick")
    public ResponseEntity<?> getNick() {

        String nickName = memberInfoService.getUserNickName("hong");
        System.out.println("nickName = " + nickName);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Operation(summary = "소스", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/sauce")
    public ResponseEntity<?> getSauces() {
        Map<Object, Object> res = new HashMap<>();

        List<Sauce> allSauceInfo = sauceService.getAllSauceInfo();
        res.put("allSauceInfo", allSauceInfo);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
