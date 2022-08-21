package org.hzero.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * API TAG
 *
 * @author shuangfei.zhu@hand-china.com 2022-07-14 17:14:12
 */
@Configuration
public class SwaggerApiConfig {
    public static final String CLIENT = "Client";
    public static final String COMPANY = "Company";
    public static final String MATERIAL = "Material";
    public static final String PURCHASE = "Purchase";
    public static final String PURCHASE_INFO = "Purchase_info";
    public static final String PURCHASE_ORDER = "Purchase_order";
    public static final String REPERTORY = "Repertory";
    public static final String SALE = "Sale";
    public static final String SALE_INFO = "Sale_info";
    public static final String SALE_ORDER = "Sale_order";
    public static final String PURCHASE_ORDER_CHECK = "Purchase_order_check";

    @Autowired
    public SwaggerApiConfig(Docket docket) {
        docket.tags(
                new Tag(CLIENT, "CLIENT"),
                new Tag(COMPANY, "COMPANY"),
                new Tag(MATERIAL, "MATERIAL"),
                new Tag(PURCHASE, "PURCHASE"),
                new Tag(PURCHASE_INFO, "PURCHASE_INFO"),
                new Tag(PURCHASE_ORDER, "PURCHASE_ORDER"),
                new Tag(REPERTORY, "REPERTORY"),
                new Tag(SALE, "SALE"),
                new Tag(SALE_INFO, "SALE_INFO"),
                new Tag(SALE_ORDER, "SALE_ORDER"),
                new Tag(PURCHASE_ORDER_CHECK, "PURCHASE_ORDER_CHECK")
        );
    }
}
