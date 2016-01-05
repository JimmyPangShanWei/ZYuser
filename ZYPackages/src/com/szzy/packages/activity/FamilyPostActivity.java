package com.szzy.packages.activity;

import com.szzy.packages.R;
import com.szzy.packages.entity.PostBoxInfo;
import com.szzy.packages.http.HttpHelper;
import com.szzy.packages.http.OpenBoxCall;
import com.szzy.packages.http.QueryBoxCall;
import com.szzy.packages.tool.TipsHttpError;
import com.szzy.packages.tool.Utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ���ѼĴ�
 * @author mac
 *
 */
public class FamilyPostActivity extends Activity implements OnClickListener{
	private TextView tvTitle ;//����
	private ImageView imgBack ; //���ؼ�
	
	private EditText editLock ;//����
	private ImageView imgSys ;//ɨһɨ
	private Button btnSelectBox ; //ѡ�����
	private Button btnOpenBox ; //����Ͷ��
	private boolean isSelectLock ;//�Ƿ�ɨ�������
	private EditText editTel ;//�ռ��˵绰
	private EditText editLetter ;//��ע����,��֪��δ�����Ϣ
	private String lockCode ;  //�����
	private String lockName ;  //�������
	private int postTel = 0 ;
	private final static int SCANNIN_LOCK_CODE = 11;   //ɨ�������
	private String boxName  ; //���ź�
	private String boxId ; //����ID 
	private MApplication mApp ;
	private HttpHelper httpHelper ;
	private Handler mHandler = new Handler() ;
	
	private int serverTel = 0 ;
	private String telStr = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_family_post) ;
		mApp = (MApplication) getApplication() ;
		httpHelper = new HttpHelper() ;
		initView() ;
	}
	
	private void initView(){
		tvTitle = (TextView) findViewById(R.id.textView_title) ;
		tvTitle.setText("���ѼĴ�");
		imgBack = (ImageView) findViewById(R.id.imageButton_back) ;
		editTel = (EditText) findViewById(R.id.editText_family_tel) ;
		editLetter = (EditText) findViewById(R.id.editText_family_letter) ;
		editLock = (EditText) findViewById(R.id.editText_family_lock) ;
		imgSys = (ImageView) findViewById(R.id.imageView_family_sys) ;
		btnSelectBox = (Button) findViewById(R.id.button_family_select_box) ;
		btnOpenBox = (Button) findViewById(R.id.button_family_commit) ;
		
		imgSys.setOnClickListener(this);
		btnSelectBox.setOnClickListener(this);
		btnOpenBox.setOnClickListener(this);
		//��������绰��λ��
		editTel.addTextChangedListener(new TextWatcher() {
			 private CharSequence temp;//����ǰ���ı�  
		       private int editStart;//��꿪ʼλ��  
		       private int editEnd;//������λ��  
		       private final int charMaxNum = 11; //����ı���
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				temp = s;
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				editStart = editTel.getSelectionStart();
				editEnd = editTel.getSelectionEnd() ;
				if (temp.length() > charMaxNum) { 
					Toast.makeText(mApp, "���Ѿ�������11λ�绰������", 0).show();
					s.delete(editStart - 1, editEnd);
					int tempSelection = editStart;
					editTel.setText(s);
//					telStr = s.toString() ;
					editTel.setSelection(tempSelection);
				}
				telStr = editTel.getText().toString();
			}
		});
		btnSelectBox.setOnClickListener(this) ;
		btnOpenBox.setOnClickListener(this) ;
		imgBack.setOnClickListener(this) ;
	}
	
	private final int SELECT_BOX_REQUEST_CODE = 2 ;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case SCANNIN_LOCK_CODE://���������
			if(resultCode == RESULT_OK){
				Log.e("lock", ""+ mApp.getLockId()) ;
				lockCode = mApp.getLockId().trim();
				try{
					int lock = Integer.valueOf(lockCode) ;
					//��ѯ����״̬
					httpHelper.queryBoxInfo(mApp.getUser(), mApp.getPassword(), lock, new QueryBoxCall() {
					
					@Override
					public void call( PostBoxInfo boxInfo) {
						if(boxInfo!= null){
							mApp.setListBox( boxInfo.getListBox());
							 lockName = boxInfo.getLockName();
							 isSelectLock = true ; 
							//���¹�����
							mHandler.post(new Runnable() {
								
								@Override
								public void run() {
									editLock.setText(lockName);
								}
							});
						}
						
					}
				});
				}catch(Exception e){
					Log.e("",e.toString());
				}
				
				//��ѯ����״̬
//				httpHelper.queryBoxInfo(mApp.getUser(), mApp.getPassword(), lock, new QueryBoxCall() {
//					
//					@Override
//					public void call( PostBoxInfo boxInfo) {
//						if(boxInfo!= null){
//							mApp.setListBox( boxInfo.getListBox());
//							 lockName = boxInfo.getLockName();
//							//���¹�����
//							mHandler.post(new Runnable() {
//								
//								@Override
//								public void run() {
//									editLock.setText(lockName);
//								}
//							});
//						}
//						
//					}
//				});
			}			
			break ;
		case SELECT_BOX_REQUEST_CODE://ѡ�������
			if(resultCode == RESULT_OK){
				int position = data.getIntExtra("position", 0);
				boxName = mApp.getListBox().get(position).getBoxName();
				btnSelectBox.setText(boxName);
				boxId = mApp.getListBox().get(position).getBoxid();
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageButton_back://����
			finish() ;
			break;
		case R.id.imageView_family_sys://ɨһɨ���
			Intent intent = new Intent(FamilyPostActivity.this, MipcaActivityCapture.class);
			intent.putExtra("mode", 1);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_LOCK_CODE);
			break; 
		case R.id.button_family_select_box://ѡ������
			//ѡ������֮ǰҪ���ж����Ƿ�ɨ�������
			if(isSelectLock){
				Intent toSelect = new Intent(FamilyPostActivity.this, SelectBoxActivity.class);
				startActivityForResult(toSelect, SELECT_BOX_REQUEST_CODE);
			}else{
				Toast.makeText(getApplicationContext(), "����ɨ�����", 0).show() ;
			}
			break;
		case R.id.button_family_commit://����Ͷ��
			Utils.tipsUnfinish(this) ;
