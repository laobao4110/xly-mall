package com.xly.mall.dataaccess.dao.mysql.user;

import com.xly.mall.dataaccess.dao.mysql.base.BaseMysqlDAO;
import com.xly.mall.dataaccess.domain.user.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper extends BaseMysqlDAO<UserDO> {

    List<UserDO> listPage(@Param("maps") Map<String, Object> paramMap);

    Integer listCount(@Param("maps") Map<String, Object> paramMap);

    UserDO findByUserName(@Param("userName") String userName);

    UserDO findByUserId(@Param("userId") Integer userId);
}