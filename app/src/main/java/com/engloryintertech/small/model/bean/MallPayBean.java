package com.engloryintertech.small.model.bean;

import java.io.Serializable;

/**微信支付*/
public class MallPayBean implements Serializable{

	private String AppId;
	private String PrepayId;      //预支付交易会话ID
	/**微信*/
	private String PartnerId;     //商户号
	private String PackageValue;  //固定值 Sign=WXPay
	private String NonceStr;      //随机字符串
	private long TimeStamp;       //时间戳
	private String Sign;          //签名
	/**支付宝*/
	private String SignAliPay;

	/**微信*/
	public MallPayBean(String PrepayId, String NonceStr, long TimeStamp, String Sign){
		this.PrepayId = PrepayId;
		this.NonceStr = NonceStr;
		this.TimeStamp = TimeStamp;
		this.Sign = Sign;
	}

	/**支付宝*/
	public MallPayBean(String PrepayId, String SignAliPay){
		this.PrepayId = PrepayId;
		this.SignAliPay = SignAliPay;
	}

	public void setAppId(String appId) {
		AppId = appId;
	}

	public void setPartnerId(String partnerId) {
		PartnerId = partnerId;
	}

	public void setPackageValue(String packageValue) {
		PackageValue = packageValue;
	}

	public void setPrepayId(String prepayId) {
		PrepayId = prepayId;
	}

	public void setNonceStr(String nonceStr) {
		NonceStr = nonceStr;
	}

	public void setTimeStamp(long timeStamp) {
		TimeStamp = timeStamp;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	public String getAppId() {
		return AppId;
	}

	public String getPartnerId() {
		return PartnerId;
	}

	public String getPackageValue() {
		return PackageValue;
	}

	public String getPrepayId() {
		return PrepayId;
	}

	public long getTimeStamp() {
		return TimeStamp;
	}

	public String getNonceStr() {
		return NonceStr;
	}

	public String getSign() {
		return Sign;
	}

	public void setSignAliPay(String signAliPay) {
		SignAliPay = signAliPay;
	}

	public String getSignAliPay() {
		return SignAliPay;
	}
}
