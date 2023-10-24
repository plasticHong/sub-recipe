package com.hong.service;

import com.hong.entity.sub.JmtPointRecord;
import com.hong.entity.sub.Recipe;
import com.hong.entity.sub.RespectPointRecord;
import com.hong.entity.sub.member.Member;
import com.hong.repository.sub.JmtPointRecordRepo;
import com.hong.repository.sub.MemberRepo;
import com.hong.repository.sub.RecipeRepo;
import com.hong.repository.sub.RespectPointRecordRepo;
import com.hong.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RecipeEvaluateService {

    private final JmtPointRecordRepo jmtRecordRepo;
    private final RespectPointRecordRepo respectRecordRepo;
    private final RecipeRepo recipeRepo;
    private final MemberRepo memberRepo;

    @Transactional
    public Long jmt(Long recipeId) {
        return recipeEvaluation(recipeId, EvaluationType.JMT);
    }

    @Transactional
    public Long respect(Long recipeId) {
        return recipeEvaluation(recipeId, EvaluationType.RESPECT);
    }

    private Long recipeEvaluation(Long recipeId, EvaluationType type) {

        String ipAddr = AuthenticationUtils.getUserIp();
        Long memberId = AuthenticationUtils.getCurrentMemberId();

        boolean isAlreadyEvaluate;

        if (memberId == null) {
            isAlreadyEvaluate = isAlreadyEvaluate(recipeId, ipAddr, type);
        } else {
            isAlreadyEvaluate = isAlreadyEvaluate(memberId, recipeId, type);
        }

        if (isAlreadyEvaluate) {
            System.out.println("can not undo!");
            return null;
        }

        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("recipeId {" + recipeId + "}"));

        Long recipeOwnerId = recipe.getMemberId();

        switch (type) {
            case JMT -> {

                recipe.gotJmt();
                pointIncreaseByRecordType(recipeOwnerId, type);

                JmtPointRecord jmtPointRecord = new JmtPointRecord(memberId, recipeId, ipAddr);
                return jmtRecordRepo.save(jmtPointRecord).getId();
            }
            case RESPECT -> {

                recipe.gotRespect();
                pointIncreaseByRecordType(recipeOwnerId, type);

                RespectPointRecord respectPointRecord = new RespectPointRecord(memberId, recipeId, ipAddr);
                return respectRecordRepo.save(respectPointRecord).getId();
            }
        }

        return null;
    }

    private void pointIncreaseByRecordType(Long recipeOwnerId, EvaluationType type) {

        Member recipeOwner = memberRepo.findById(recipeOwnerId).orElseThrow(() -> new NoSuchElementException("memberId {" + recipeOwnerId + "}"));

        switch (type){
            case JMT -> recipeOwner.jmtPointIncrease();
            case RESPECT -> recipeOwner.respectPointIncrease();
        }

    }

    private boolean isAlreadyEvaluate(Long memberId, Long recipeId, EvaluationType type) {

        switch (type){
            case JMT -> {
                return jmtRecordRepo.findByMemberIdAndRecipeId(memberId, recipeId).isPresent();
            }
            case RESPECT -> {
                return respectRecordRepo.findByMemberIdAndRecipeId(memberId, recipeId).isPresent();
            }
        }

        return false;
    }

    private boolean isAlreadyEvaluate(Long recipeId, String ipAddr, EvaluationType type) {

        switch (type){
            case JMT -> {
                return jmtRecordRepo.findByRecipeIdAndIpAddr(recipeId, ipAddr).isPresent();
            }
            case RESPECT -> {
                return respectRecordRepo.findByRecipeIdAndIpAddr(recipeId, ipAddr).isPresent();
            }
        }

        return false;
    }

    private enum EvaluationType {
        JMT, RESPECT
    }

}
