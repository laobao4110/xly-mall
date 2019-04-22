package com.xly.mall.common.constant;

import java.math.BigDecimal;

 /**
  * @method
  * @description 描述一下方法的作用
  * @date: 2019/4/19/019 11:15
  * @author: xiaoluyu
  */
public class CommonConstant {
    public static final String ERP_USER_SESSION_KEY = "user";
    public static final String ERP_CAPTCHA_IMAGE_SESSION_KEY = "captchaImage";
    public static final Double PROPORTION_MAX = 100.0;
    public static final String ERP_PHONE_LOGIN_SESSION_KEY = "phoneLoginUser";

    public static final Integer COMMON_CONSTANT_YES = 1;
    public static final Integer COMMON_CONSTANT_NO = 0;

    public static final int YES = 1;
    public static final int NO  = 0;

    public static final String UPLOAD_USER = "admin";
    public static final String ADMIN_REAL_NAME = "管理员";
    public static final Integer ADMIN_ID = 600007;

    public static final Integer SUPER_MENU_ID = 200000;
    public static final Integer SUPER_DATA_DICTIONARY_ID = 300000;
    public static final Integer SUPER_DEPARTMENT_ID = 400000;
    public static final Integer SUPER_USER_ID = 500001;
    public static final Integer ADMIN_USER_ID = 500001;
    public static final Integer SUPER_ROLE_ID = 600000;
    public static final Integer SUPER_CUSTOMER_ID = 700000;
    public static final Integer SUPER_PRODUCT_CATEGORY_ID = 800000;
    public static final Integer HEAD_COMPANY_ID = 1;
    public static final Integer ELECTRIC_SALE_COMPANY_ID = 10;
    public static final Integer CHANNEL_CUSTOMER_COMPANY_ID = 11;
    public static final Integer HEADER_COMPANY_ID = 1;
    public static final Integer MALL_USER_ID = 500717;

    public static final String COMMUNITY_SESSION_KEY = "community_id";
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String MD5_KEY = "kjhku8gsasiow34";

    // 数据状态
    public static final Integer DATA_STATUS_DISABLE = 0;//标记禁用
    public static final Integer DATA_STATUS_ENABLE = 1;//标记可用
    public static final Integer DATA_STATUS_DELETE = 2;//标记删除
    // 操作类型
    public static final Integer COMMON_DATA_OPERATION_TYPE_ADD = 1;      // 增加
    public static final Integer COMMON_DATA_OPERATION_TYPE_UPDATE = 2;   // 修改
    public static final Integer COMMON_DATA_OPERATION_TYPE_DELETE = 3;   // 删除

    public static final String COMMON_CONSTANT_SEPARATOR = ",";

    public static final String NORMAL_STRING = "正常";
    public static final Integer ORDER_RENT_TYPE_LONG_MIN = 6;

    public static final Integer ORDER_NEED_VERIFY_DAYS = 90;
    public static final Integer ORDER_NEED_VERIFY_MONTHS = 3;
    public static final Integer ORDER_NEED_VERIFY_PRODUCT_COUNT = 100;
    public static final BigDecimal ORDER_NEED_VERIFY_PRODUCT_AMOUNT = new BigDecimal(200000);
    public static final Integer ORDER_TEST_MACHINE_RENT_TIME = 30;

    public static final Integer COMMON_MINUS_ONE = -1;
    public static final Integer COMMON_ZERO = 0;
    public static final Integer COMMON_ONE = 1;
    public static final Integer COMMON_TWO = 2;
    public static final Integer COMMON_THREE = 3;
    public static final Integer COMMON_FOUR = 4;
    public static final Integer COMMON_TEN = 10;
    public static final Integer COMMON_THIRTY_SIX = 36;
    public static final Integer COMMON_THIRTY_ONE = 31;
    public static final Integer COMMON_NINETY = 90;

    public static final Integer COMMON_SIX = 6;
    public static final Integer COMMON_FIVE = 5;
    public static final Integer COMMON_SEVEN = 7;
    public static final Integer COMMON_EIGHT = 8;

    public static final Integer COMMON_TWO_HUNDRED = 200;
    public static final Integer COMMON_FIVE_HUNDRED = 500;
    public static final Integer COMMON_TWO_THOUSAND = 2000;

    public static final String MONTH_DAY_TWENTY = "21";
    public static final String MONTH_DAY_LAST = "01";


