package com.xly.mall.core.service.user.impl.support;

import com.xly.mall.common.base.ConverterUtil;
import com.xly.mall.common.base.user.MD5Util;
import com.xly.mall.common.constant.CommonConstant;
import com.xly.mall.common.domain.user.pojo.User;
import com.xly.mall.dataaccess.dao.mysql.user.UserMapper;
import com.xly.mall.dataaccess.dao.mysql.userLoginLog.UserLoginLogMapper;
import com.xly.mall.dataaccess.domain.user.UserDO;
import com.xly.mall.dataaccess.domain.userLoginLog.UserLoginLogDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Component
public class UserSupport {

    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    @Autowired(required = false)
    private HttpSession httpSession;

    @Autowired
    private UserMapper userMapper;

    public void addUserLoginLog(String userName, String loginIp, String loginMacAddress) {
        if (loginIp == null) {
            return;
        }
        UserLoginLogDO userLoginLogDO = new UserLoginLogDO();
        userLoginLogDO.setUserName(userName);
        userLoginLogDO.setLoginIp(loginIp);
        userLoginLogDO.setLoginMacAddress(loginMacAddress);
        userLoginLogDO.setCreateTime(new Date());
        userLoginLogMapper.save(userLoginLogDO);
    }

    public String generateMD5Password(String username, String password, String md5Key) {
        String value = MD5Util.encryptWithKey(username + password, md5Key);
        return value;
    }

    public User getCurrentUser() {
        return (User) httpSession.getAttribute(CommonConstant.ERP_USER_SESSION_KEY);
    }

    public Integer getCurrentUserId() {
        User user = (User) httpSession.getAttribute(CommonConstant.ERP_USER_SESSION_KEY);
        return user == null ? null : user.getUserId();
    }

    public Integer getCurrentUserIdDefaultSuper() {
        User loginUser = getCurrentUser();
        Integer loginUserId = loginUser == null ? CommonConstant.SUPER_USER_ID : loginUser.getUserId();
        return loginUserId;
    }

    public void setCurrentUser(Integer userId) {
        User user = getCurrentUser();
        if (user == null) {
            UserDO userDO = userMapper.findByUserId(userId);
            user = ConverterUtil.convert(userDO, User.class);
            httpSession.setAttribute(CommonConstant.ERP_USER_SESSION_KEY, user);
        }
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

}
