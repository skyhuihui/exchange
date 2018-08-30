package com.zag.exception;

import com.zag.core.exception.ExceptionType;
import com.zag.core.exception.FunctionExceptions;

import static com.zag.exception.Exceptions.Constant.GLOBAL;
import static com.zag.exception.Exceptions.Constant.USER;

/**
 * 枚举异常常量
 * 
 * @author stone
 * @date 2017年8月30日
 * @reviewer
 */
public interface Exceptions extends FunctionExceptions {
	/**
	 * 异常模块code area constant
	 * 
	 * @author stone
	 * @since 2017年9月1日
	 * @usage
	 * @reviewer
	 */
	interface Constant {
		int GLOBAL = 900;
		int USER = 1000;
		int ORDER = 2000;
		int PRODUCT = 3000;
	}

    enum Global implements ExceptionType {
        SERVER_EXCEPTION(GLOBAL + 1, "Sorry,服务器开小差了！"),
        ID_NOT_EXIST(GLOBAL + 1, "id不存在"),
		ILLEGAL_PARAMETER_ERROR(GLOBAL + 4, "参数不合法"),
        PARAMETER_ERROR(GLOBAL + 4, "参数有误，请稍后重试！"),
        MISSING_REQUIRED_PARAMS(GLOBAL + 4, "参数有误，请稍后重试！"),
        SMS_SEND_TIME_INVALID(GLOBAL + 4, "验证码发送失败"),
        ILLEGAL_SMS_CODE(GLOBAL + 4, "短信验证码错误"),
        SERVER_TIPS(GLOBAL + 4, ""),//服务器给手机端的提示信息
        SIGN_ERROR(GLOBAL + 1, "签名错误"),
        ILLEGAL_OPERATION(GLOBAL + 1, "非法操作"),
    	NULL_OBJECT(GLOBAL+1, "对象不能为NULL"),
    	LOCATION_ERROR(GLOBAL + 4, "获取定位城市失败"),
		ACCESS_EXPIRED(GLOBAL+1,"请求过期");
		private int code;
		private String description;

		Global(int code, String description) {
			this.code = code;
			this.description = description;
		}

		@Override
		public int getCode() {
			return code;
		}

		@Override
		public String getDescription() {
			return description;
		}

	}

	enum User implements ExceptionType {
		USER_NOT_EXIST(USER + 1, "用户不存在"), 
		MOBILE_ERROR(USER + 2, "手机号格式错误"), 
		USER_ALREADY_EXIST(USER + 3, "用户已存在"),
		USERNAME_OR_PASSWORD_ERROR(USER + 4, "用户名或密码错误"), 
		ILLEGAL_USER_LEVEL(USER + 5, "用户等级有误，请咨询客服！"), 
		OLD_PASSWORD_ERROR(USER + 6, "原密码错误，请重新输入！"),
		PASSWORD_ERROR(USER + 6, "密码错误"),
		ADDRESS_NOT_EXIST(USER + 7, "收货地址不存在，请新建或者重新选择！"), 
		HAND_PASSWORD_DISABELD(USER + 8, "手势密码已被禁用"), 
		TOKEN_GET_FAILED(USER + 9, "imtoken获取失败"),
		USER_EXCEPTION(USER + 10, "您的账号数据异常，请拨打400电话联系客服解决"),
		MOBILE_NOT_FOUND(USER + 11, "您的账号尚未绑定手机号"),
		WALLET_RECHARGE_LEVEL_ERROR(USER + 12, "该服务不支持20级会员使用!"),
		USER_BE_FROZEN(USER + 13, "您的账号被冻结，请联系客服。"),
		OTHER_DEVICE_LOGIN(USER + 909, "您的账号在其他设备登录，如非本人操作请修改密码！"),
		AUTH_DENIED(USER + 919, "您的账号在其他设备登录，如非本人操作请修改密码！"), 
		
		;

		private int code;
		private String description;

		User(int code, String description) {
			this.code = code;
			this.description = description;
		}

		@Override
		public int getCode() {
			return code;
		}

		@Override
		public String getDescription() {
			return description;
		}
	}

	/**
	 * 2500-2600
	 */
	enum Cart implements ExceptionType {
		ILLEGAL_PARAMETER(2501, "参数不合法"),
		CART_ITEM_NOT_EXIST(2502, "购物车商品不存在"),
		CART_ITEM_INVALID_EXIST(2503, "购物车商品无效");
		private int code;
		private String description;

		Cart(int code, String description) {
			this.code = code;
			this.description = description;
		}

		@Override
		public int getCode() {
			return code;
		}

		@Override
		public String getDescription() {
			return description;
		}
	}

	/**
	 * 3000-3100
	 */
	enum Order implements ExceptionType {

		ORDER_NOT_EXIST(3001, "订单不存在"),
		ORDER_PAYED(3002, "订单已经支付"),
		ORDER_NOT_ALLOWED_RETURN(3003, "订单当前状态不允许申请售后"),
		ORDER_PRODUCT_NOT_EXIST(3004, "订单商品不存在"),
		ORDER_EXCHANGE_NOT_SUPPORT(3005, "本期暂不支持换货");

		private int code;
		private String description;

		Order(int code, String description) {
			this.code = code;
			this.description = description;
		}

		@Override
		public int getCode() {
			return code;
		}

		@Override
		public String getDescription() {
			return description;
		}
	}

	/**
	 * 3100-3200
	 */
	enum Payment implements ExceptionType {

		PAYMENT_ERROR(3100, "支付单异常");

		private int code;
		private String description;

		Payment(int code, String description) {
			this.code = code;
			this.description = description;
		}

		@Override
		public int getCode() {
			return code;
		}

		@Override
		public String getDescription() {
			return description;
		}
	}

}
