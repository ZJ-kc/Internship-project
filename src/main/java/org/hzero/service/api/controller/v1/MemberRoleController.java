package org.hzero.service.api.controller.v1;

import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.service.app.service.MemberRoleService;
import org.hzero.service.domain.entity.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.domain.Page;
import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.mybatis.pagehelper.annotation.SortDefault;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.choerodon.mybatis.pagehelper.domain.Sort;
import io.choerodon.swagger.annotation.Permission;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 *  管理 API
 *
 * @author yuji.sun@zknow.com 2022-08-25 16:06:28
 */
@RestController("memberRoleController.v1" )
@RequestMapping("/v1/{organizationId}/member-roles" )
public class MemberRoleController extends BaseController {

    private final MemberRoleService memberRoleService;
    @Autowired
    public MemberRoleController(MemberRoleService memberRoleService) {
        this.memberRoleService = memberRoleService;
    }



}
