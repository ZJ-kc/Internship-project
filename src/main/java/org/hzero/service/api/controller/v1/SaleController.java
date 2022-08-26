package org.hzero.service.api.controller.v1;

import java.util.List;

import io.choerodon.core.oauth.DetailsHelper;
import io.swagger.annotations.Api;
import org.hzero.core.base.BaseController;
import org.hzero.service.app.service.SaleService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;

import io.swagger.annotations.ApiOperation;

/**
 *  管理 API
 *
 * @author yuji.sun@zknow.com 2022-08-20 14:22:57
 */
@Api(SwaggerApiConfig.SALE)
@RestController("saleController.v1" )
@RequestMapping("/v1/{organizationId}/sale" )
public class SaleController extends BaseController {

    private final SaleService saleService;
    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @ApiOperation(value = "查询所有销售员")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/list")
    public ResponseEntity<List<Sale>> list(@PathVariable Long organizationId) {
        Long roleId = DetailsHelper.getUserDetails().getRoleId();
        return saleService.list(organizationId, roleId);
    }


}
