package org.hzero.service.api.controller.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hzero.core.util.Results;
import org.hzero.service.config.SwaggerApiConfig;
import org.hzero.service.domain.entity.Store;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/23 13:28
 */
@Api(SwaggerApiConfig.COMPANY)
@RestController("CurrencyController.v1" )
@RequestMapping("/v1/{organizationId}/currency" )
public class CurrencyController {

    @ApiOperation(value = "查询所有币种")
    @Permission(level = ResourceLevel.ORGANIZATION, permissionLogin = true)
    @GetMapping("/list")
    public ResponseEntity<List<Map<String, String>>> list() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> currency1 = new HashMap<>();
        currency1.put("currencyCode","USD");
        currency1.put("currencyName","$");
        list.add(currency1);

        Map<String, String> currency2 = new HashMap<>();
        currency2.put("currencyCode","CNY");
        currency2.put("currencyName","￥");
        list.add(currency2);
        return Results.success(list);
    }
}
