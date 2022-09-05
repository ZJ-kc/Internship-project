package org.hzero.service.interceptor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.hzero.service.annotation.OpenDataSourceAnnotation;
import org.hzero.service.infra.util.EnhanceSqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SunYuji
 * @create 2022/8/25 10:02
 */
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
public class MybatisInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(MybatisInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        //获取执行方法的位置
        String namespace = mappedStatement.getId();
        //获取mapper名称
        String className = namespace.substring(0,namespace.lastIndexOf("."));

        // 获取注解
        OpenDataSourceAnnotation annotation = Class.forName(className).getAnnotation(OpenDataSourceAnnotation.class);
        if(null == annotation) {
            return invocation.proceed();
        }
        String datasourceName = annotation.value();

        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");

        String sql = boundSql.getSql().replaceAll("\\s+", " ").toLowerCase();
        String enhanceSql = EnhanceSqlUtils.getEnhanceSql(sql, datasourceName);

        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, enhanceSql);

        // 将拦截到的sql语句插入日志表中
        logger.info("############{} 语句被增强为\n{} ##############",sql, enhanceSql);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if(target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
