package com.subway.sevice;

import com.subway.entity.JmtPointRecord;
import com.subway.entity.Recipe;
import com.subway.entity.RespectPointRecord;
import com.subway.entity.member.Member;
import com.subway.repository.JmtPointRecordRepo;
import com.subway.repository.MemberRepo;
import com.subway.repository.RecipeRepo;
import com.subway.repository.RespectPointRecordRepo;
import com.subway.utils.AuthenticationUtils;
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
        return evaluatePoint(recipeId, RecordType.JMT);
    }

    @Transactional
    public Long respect(Long recipeId) {
        return evaluatePoint(recipeId, RecordType.RESPECT);
    }

    private Long evaluatePoint(Long recipeId, RecordType type) {
        Long memberId = AuthenticationUtils.getCurrentMemberId();

        boolean isAlreadyEvaluate = isAlreadyEvaluate(recipeId, memberId, type);

        if (isAlreadyEvaluate) {
            System.out.println("can not undo!");
            return null;
        }

        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new NoSuchElementException("recipeId: " + recipeId));

        switch (type){
            case JMT -> {
                recipe.gotJmt();
                pointIncreaseByRecordType(memberId,type);

                JmtPointRecord jmtPointRecord = new JmtPointRecord(memberId, recipeId);
                return jmtRecordRepo.save(jmtPointRecord).getId();
            }
            case RESPECT -> {
                recipe.gotRespect();
                pointIncreaseByRecordType(memberId,type);

                RespectPointRecord respectPointRecord = new RespectPointRecord(memberId, recipeId);
                return respectRecordRepo.save(respectPointRecord).getId();
            }
        }

        return null;
    }

    private void pointIncreaseByRecordType(Long memberId, RecordType type) {
        Member member = memberRepo.findById(memberId).orElseThrow(() -> new NoSuchElementException("check memberId"));
        if (type.equals(RecordType.RESPECT)) {
            member.respectPointIncrease();
        }
        if (type.equals(RecordType.JMT)) {
            member.jmtPointIncrease();
        }
    }

    private boolean isAlreadyEvaluate(Long recipeId, Long memberId, RecordType type) {
        if (type.equals(RecordType.RESPECT)) {
            return respectRecordRepo.findByMemberIdAndRecipeId(memberId, recipeId).isPresent();
        }
        if (type.equals(RecordType.JMT)) {
            return jmtRecordRepo.findByMemberIdAndRecipeId(memberId, recipeId).isPresent();
        }
        return false;
    }

    private enum RecordType {
        JMT, RESPECT
    }

}
