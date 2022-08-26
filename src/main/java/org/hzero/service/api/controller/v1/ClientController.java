package org.hzero.service.api.controller.v1;

import java.util.List;

import io.swagger.annotations.Api;
import org.hzero.core.base.BaseController;
import org.hzero.service.app.service.ClientService;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.Client;

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
@Api(SwaggerApiConfig.CLIENT)
@RestController("clientController.v1" )
@RequestMapping("/v1/{organizationId}/client" )
public class ClientController extends BaseController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "查询所有客户")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/list")
    public ResponseEntity<List<Client>> list() {
        return clientService.list();
    }

}
