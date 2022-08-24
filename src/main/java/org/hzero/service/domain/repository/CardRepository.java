package org.hzero.service.domain.repository;

import org.hzero.mybatis.base.BaseRepository;
import org.hzero.service.api.dto.CardDTO;
import org.hzero.service.domain.entity.Card;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 资源库
 *
 * @author jiao.zhao@zknown.com 2022-08-23 17:39:12
 */
public interface CardRepository extends BaseRepository<Card> {

    List<CardDTO> listCards();
}
