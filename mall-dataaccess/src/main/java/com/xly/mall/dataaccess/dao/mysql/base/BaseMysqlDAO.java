package com.xly.mall.dataaccess.dao.mysql.base;

import com.xly.mall.common.domain.base.PageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMysqlDAO<T> {
    Integer save(T varl);

    int update(T var1);

    void delete(Integer var1);

    T findById(Integer var1);

    List<T> findAll(@Param("filterRules") List<FilterRule> var1);

    void deleteAll(@Param("filterRules") List<FilterRule> var1);

    List<T> findByPage(@Param("filterRules") List<FilterRule> var1, @Param("pageQuery") PageQuery var2);

    Integer getTotalCount(@Param("filterRules") List<FilterRule> var1);
}
