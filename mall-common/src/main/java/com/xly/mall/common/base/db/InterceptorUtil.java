package com.xly.mall.common.base.db;

import com.xly.mall.common.base.SystemException;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;

public class InterceptorUtil {
    private static SqlSessionFactoryBean sqlSessionFactoryBean;

    public InterceptorUtil() {
    }

    public static void setSqlSessionFactoryBean(SqlSessionFactoryBean sqlSessionFactoryBean) {
        if (sqlSessionFactoryBean == null) {
            sqlSessionFactoryBean = sqlSessionFactoryBean;
        }

    }

    public static void dynamicAddInterceptor(String typeClass) {
        try {
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
            Configuration configuration = sqlSessionFactory.getConfiguration();
            TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
            Interceptor interceptor = (Interceptor)resolveClass(typeClass, typeAliasRegistry).newInstance();
            configuration.addInterceptor(interceptor);
        } catch (Exception var5) {
            throw new SystemException("DynamicAddInterceptor error", var5);
        }
    }

    private static Class<?> resolveClass(String alias, TypeAliasRegistry typeAliasRegistry) {
        if (alias == null) {
            return null;
        } else {
            try {
                return resolveAlias(alias, typeAliasRegistry);
            } catch (Exception var3) {
                throw new BuilderException("Error resolving class . Cause: " + var3, var3);
            }
        }
    }

    private static Class<?> resolveAlias(String alias, TypeAliasRegistry typeAliasRegistry) {
        return typeAliasRegistry.resolveAlias(alias);
    }
}

