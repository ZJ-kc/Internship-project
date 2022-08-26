package org.hzero.service.api.controller.v1;

import io.choerodon.core.oauth.DetailsHelper;
import io.swagger.annotations.Api;
import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.service.app.service.RepertoryService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.PurchaseOrder;
import org.hzero.service.domain.entity.Repertory;
import org.hzero.service.domain.entity.Store;
import org.hzero.service.domain.vo.RepertoryParam;
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
@Api(SwaggerApiConfig.REPERTORY)
@RestController("repertoryController.v1" )
@RequestMapping("/v1/{organizationId}/repertory" )
public class RepertoryController extends BaseController {

    private final RepertoryService repertoryService;
    @Autowired
    public RepertoryController(RepertoryService repertoryService) {
        this.repertoryService = repertoryService;
    }

    @ApiOperation(value = "分页查询库存信息")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/paging")
    public ResponseEntity<Page<Repertory>> list(@PathVariable("organizationId") Long organizationId,
                                                    RepertoryParam repertoryParam,
                                                    @ApiIgnore @SortDefault(value = Store.FIELD_LAST_UPDATE_DATE,
                                                            direction = Sort.Direction.DESC) PageRequest pageRequest) {
        Page<Repertory> list = repertoryService.list(organizationId, repertoryParam, pageRequest);
        return Results.success(list);
    }
}
