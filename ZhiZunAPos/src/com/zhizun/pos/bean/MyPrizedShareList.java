package com.zhizun.pos.bean;

import com.zhizun.pos.base.BaseBean;
	/**
	 * 参与详情列表
	 */
public class MyPrizedShareList extends BaseBean {

	private static final long serialVersionUID = 1L;
	private String type;
	private String lastUpdateTime;
	private String refId;
	private String smpType;//分享平台类型1：微信朋友圈；2：QQ；3：新浪微博；4：腾讯微博；5：人人网
	private String newFlag;//分享是否有效
	private String shareTime;
	private String isValid;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getSmpType() {
		return smpType;
	}
	public void setSmpType(String smpType) {
		this.smpType = smpType;
	}
	public String getNewFlag() {
		return newFlag;
	}
	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}
	public String getShareTime() {
		return shareTime;
	}
	public void setShareTime(String shareTime) {
		this.shareTime = shareTime;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	

}
