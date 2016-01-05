package com.szzy.packages.base;

import com.szzy.packages.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * �����࣬��Ҫ�ṩһЩactivity�й��õķ���
 * @author mac
 *
 */
public abstract class MBaseActivity extends Activity implements OnClickListener{
	
	@Override
	protected  void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}
	/**
	 *��ʼ��UI�ؼ�
	 */
	public void initView(){
		
	}
	
	long exitSytemTime = 0;
	/**
	 * �����η��ؼ��˳�����
	 * @param keyCode
	 * @param event
	 * @return
	 */
	public boolean twoPressExit(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK){  
	        if(System.currentTimeMillis() - exitSytemTime > 2000){  
	            Toast.makeText(getApplicationContext(), R.string.again_exit_system, Toast.LENGTH_SHORT).show();  
	            exitSytemTime = System.currentTimeMillis();  
	            return true;  
	        }else{  
	            finish();  
	        }  
	          
	    }
		return false;
	}
	
	/**
	 * ��ӡLOG.E��Ϣ
	 * @param tag
	 * @param msg
	 */
	public void printLog_E(String tag , String msg){
		Log.e(tag, msg);
	}
	/**
	 * ��ӡLOG.I��Ϣ
	 * @param tag
	 * @param msg
	 */
	public void printLog_i(String tag , String msg){
		Log.i(tag, msg);
	}
	/**
	 * ��ӡLOG.D��Ϣ
	 * @param tag
	 * @param msg
	 */
	public void printLog_d(String tag , String msg){
		Log.d(tag, msg);
	}
	
	
	@Override
	public void onClick(View v) {
		
		
	}

}
