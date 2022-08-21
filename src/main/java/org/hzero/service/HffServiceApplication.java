package org.hzero.service;

import io.choerodon.resource.annoation.EnableChoerodonResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/20 14:59
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableChoerodonResourceServer
public class HffServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HffServiceApplication.class, args);
    }
}