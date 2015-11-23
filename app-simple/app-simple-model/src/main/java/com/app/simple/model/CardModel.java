package com.app.simple.model;

import java.util.List;



public class CardModel {

	private String cardNo;//会员卡号
	private String relativeDistance;//距离或告警类型
	private String referencePoint;//参考点
	//心跳协议新加字段区别对待
	private String headSign;//头标识 FA--报警或定位数据包，FC/FD--心跳数据包
	private List<String> areaNoList;//区域号集合，存心跳包传过来的区域节点中心节点id
	
	/**
	 * 是否心跳信息
	 * @return
	 */
	public boolean isHeartbeatInfo(){
		boolean heartBeat = "FC".equals(headSign) || "FD".equals(headSign);
		return heartBeat;
	}
	
	/**
	 * 是否告警或定位信息
	 * @return
	 */
	public boolean isAlarmOrPosition(){
		return "FA".equals(headSign);
	}
	
	/**是告警报文*/
	public boolean isAlarm(){
		return "FA".equals(headSign) && ("FF".equals(relativeDistance) || "FE".equals(relativeDistance));
	}
	
	/**是定位报文*/
	public boolean isPosition(){
		return "FA".equals(headSign) && !"FF".equals(relativeDistance) && !"FE".equals(relativeDistance);
	}
	
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getRelativeDistance() {
		return relativeDistance;
	}
	public void setRelativeDistance(String relativeDistance) {
		this.relativeDistance = relativeDistance;
	}
	public String getReferencePoint() {
		return referencePoint;
	}
	public void setReferencePoint(String referencePoint) {
		this.referencePoint = referencePoint;
	}

	public String getHeadSign() {
		return headSign;
	}
	public void setHeadSign(String headSign) {
		this.headSign = headSign;
	}
	public List<String> getAreaNoList() {
		return areaNoList;
	}
	public void setAreaNoList(List<String> areaNoList) {
		this.areaNoList = areaNoList;
	}
}