//			if(telStr.length() != 11){
//				Toast.makeText(mApp, "�������ռ���11Ϊ�绰����", 0).show() ;
//				return ; 
//			}
//			if(!isSelectLock){
//				Toast.makeText(getApplicationContext(), "����ɨ�����", 0).show() ;
//				return ;
//			}
//			if(boxId == null || boxId.length() < 1){
//				Toast.makeText(mApp, "��ѡ���ݹ�����", 0).show();
//				return;
//			}
//			httpHelper.openBox(mApp.getUser(), mApp.getPassword(), Integer.valueOf(lockCode), boxId, new OpenBoxCall() {
//				
//				@Override
//				public void call(final String errorCode) {
//					Log.e("errorCode----", errorCode);
//					mHandler.post(new Runnable() {
//						
//						@Override
//						public void run() {
//							if("0".equals(errorCode)){
//								createDialog();
//							}
//							TipsHttpError.toastError(mApp, errorCode);
//							
//						}
//					});
//					
//					
//				}
//			});
			break;

		default:
			break;
		}
		
	}
	
	private TextView tvDiaLock ;
	private TextView tvDiaCompany  ;
	private TextView tvDiaCompanyType ;
	private TextView tvDiaBox ;
	
	//�����Ի���
	private void createDialog(){
		Builder builder = new Builder(this);
		LayoutInflater inflater = LayoutInflater.from(this);
		View view  = inflater.inflate(R.layout.dialog_user_express, null);
		builder.setView(view);
		tvDiaLock = (TextView) view.findViewById(R.id.textView_dialog_user_express_lock) ;
		tvDiaCompany = (TextView) view.findViewById(R.id.textView_dialog_user_express_post_company) ;
		tvDiaCompanyType = (TextView) view.findViewById(R.id.textView_dialog_user_express_post_company_type) ;
		tvDiaCompanyType.setText("�ռ��˵绰:");
		tvDiaBox = (TextView) view.findViewById(R.id.textView_dialog_user_express_boxname) ;
		tvDiaLock.setText(lockName);
		tvDiaCompany.setText(telStr);
		tvDiaBox.setText(boxName);
		builder.setPositiveButton("ȷ��Ͷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.e("TEL", postTel + "") ;
				//�ύ
				httpHelper.postPackages(mApp.getUser(), mApp.getPassword(),
						Integer.valueOf(lockCode), boxId, telStr, "", new OpenBoxCall() {
					
					@Override
					public void call(String errorCode) {
						Log.e("55", errorCode);
						if("0".equals(errorCode.trim())){
							mHandler.post(new Runnable() {
								
								@Override
								public void run() {
									Toast.makeText(mApp, "Ͷ�ݳɹ�", 0).show();
									btnSelectBox.setText("���ѡ������");
									//��������״̬
									httpHelper.queryBoxInfo(mApp.getUser(), mApp.getPassword(), Integer.valueOf(lockCode), new QueryBoxCall() {
										
										@Override
										public void call( PostBoxInfo boxInfo) {
											if(boxInfo!= null){
												mApp.setListBox( boxInfo.getListBox());
												 lockName = boxInfo.getLockName();
												//���¹�����
												mHandler.post(new Runnable() {
													
													@Override
													public void run() {
														editLock.setText(lockName);
													}
												});
											}
											
										}
									});
								}
							});
						}
						
					}
				});
				
			}
		});
		builder.setNegativeButton("ȡ��", null) ;
		builder.create().show() ;
	}
}
