package com.xly.mall.core.service.user;

import com.xly.mall.common.base.result.ServiceResult;
import com.xly.mall.common.domain.user.pojo.User;

public interface UserService {
     /**
      * @method  用户登录
      * @description 描述一下方法的作用
      * @date: 2019/4/18/018 14:34
      * @author: xiaoluyu
      */
    ServiceResult<String,User> userLogin(User user,String ip);
     /**
      * @method  添加用户
      * @description 描述一下方法的作用
      * @date: 2019/4/20/020 17:30
      * @author: xiaoluyu
      */
    ServiceResult<String,Integer> addUser(User user);
}
