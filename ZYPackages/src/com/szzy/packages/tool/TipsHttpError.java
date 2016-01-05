package com.szzy.packages.tool;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

//���ڸ���HTTP������ʾ
public class TipsHttpError {

	/**
	 * #define Http_err_unknown	0xFF			//δ֪����;
#define Http_err_OK			0x00			//��ȷ,�޴���
#define Http_err_authority	0x01			//��Ȩ����
#define Http_err_GetUse		0x02			//Ͷ�����˺���Ч
#define Http_err_BoxUse		0x03			//�����ѱ�ռ��
#define Http_err_BoxErr		0x04			//��Ż���Ŵ���
#define Http_err_Record		0x05			//�޴�Ͷ�ݼ�¼
#define Http_err_user		0x06			//�û�������
#define Http_err_cmd		0x07			//�������
#define Http_err_Off		-5	//�豸������;
#define Http_err_Timeout	-1	//�豸��ʱ����Ӧ;
	 */
	public static final String OK = "0" ;//��ȷ����
	public static final String ERROR_AUTHORITY = "1";  //��Ȩ����
	public static final String ERROR_GETUSE = "2";   //Ͷ�����˺���Ч
	public static final String ERROR_BOX_USE = "3"; //�����ѱ�ռ��
	public static final String ERROR_BOX_ERROR = "4";	//��Ż���Ŵ���
	public static final String ERROR_RECORD = "5";	//�޴�Ͷ�ݼ�¼
	public static final String ERROR_USER = "6";	//�û�������
	public static final String ERROR_CMD = "7";		//�������
	public static final String ERROR_8 = "8";
	public static final String ERROR_9 = "9";
	public static final String ERROR_10 = "10";
	public static final String ERROR_UNKNOW = "255";  //δ֪����;
	public static final String ERROR_NETWORK = "256";  //�������ӳ���
	
	public static final String ERROR_TIMEOUT = "-1";  //�豸��ʱ����Ӧ;
	public static final String ERROR_2_ = "-2";
	public static final String ERROR_3_ = "-3";
	public static final String ERROR_4_ = "-4";
	public static final String ERROR_OFF = "-5";//�豸������;
//	public static Context context = null ;
	
	/**
	 * ����http����ʱ�����Ĵ���
	 * @param errorCode
	 */
	public static void toastError(final Context context ,final String errorCode){
		Handler handler = new Handler();
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				String msg = "";
				switch (errorCode) {
				case "0":
					return;
				case ERROR_AUTHORITY:
					msg = "err=" + errorCode + "��Ȩ�޲���";
					break;
				case ERROR_GETUSE:
					msg = "err=" + errorCode + "Ͷ�����˺���Ч";
					break;
				case ERROR_BOX_USE:
					msg = "err=" + errorCode + "�����ѱ�ռ��";
					break;
				case ERROR_BOX_ERROR:
					msg = "err=" + errorCode + "��Ż���Ŵ���";
					break;
				case ERROR_RECORD:
					msg = "err=" + errorCode + "�޴�Ͷ�ݼ�¼";
					break;
				case ERROR_USER:
					msg = "err=" + errorCode + "�û�������";
					break;
				case ERROR_CMD:
					msg = "err=" + errorCode + "�������";
					break;
				case ERROR_8:
//					msg = "err=" + errorCode + "�����ѱ�ռ��";
					break;
				case ERROR_9:
//					msg = "err=" + errorCode + "Ͷ���ֻ�������ʹ��Ȩ��";
					break;
				case ERROR_10:
//					msg = "err=" + errorCode + "�޴�Ͷ�ݼ�¼";
					break;
				case ERROR_UNKNOW:
					msg = "err=" + errorCode + "δ֪����";
					break;
				case ERROR_NETWORK:
					msg =  "�������ӳ�����������";
					break;
				case ERROR_TIMEOUT:
					msg = "err=" + errorCode + "�豸��ʱ����Ӧ";
					break ;
				case ERROR_2_:
					msg = "err=" + errorCode + "���ݽ��մ���";
					break ;
				case ERROR_3_:
					msg = "err=" + errorCode + "�豸�������";
					break ;
				case ERROR_4_:
					msg = "err=" + errorCode + "��ǰ����Ա�϶࣬ϵͳ��æ";
					break ;
				case ERROR_OFF:
					msg = "err=" + errorCode + "�豸�ѶϿ�����";
					break ;

				default:
					msg = "err=" + errorCode + "δ֪����";
					break ;
				}
				Toast.makeText(context, msg, 0).show();
			}
		});
	}
}
