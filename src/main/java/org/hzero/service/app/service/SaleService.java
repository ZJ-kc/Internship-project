package org.hzero.service.app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import org.hzero.service.domain.entity.Purchase;
import org.hzero.service.domain.entity.Sale;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
/**
 * 应用服务
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
public interface SaleService {

    /**
     * 查询所有销售员
     * @return
     */
    ResponseEntity<List<Sale>> list();
}
