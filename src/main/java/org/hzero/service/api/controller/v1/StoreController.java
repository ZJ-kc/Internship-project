package org.hzero.service.api.controller.v1;

import java.time.LocalDate;

import org.hzero.core.util.Results;
import org.hzero.core.base.BaseController;
import org.hzero.service.app.service.StoreService;
import org.hzero.service.domain.entity.PurchaseOrder;
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
 * @author yuji.sun@zknow.com 2022-08-21 10:41:27
 */
@RestController("storeController.v1" )
@RequestMapping("/v1/{organizationId}/store" )
public class StoreController extends BaseController {

    private final StoreService storeService;
    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }



}
