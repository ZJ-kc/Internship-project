package org.hzero.service.infra.listener;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import org.hzero.excel.service.ExcelReadListener;
import org.hzero.service.domain.repository.PurchaseInfoRepository;
import org.hzero.service.domain.repository.PurchaseOrderRepository;
import org.hzero.service.infra.util.PurchaseInfoImportConstantUtils;
import org.hzero.service.infra.util.PurchaseOrderImportConstantUtils;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/22 7:36
 */
public class PurchaseOrderExcelListener implements ExcelReadListener {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderExcelListener(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public void invoke(JSONObject object) {
//        System.out.println(object);
        Set<Map.Entry<String, Object>> entries = object.entrySet();
        System.out.println(entries);
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        if(iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE.equals(key)) {

            }
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE.equals(key)) {

            }
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE.equals(key)) {

            }
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE.equals(key)) {

            }if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE.equals(key)) {

            }
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE.equals(key)) {

            }
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE.equals(key)) {

            }
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE.equals(key)) {

            }
            if(PurchaseOrderImportConstantUtils.FIELD_PURCHASE_ORDER_DATE.equals(key)) {

            }

        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }
}
