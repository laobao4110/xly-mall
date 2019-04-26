package com.xly.mall.core.service.user.impl;

import com.xly.mall.common.base.ConverterUtil;
import com.xly.mall.common.base.db.dbInterface.DynamicDataSource;
import com.xly.mall.common.base.result.ServiceResult;
import com.xly.mall.common.base.user.SessionManagement;
import com.xly.mall.common.constant.CommonConstant;
import com.xly.mall.common.constant.ErrorCode;
import com.xly.mall.common.domain.ApplicationConfig;
import com.xly.mall.common.domain.user.pojo.User;
import com.xly.mall.common.util.UserUtil;
import com.xly.mall.core.service.user.UserService;
import com.xly.mall.core.service.user.impl.support.UserSupport;
import com.xly.mall.dataaccess.dao.mysql.user.UserMapper;
import com.xly.mall.dataaccess.dao.mysql.userLoginLog.UserLoginLogMapper;
import com.xly.mall.dataaccess.domain.user.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    @Autowired
    private UserSupport userSupport;

//    @Autowired(required=false)：表示忽略当前要注入的bean，如果有直接注入，没有跳过，不会报错
    @Autowired(required = false)
    private HttpSession session;

    @DynamicDataSource()
    @Override
    public ServiceResult<String, User> userLogin(User user,String ip) {
        ServiceResult<String, User> result = new ServiceResult<>();
        //保存登录信息（这里获取不到mac地址需要需要大家的优化）
        userSupport.addUserLoginLog(user.getUserName(),ip,"");
        UserDO userDO = userMapper.findByUserName(user.getUserName());
        if (userDO == null) {
            result.setErrorCode(ErrorCode.USER_NAME_NOT_FOUND);
        } else if (userDO.getIsDisabled().equals(CommonConstant.COMMON_CONSTANT_YES)) {
            result.setErrorCode(ErrorCode.USER_DISABLE);
        } else if (userDO.getIsActivated().equals(CommonConstant.COMMON_CONSTANT_NO)) {
            result.setErrorCode(ErrorCode.USER_NOT_ACTIVATED);
        } else if (!userDO.getPassword().equals(userSupport.generateMD5Password(userDO.getUserName(), user.getPassword(), ApplicationConfig.authKey))) {
            result.setErrorCode(ErrorCode.USER_PASSWORD_ERROR);
        } else if (!UserUtil.isNotSimple(user.getPassword())) {
            result.setErrorCode(ErrorCode.USER_PASSWORD_TOO_SIMPLE);
        } else {
            User saveDbUser = ConverterUtil.convert(userDO, User.class);
            userDO.setLastLoginIp(ip);
            userDO.setLastLoginTime(new Date());
            userMapper.update(userDO);
            session.setAttribute(CommonConstant.ERP_USER_SESSION_KEY, saveDbUser);
            SessionManagement.getInstance().removeSession(session.getId());
            SessionManagement.getInstance().addSessionId(saveDbUser.getUserId().toString());
            result.setErrorCode(ErrorCode.SUCCESS);
            result.setResult(saveDbUser);
        }
        return result;
    }

    @Override
    public ServiceResult<String, Integer> addUser(User user) {
        ServiceResult<String, Integer> result = new ServiceResult<>();
        Integer currentUserId = userSupport.getCurrentUserId();
        Date now = new Date();
        UserDO dbUserDO =  userMapper.findByUserName(user.getUserName());
        if(dbUserDO != null){
            result.setErrorCode(ErrorCode.USER_EXIST);
            return result;
        }
        UserDO userDO = ConverterUtil.convert(user, UserDO.class);
        userDO.setPassword(userSupport.generateMD5Password(userDO.getUserName(), user.getPassword(), ApplicationConfig.authKey));
        userDO.setCreateTime(now);
        userDO.setCreateUser(currentUserId == null ? "":currentUserId.toString());
        userDO.setIsActivated(CommonConstant.COMMON_CONSTANT_YES);
        userDO.setIsDisabled(CommonConstant.COMMON_CONSTANT_NO);
        userDO.setRegisterTime(now);

        userMapper.save(userDO);
        result.setErrorCode(ErrorCode.SUCCESS);
        return result;
    }
}
