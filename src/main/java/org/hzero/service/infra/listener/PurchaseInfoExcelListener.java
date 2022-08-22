package org.hzero.service.infra.listener;

import com.alibaba.fastjson.JSONObject;
import org.hzero.excel.service.ExcelReadListener;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/22 8:14
 */
public class PurchaseInfoExcelListener implements ExcelReadListener {
    @Override
    public void invoke(JSONObject object) {
        System.out.println(object);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }
}
