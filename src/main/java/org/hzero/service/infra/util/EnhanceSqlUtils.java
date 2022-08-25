package org.hzero.service.infra.util;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.hzero.service.annotation.OpenDataSourceAnnotation;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/25 10:21
 */
public class EnhanceSqlUtils {
    public static String getEnhanceSql(String sql, String datasourceName) throws Throwable {
//        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
//        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
//        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
//
//        //获取执行方法的位置
//        String namespace = mappedStatement.getId();
//        //获取mapper名称
//        String className = namespace.substring(0,namespace.lastIndexOf("."));
//
//        // 获取注解
//        OpenDataSourceAnnotation annotation = Class.forName(className).getAnnotation(OpenDataSourceAnnotation.class);
//        String datasourceName = annotation.value();

//        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
//        String sql = boundSql.getSql().replaceAll("\\s+", " ").toLowerCase().trim();

        String enchanceSql = "";
        if(sql.toUpperCase().startsWith("INSERT")){
            enchanceSql = getInsertSql(datasourceName, sql);
        } else if(sql.toUpperCase().startsWith("UPDATE")) {
            enchanceSql = getUpdateSql(datasourceName, sql);
        } else if(sql.toUpperCase().startsWith("SELECT")) {
            enchanceSql = getSelectSql(datasourceName, sql);
        } else if(sql.toUpperCase().startsWith("DELETE")) {
            enchanceSql = getDeleteSql(datasourceName, sql);
        }

        return enchanceSql;
    }

    private static String getSelectSql(String datasourceName, String sql) {// select * from hff_sale,hff_sale_order where id = 1
        String newSql1 = sql.split("from")[0].trim();
        String newSql2 = sql.split("from")[1].trim();
        String newSql3 = "";
        if(newSql2.contains("where")){
            newSql3 = newSql2.split("where")[1].trim();
            newSql3 = " where " + newSql3;
            newSql2 = newSql2.split("where")[0].trim();
        }

        String newSql21 = "";

        String[] split = newSql2.split(",");
        for(String item: split) {
            if(item.equals(split[split.length-1])) {
                newSql21 = newSql21 + datasourceName + "." + item;
            } else {
                newSql21 = newSql21 + datasourceName + "." + item + ",";
            }
        }

        String newSQL = newSql1 + " from " + newSql21 + newSql3;

        return newSQL.trim();
    }

    private static String getUpdateSql(String datasourceName, String sql) {// update hff_sale set id = 1 where id = 2
        String newSql = sql.split("update ")[1].trim();

        newSql = "update " + datasourceName + "." + newSql;
        return newSql;
    }

    private static String getDeleteSql(String datasourceName, String sql) {//delete from hff_sale where id = 2
        String newSql = sql.split("from")[1].trim();
        newSql = "delete from " + datasourceName + "." + newSql;
        return newSql;
    }

    private static String getInsertSql(String datasourceName, String sql) {//insert into hff_sale(id, sale_name) values(1, 'suj')
        String newSql = sql.split("insert into")[1].trim();
        newSql = "insert into " + datasourceName + "." + newSql;
        return newSql;
    }
}
