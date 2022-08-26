package org.hzero.service.app.service.impl;

import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.service.app.service.CardService;
import org.hzero.service.domain.entity.Card;
import org.hzero.service.domain.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 应用服务默认实现
 *
 * @author jiao.zhao@zknown.com 2022-08-23 17:39:12
 */
@Service
public class CardServiceImpl extends BaseAppService implements CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> addBatchCard(List<Card> cards) {
        if(cards.isEmpty()) {
            return Results.error("error");
        }
        cardRepository.batchDelete(cardRepository.selectAll());
        cardRepository.batchInsertSelective(cards);

        return Results.success("success");
    }
}
