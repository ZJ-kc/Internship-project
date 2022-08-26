package org.hzero.service.api.controller.v1;

import java.util.List;

import io.swagger.annotations.Api;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.service.app.service.CompanyService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.Company;
import org.hzero.service.domain.entity.Supplier;

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
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Api(SwaggerApiConfig.COMPANY)
@RestController("companyController.v1" )
@RequestMapping("/v1/{organizationId}/company" )
public class CompanyController extends BaseController {

    private final CompanyService companyService;
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @ApiOperation(value = "查询所有公司")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/list")
    public ResponseEntity<List<Company>> list() {
        return companyService.list();
    }


}
