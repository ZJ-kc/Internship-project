package org.hzero.service.api.controller.v1;

import java.util.List;

import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.service.app.service.SupplierService;
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
 * @author yuji.sun@zknow.com 2022-08-21 10:41:27
 */
@RestController("supplierController.v1" )
@RequestMapping("/v1/{organizationId}/supplier" )
public class SupplierController extends BaseController {

    private final SupplierService supplierService;
    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @ApiOperation(value = "查询所有供应商")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/list")
    public ResponseEntity<List<Supplier>> list() {
        return supplierService.list();
    }
}
