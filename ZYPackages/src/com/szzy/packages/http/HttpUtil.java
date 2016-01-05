package com.szzy.packages.http;

import java.util.ArrayList;
import java.util.List;

import com.szzy.packages.entity.Box;
import com.szzy.packages.entity.GetPackages;
import com.szzy.packages.entity.MailBox;
import com.szzy.packages.entity.PostBoxInfo;
import com.szzy.packages.entity.PostPackages;
import com.szzy.packages.entity.UserInfo;
import com.szzy.packages.entity.UserLoginInfo;

public class HttpUtil {
	
	
	/**
	 * ������������
	 * @param response  ��������������
	 * @return
	 */
	public static String getError(String response){
		String errStr = response.replaceAll("\\&.*", "");
		String errValue = errStr.replaceAll("err=", "");
		return errValue ;
	}
	
	/**
	 * �����û���¼��Ϣ
	 * @param response
	 * @return
	 */
	public static UserLoginInfo resolveUserLogin(String response){
		UserLoginInfo info = null; 
		String spid = null ;
		String state = null; 
		String mailBoxNum = null; 
		List<MailBox> listBox = null; 
		String rex0 = "err=0" ;
		if(response.startsWith(rex0)){
			info = new UserLoginInfo() ;
			rex0 = rex0 + "&" ;
			//��ȡspid
			spid = response.replaceAll(rex0, "")
					.replaceAll("\\&.*", "")
					.replaceAll("spid=", "");
			rex0 = rex0 + "spid=" + spid + "&";
			info.setSpid(spid);
			//��ȡstate
			state = response.replaceAll(rex0, "")
					.replaceAll("\\&.*", "")
					.replaceAll("state=", "");
			rex0 = rex0 + "state=" + state  + "&";
			info.setState(state) ;
			//��ȡ��������
			mailBoxNum = response.replaceAll(rex0, "")
					.replaceAll("\\&.*", "")
					.replaceAll("mailboxnum=", "");
			rex0 = rex0 + "mailboxnum=" + mailBoxNum + "&";
			//������������0
			if(mailBoxNum != null && mailBoxNum.length() > 0){
				int rows = Integer.valueOf(mailBoxNum) ;
				String[] row = response.replaceAll(rex0, "")
				.replaceAll("show.*", "")
				.split("mecode");
				String[] itemRows = null;
				if(row != null && row.length > 0){
					listBox = new ArrayList<MailBox>() ;
					for(int i = 0; i < row.length; i++){
						itemRows = row[i].split("\\&");
						if(itemRows != null && itemRows.length == 3){//3���ֶ�
							String rex1 = ".*\\=";
							MailBox mailBox = new MailBox() ;
							mailBox.setMecode(itemRows[0].replaceAll(rex1, ""));
							mailBox.setMbcode(itemRows[1].replaceAll(rex1, ""));
							mailBox.setMname(itemRows[2].replaceAll(rex1, "")) ;
							listBox.add(mailBox);
						}
					}
					info.setListBox(listBox);
					
				}
//				String rex0 = "err=0&spid=" + spid + "&state=" + state
			}
			
		}
		return info ;
	}
	
	/*******-----------------�ָ���------------------********/

	/**
	 * ����ȡ������
	 * @param response
	 * @param error
	 * @return
	 */
	public static List<GetPackages> resolveQueryData(String response, HttpError error){
		List<GetPackages> list = new ArrayList<GetPackages>();
		String errStr = response.replaceAll("\\&.*", "");
		String errValue = errStr.replaceAll("err=", "");
		error.setErroCode(errValue);//���صĴ�����
		if(response.startsWith("err=0")){
		
			String rows = response.replaceAll("err=0&", "")//��ȡ����
					.replaceAll("\\&.*", "")
					.replaceAll("rows=", "");
			if(rows != null && rows.length() > 0){
				int rowsValue = Integer.valueOf(rows); 
				if(rowsValue > 0){
					String rex0 = "err=0&rows="+rows+"&";
					String[] row = response.replaceAll(rex0, "").split("settime");//��ʱ��Ϊ�ֽ���
					String[] itemRows ;
					for(int i = 0; i < row.length; i++){
						itemRows = row[i].split("\\&");
						if(itemRows != null && itemRows.length == 9){//���ص����ݰ���8���ֶ�
							String rex1 = ".*\\=";
							GetPackages notget = new GetPackages();
							notget.setTime(itemRows[0].replaceAll(rex1, ""));
							notget.setOrderNo(itemRows[1].replaceAll(rex1, ""));
							notget.setLockCode(itemRows[2].replaceAll(rex1, ""));
							notget.setBoxId(itemRows[3].replaceAll(rex1, ""));
							notget.setBoxName(itemRows[4].replaceAll(rex1, ""));
							notget.setAddress(itemRows[5].replaceAll(rex1, ""));
							notget.setGettime(itemRows[6].replaceAll(rex1, ""));
							notget.setGetstyle(itemRows[7].replaceAll(rex1, ""));
							notget.setState(itemRows[8].replaceAll(rex1, ""));
							list.add(notget);
						}
					}
				}
			}
		}
		return list ;
	}
	
