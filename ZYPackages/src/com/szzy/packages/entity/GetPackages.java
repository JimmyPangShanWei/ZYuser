package com.szzy.packages.entity;

import java.io.UnsupportedEncodingException;

import com.szzy.packages.tool.Tools;

public class GetPackages {
	String time ;			//Ͷ��ʱ��
	String orderNo ;		//��ݵ���
	String lockCode ;		//��ݹ���
	String boxId ;			//��ݹ����
	String address ;		//��ݹ��ʶ
	String gettime ;		//ȡ��ʱ��
	String getstyle ;		//ȡ����ʽ
	String state ;			//��ǰ״̬
	String boxName ;		//��������
	
	public String getBoxName() {
		return boxName;
	}
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getLockCode() {
		return lockCode;
	}
	public void setLockCode(String lockCode) {
		this.lockCode = lockCode;
	}
	public String getBoxId() {
		return boxId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	public String getAddress() {
		byte[] addressBytes = Tools.HexString2Bytes(address);
		String addr = null;
		try {
			addr = new String(addressBytes, "gb2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return addr;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGettime() {
		return gettime;
	}
	public void setGettime(String gettime) {
		this.gettime = gettime;
	}
	public String getGetstyle() {
		return getstyle;
	}
	public void setGetstyle(String getstyle) {
		this.getstyle = getstyle;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