    public static final Integer COMMON_SALES_WORKBENCH = 0;//工作台类型，0-业务工作台
    public static final Integer COMMON_BUSINESS_AFFAIRS_WORKBENCH = 1;//工作台类型，1-商务工作台
    public static final Integer COMMON_SALES_AND_BUSINESS_AFFAIRS_WORKBENCH = 2;//工作台类型，2-业务和商务工作台
    public static final Integer COMMON_WORKHOUSE_WORKBENCH = 3;//工作台类型，3-仓库工作台
    public static final Integer COMMON_WORKHOUSE_RISK = 4;//工作台类型，4-风控工作台
    public static final Integer COMMON_WORKHOUSE_FINANCE = 5;//工作台类型，5-财务工作台

    public static final Integer WORKFLOW_STEP_TWO = 2;
    public static final Integer WORKFLOW_STEP_THREE = 3;

    public static final Integer RELET_TIME_OF_RENT_TYPE_MONTH = 30;  //按月租提前30天
    public static final Integer RELET_TIME_OF_RENT_TYPE_DAY = 15;   //按天租提前15天

    public static final Integer STATEMENT_ADVANCE_EXPECT_PAY_END_TIME = 7;//工作台结算单预计支付时间提前七天查询

    public static final Integer ORDER_TYPE_RELET = 6;//续租状态，对账单导出专用
    public static final Integer ORDER_TYPE_RELET_RETURN = 7;//续租退货状态，对账单导出专用

    public static final long WORKBENCH_REDIS_SAVE_TIME = 180L; //工作台redis缓存时间，三分钟
    public static final long MOVE_IMG_REDIS_SAVE_TIME = 300L; //终止图片迁移redis缓存时间，5分钟

    public static final long PHONE_LOGIN_REDIS_SAVE_TIME = 180L; //工作台redis缓存时间，三分钟

    public static final Integer K3_SEL_STOCK_WARE_TYPE_ONE = 1; //k3库存查询库位类型，1-分公司仓
    public static final Integer K3_SEL_STOCK_WARE_TYPE_TWO = 2; //k3库存查询库位类型，2-借出仓
    public static final Integer K3_SEL_STOCK_WARE_TYPE_THREE = 3; //k3库存查询库位类型，3-全部

    public static final String STR_REGEX="\\";//转义字符


    public static final Integer[] SUCCESSSTATUS = {1, 2}; //退货申请单状态，1：待分配，2,：处理中

    public static final Integer RETURN_THE_WAY = 1;  //退还方式，1-上门取件，2邮寄
    public static final Integer RETURNREASONTYPE = 11;  //退还原因类型：1-客户方设备不愿或无法退还，2-期满正常收回，3-提前退租，4-未按时付款或风险等原因上门收回，5-设备故障等我方原因导致退货，6-主观因素等客户方原因导致退货，7-更换设备，8-公司倒闭，9-设备闲置，10-满三个月或六个月随租随还，11-其它
    public static final Integer DELIVERYSUBCOMPANY = 1;//发货公司
    public static final BigDecimal BIG_DECIMAL_ZERO = BigDecimal.valueOf(100.00); //提现起步金额

    public static final Integer PROTO_NUMBER =1; //样机
    public static final Integer SHORT_LONG_NUMBER =2; //短租/长租

    public static final String BIG_IMG_START = "Begin"; //开始图片迁移
    public static final String BIG_IMG_STOP = "Stop"; //图片迁移停止
    public static final int MINUTE = 2880; //分钟

    public static final int RETURN_ORDER_CREATE_STATEMENT_ORDER_SEND_START_MESSAGE_SLEEP_TIME = 1000; //退货单生成结算单发送第一条钉钉消息延迟时间(毫秒)
    public static final int RETURN_ORDER_CREATE_STATEMENT_ORDER_SEND_LAST_MESSAGE_SLEEP_TIME = 650000; //退货单生成结算单发送最后一条钉钉消息延迟时间(毫秒)
    public static final int RETURN_ORDER_CREATE_STATEMENT_ORDER_SEND_MESSAGE_SLEEP_TIME = 200; //退货单生成结算单发送钉钉消息延迟时间(毫秒)

    public static final Integer SUB_COMPANY_ID_JD = 12; //京东项目部

    public static final Integer MAX_RENT_START_TIME_DELAY_DAYS = 15; // 起租日期最大延迟天数
}
