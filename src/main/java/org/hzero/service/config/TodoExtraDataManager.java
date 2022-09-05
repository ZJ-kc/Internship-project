package org.hzero.service.config;

import io.choerodon.core.swagger.ChoerodonRouteData;
import io.choerodon.swagger.annotation.ChoerodonExtraData;
import io.choerodon.swagger.swagger.extra.ExtraData;
import io.choerodon.swagger.swagger.extra.ExtraDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * 路由配置
 *
 * @author shuangfei.zhu@hand-china.com 2022-07-14 17:14:12
 */
@ChoerodonExtraData
public class TodoExtraDataManager implements ExtraDataManager {

    @Autowired
    private Environment environment;

    @Override
    public ExtraData getData() {
        ChoerodonRouteData choerodonRouteData = new ChoerodonRouteData();
        choerodonRouteData.setName(environment.getProperty("hzero.service.current.name", "h5f"));
        choerodonRouteData.setPath(environment.getProperty("hzero.service.current.path", "/h5f/**"));
        choerodonRouteData.setServiceId(environment.getProperty("hzero.service.current.service-name", "hff-service-zj"));
        extraData.put(ExtraData.ZUUL_ROUTE_DATA, choerodonRouteData);
        return extraData;
    }
}
