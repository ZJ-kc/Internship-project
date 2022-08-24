package org.hzero.service.infra.repository.impl;

import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.service.api.dto.CardDTO;
import org.hzero.service.domain.entity.Card;
import org.hzero.service.domain.repository.CardRepository;
import org.hzero.service.infra.mapper.CardMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *  资源库实现
 *
 * @author jiao.zhao@zknown.com 2022-08-23 17:39:12
 */
@Component
public class CardRepositoryImpl extends BaseRepositoryImpl<Card> implements CardRepository {

    @Override
    public List<CardDTO> listCards() {
        List<Card> cards = this.selectAll();
        List<CardDTO> cardList=new ArrayList<>();
        for(Card card:cards){
            CardDTO cardDTO=new CardDTO()
                    .setId(card.getId())
                    .setI(card.getI())
                    .setX(card.getX())
                    .setY(card.getY())
                    .setW(card.getW())
                    .setH(card.getH())
                    .setIsOn(card.getIsOn());
            cardList.add(cardDTO);
        }
        return cardList;
    }
}