	/**
	 * ����ȡ������
	 * @param response
	 * @param error
	 * @return
	 */
	public static List<PostPackages> resolveQueryPost(String response, HttpError error){
		List<PostPackages> list = new ArrayList<PostPackages>();
		String errStr = response.replaceAll("\\&.*", "");
		String errValue = errStr.replaceAll("err=", "");
		error.setErroCode(errValue);//���صĴ�����
		if(response.startsWith("err=0")){
		
			String rows = response.replaceAll("err=0&", "")//��ȡ����
					.replaceAll("\\&.*", "")
					.replaceAll("rows=", "");
			if(rows != null && rows.length() > 0){
				int rowsValue = Integer.valueOf(rows); 
				if(rowsValue > 0){
					String rex0 = "err=0&rows="+rows+"&";
					String[] row = response.replaceAll(rex0, "").split("settime");//��ʱ��Ϊ�ֽ���
					String[] itemRows ;
					for(int i = 0; i < row.length; i++){
						itemRows = row[i].split("\\&");
						if(itemRows != null && itemRows.length == 10){//���ص����ݰ���10���ֶ�
							String rex1 = ".*\\=";
							PostPackages post = new PostPackages();
							post.setTime(itemRows[0].replaceAll(rex1, ""));
							post.setOrderNo(itemRows[1].replaceAll(rex1, ""));
							post.setuTel(itemRows[2].replaceAll(rex1, ""));
							post.setLockCode(itemRows[3].replaceAll(rex1, ""));
							post.setBoxId(itemRows[4].replaceAll(rex1, ""));
							post.setBoxName(itemRows[5].replaceAll(rex1, ""));
							post.setAddress(itemRows[6].replaceAll(rex1, ""));
							post.setGettime(itemRows[7].replaceAll(rex1, ""));
							post.setGetstyle(itemRows[8].replaceAll(rex1, ""));
							post.setState(itemRows[9].replaceAll(rex1, ""));
							list.add(post);
						}
					}
				}
			}
		}
		return list ;
	}
	
	/**
	 * ������¼��Ϣ
	 * @param response
	 * @return
	 */
	public static UserInfo resolveLogin(String response){
		UserInfo user = new UserInfo() ; ;
		/**
		 */
		String id = response.replaceAll("err=0&", "")
				.replaceAll("\\&.*", "")
				.replaceAll("id=", "");
		user.setId(id);
		String tel = response.replaceAll("err=0\\&id="+id+"\\&", "")
				.replaceAll("\\&.*", "")
				.replaceAll("Tel=", "");
		user.setTel(tel);
		String loginName = response.replaceAll("err=0\\&id="+id+"\\&Tel=" + tel + "\\&", "")
				.replaceAll("\\&.*", "")
				.replaceAll("loginName=", "");
		user.setLoginName(loginName);
		String userName = response.replaceAll("err=0\\&id="+id+"\\&Tel=" + tel + "\\&loginName=" + loginName + "\\&", "")
				.replaceAll("\\&.*", "")
				.replaceAll("username=", "");
		user.setUserName(userName);
		String idCard = response.replaceAll("err=0\\&id="+id+"\\&Tel=" + tel + "\\&loginName=" + loginName + "\\&username=" + userName + "\\&", "")
				.replaceAll("\\&.*", "")
				.replaceAll("idcard=", "");
		user.setIdCard(idCard);
		String email = response.replaceAll("err=0\\&id="+id+"\\&Tel=" + tel + "\\&loginName=" 
				+ loginName + "\\&username=" + userName + "\\&idcard=" + idCard + "\\&", "")
				.replaceAll("\\&.*", "")
				.replaceAll("email=", "");
		user.setEmail(email);
		String state = response.replaceAll("err=0\\&id="+id+"\\&Tel=" + tel + "\\&loginName=" 
				+ loginName + "\\&username=" + userName + "\\&idcard=" + idCard + "\\&email=" + email + "\\&", "")
				.replaceAll("\\&.*", "")
				.replaceAll("state=", "");
		user.setState(state) ;
		return user;
	}
	
	
	/**
	 * ���������豸����״̬
	 * @param resp
	 * @param error
	 * @return
	 */
	public static PostBoxInfo resolveQueryBox(String response, HttpError error){
		PostBoxInfo boxInfo = null;
		List<Box> listBox = new ArrayList<Box>();
		String errStr = response.replaceAll("\\&.*", "");
		String errValue = errStr.replaceAll("err=", "");
		error.setErroCode(errValue);//���صĴ�����
		if(response.startsWith("err=0")){
			boxInfo = new PostBoxInfo();
			String cabinetName = response.replaceAll("err=0&", "")//��ȡ�������
					.replaceAll("\\&.*", "")
					.replaceAll("cabinetname=", "");
			boxInfo.setLockName(cabinetName);//����Ϊ16��������
			String rowStr = response.replaceAll("err=0\\&cabinetname="+cabinetName+"\\&", "")
					.replaceAll("\\&.*", "")
					.replaceAll("boxnum=", "");
			if(rowStr != null && rowStr.length() > 0){
				int rows = Integer.valueOf(rowStr);
				if(rows > 0){
					boxInfo.setBoxNum(rows);//�����������
					String rex0 = "err=0\\&cabinetname="+cabinetName+"\\&boxnum=" + rowStr;
					String[] row = response.replaceAll(rex0, "").split("id");//��idΪ�ֽ���
					String[] itemRows ;
					for(int i = 0; i < row.length; i++){
						itemRows = row[i].split("\\&");
						if(itemRows != null && itemRows.length == 4){//���ص����ݰ���4���ֶ�
							Box box = new Box();
							String rex1 = ".*\\=";
							box.setBoxid(itemRows[0].replaceAll(rex1, ""));
							box.setBoxName(itemRows[1].replaceAll(rex1, ""));
							String type = itemRows[2].replaceAll(rex1, "");//�ж������ͺţ�С��6
							if(type != null && type.length() > 0 && Integer.valueOf(type) < 5){
								box.setBoxType(type);
								box.setBoxState(itemRows[3].replaceAll(rex1, ""));
								listBox.add(box);
							}
							
						}
					}
					boxInfo.setListBox(listBox);//�������
				}
			}
		}
		return boxInfo ;
	}
	
}
