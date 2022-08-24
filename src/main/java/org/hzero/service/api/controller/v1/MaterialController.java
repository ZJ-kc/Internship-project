package org.hzero.service.api.controller.v1;

import java.util.List;

import io.swagger.annotations.Api;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.service.app.service.MaterialService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.Material;
import org.hzero.service.domain.entity.Purchase;
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
@Api(SwaggerApiConfig.MATERIAL)
@RestController("materialController.v1" )
@RequestMapping("/v1/{organizationId}/material" )
public class MaterialController extends BaseController {

    private final MaterialService materialService;
    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @ApiOperation(value = "查询所有物料信息")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionPublic = true)
    @GetMapping("/list")
    public ResponseEntity<List<Material>> list() {
        return materialService.list();
    }
}
