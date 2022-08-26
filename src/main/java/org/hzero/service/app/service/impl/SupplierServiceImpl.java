package org.hzero.service.app.service.impl;

import java.util.List;

import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.service.app.service.SupplierService;
import org.hzero.service.domain.entity.Supplier;
import org.hzero.service.domain.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-21 10:41:27
 */
@Service
public class SupplierServiceImpl extends BaseAppService implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public ResponseEntity<List<Supplier>> list() {
        return Results.success(supplierRepository.selectAll());
    }
}
