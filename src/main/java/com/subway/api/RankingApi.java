package com.subway.api;

import com.subway.dto.response.MemberRankingResponse;
import com.subway.sevice.MemberRankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranking")
@Tag(name = "ranking", description = "회원")
@RequiredArgsConstructor
public class RankingApi {

    private final MemberRankingService memberRankingService;

    @Operation(summary = "", description = "")
    @RequestMapping(method = RequestMethod.GET, value = "/member/best-worst")
    public ResponseEntity<?> getBestAndWorstMember() {

        MemberRankingResponse response = memberRankingService.getMemberRanking();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
