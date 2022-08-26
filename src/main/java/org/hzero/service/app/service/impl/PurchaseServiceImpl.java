package org.hzero.service.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import io.choerodon.core.oauth.DetailsHelper;
import org.hzero.core.base.BaseAppService;

import org.hzero.core.util.Results;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.hzero.service.app.service.PurchaseService;
import org.hzero.service.domain.entity.Purchase;
import org.hzero.service.domain.repository.PurchaseRepository;
import org.hzero.service.domain.repository.RoleRepository;
import org.hzero.service.domain.repository.UserRepository;
import org.hzero.service.infra.constant.RoleCodeConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.hzero.service.domain.entity.Role;

/**
 * 应用服务默认实现
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Service
public class PurchaseServiceImpl extends BaseAppService implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
                               UserRepository userRepository,
                               RoleRepository roleRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<List<Purchase>> list(Long organizationId, Long roleId) {
        List<Purchase> purchases = new ArrayList<>();
        // 根据角色id查询对应的角色编码，如果是采购员角色，
        // 则只能查询自己这个采购员，如果是采购经理，则可以查询所有采购员
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
        if(RoleCodeConstant.ROLE_PURCHASE_CODE.equals(role.getCode())) {
            Long userId = DetailsHelper.getUserDetails().getUserId();
            purchases = purchaseRepository.select(Purchase.FIELD_USER_ID, userId);
        } else if(RoleCodeConstant.ROLE_PURCHASE_MANAGE_CODE.equals(role.getCode())) {
            purchases = purchaseRepository.selectAll();
        } else if(RoleCodeConstant.ROLE_ADMINISTRATOR_CODE.equals(role.getCode())) {
            purchases = purchaseRepository.selectAll();
        }
        return Results.success(purchases);
    }
}
