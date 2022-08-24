package org.hzero.service.app.service.impl;

import java.util.List;

import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.mybatis.helper.SecurityTokenHelper;
import org.hzero.service.app.service.SaleService;
import org.hzero.service.domain.entity.Sale;
import org.hzero.service.domain.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Service
public class SaleServiceImpl extends BaseAppService implements SaleService {

    private final SaleRepository saleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public ResponseEntity<List<Sale>> list() {
        return Results.success(saleRepository.selectAll());
    }
}
