package org.hzero.service.app.service;

import org.hzero.service.domain.entity.Card;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 应用服务
 *
 * @author jiao.zhao@zknown.com 2022-08-23 17:39:12
 */
public interface CardService {


    /**
     * 批量插入坐标信息
     * @param cards
     * @return
     */
    ResponseEntity<?> addBatchCard(List<Card> cards);
}
