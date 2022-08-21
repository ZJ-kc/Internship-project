package org.hzero.service.controller;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/20 15:27
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Permission(permissionPublic = true, level = ResourceLevel.ORGANIZATION)
    @GetMapping("/hello")
    public String get() {
        return "hello";
    }

    @Permission(level = ResourceLevel.ORGANIZATION)
    @GetMapping("/get")
    public String get1() {
        return "get";
    }
}
