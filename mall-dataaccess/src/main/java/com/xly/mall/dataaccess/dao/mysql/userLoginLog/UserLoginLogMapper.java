package com.xly.mall.dataaccess.dao.mysql.userLoginLog;

import com.xly.mall.dataaccess.dao.mysql.base.BaseMysqlDAO;
import com.xly.mall.dataaccess.domain.userLoginLog.UserLoginLogDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserLoginLogMapper extends BaseMysqlDAO<UserLoginLogDO> {

	List<UserLoginLogDO> listPage(@Param("maps") Map<String, Object> paramMap);

	Integer listCount(@Param("maps") Map<String, Object> paramMap);
}