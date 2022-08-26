package org.hzero.service.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import io.choerodon.core.oauth.DetailsHelper;
import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.app.service.SaleService;
import org.hzero.service.domain.entity.Purchase;
import org.hzero.service.domain.entity.Role;
import org.hzero.service.domain.entity.Sale;
import org.hzero.service.domain.repository.RoleRepository;
import org.hzero.service.domain.repository.SaleRepository;
import org.hzero.service.infra.constant.RoleCodeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Service
public class SaleServiceImpl extends BaseAppService implements SaleService {

    private final SaleRepository saleRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository,
                           RoleRepository roleRepository) {
        this.saleRepository = saleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<List<Sale>> list(Long organizationId, Long roleId) {
        List<Sale> sales = new ArrayList<>();
        // 根据角色id查询对应的角色编码，如果是销售员角色，
        // 则只能查询自己这个采购员，如果是销售经理或超级管理员，则可以查询所有销售员
        List<Role> roles = roleRepository.selectByCondition(
                Condition.builder(Role.class)
                        .andWhere(
                                Sqls.custom()
                                        .andEqualTo(Role.FIELD_H_TENANT_ID, organizationId)
                                        .andEqualTo(Role.FIELD_ID, roleId)
                        )
                        .build()
        );
        Role role = null;
        if(!roles.isEmpty()) {
            role = roles.get(0);
        }
        if(RoleCodeConstant.ROLE_SALE_CODE.equals(role.getCode())) {
            Long userId = DetailsHelper.getUserDetails().getUserId();
            sales = saleRepository.select(Sale.FIELD_USER_ID, userId);
        } else if(RoleCodeConstant.ROLE_SALE_MANAGE_CODE.equals(role.getCode())) {
            sales = saleRepository.selectAll();
        } else if(RoleCodeConstant.ROLE_ADMINISTRATOR_CODE.equals(role.getCode())) {
            sales = saleRepository.selectAll();
        }
        return Results.success(sales);
    }
}
