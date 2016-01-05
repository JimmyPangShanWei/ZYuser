package com.szzy.packages.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Layout;
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

import com.szzy.packages.R;
import com.szzy.packages.base.MBaseActivity;
import com.szzy.packages.entity.PostBoxInfo;
import com.szzy.packages.http.HttpHelper;
import com.szzy.packages.http.OpenBoxCall;
import com.szzy.packages.http.QueryBoxCall;
import com.szzy.packages.tool.TipsHttpError;

//��дͶ����Ϣ
public class PostInfoActivity extends Activity implements OnClickListener{
	private final static int SCANNIN_ORDER_CODE = 1;  //ɨ���ݵ�
	
	private final static int SCANNIN_LOCK_CODE = 11;   //ɨ�������
	private EditText editOrder ;
	private EditText editLock ;
	private ImageView imgSysOrder ;//ɨһɨ��ݵ���
	private ImageView imgSysLock ;//ɨһɨ���ź�
	private EditText editTel ;
	private Button btnSelectBox ;
	private Button btnOpenBox ;//����Ͷ��
	private ImageView imgBack ;//����
	
	private MApplication mApp ;
	private HttpHelper httpHelper ;//HTTP ������
	private Handler mHandler = new Handler();
	
	private String lockCode = null;//��ݹ���
	private String orderNo = null;//��ݵ���
	private String tel = null;//�ռ��˵绰
	private String boxId = null;//������
	
	private boolean openBox = false ;//�����־
	private boolean selectFlag = false ;//�Ƿ�ѡ�������Ϊ����ı�־
	private String lockName = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_postinfo);
		super.onCreate(savedInstanceState);
//		Intent dataIntent = getIntent();
//		String result = dataIntent.getStringExtra("result");
		mApp = (MApplication) getApplication();
		lockCode = mApp.getLockId();
//		String result = mApp.getLockId();
		httpHelper = new HttpHelper();
		Log.e("", "" +lockCode);
		
		initView();

	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	public void initView() {
		editOrder = (EditText) findViewById(R.id.editText_post_order);
		imgSysOrder = (ImageView) findViewById(R.id.imageView_post_order_sys);
		imgSysLock = (ImageView) findViewById(R.id.imageView_post_lock_sys) ;
		editTel = (EditText) findViewById(R.id.editText_post_recv_tel);
		btnSelectBox = (Button) findViewById(R.id.button_post_select_box);
		editLock = (EditText) findViewById(R.id.editText_post_lock);
		btnOpenBox = (Button) findViewById(R.id.button_post_commit);
		imgBack = (ImageView) findViewById(R.id.imageButton_postinfo_back);
		
		imgBack.setOnClickListener(this);
		btnOpenBox.setOnClickListener(this);
		imgSysOrder.setOnClickListener(this);
		btnSelectBox.setOnClickListener(this);
		imgSysLock.setOnClickListener(this) ;
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
					editTel.setSelection(tempSelection);
				}
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("onActivityResult", "requestCode = " + requestCode + "resultCode = " + resultCode);
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
							 selectFlag = true ; 
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
			break;
		case SCANNIN_ORDER_CODE://����ɨ����
			if(resultCode == RESULT_OK){
				String orderId = data.getExtras().getString("barcode");
				if(orderId != null){
					editOrder.setText(orderId);
				}
			}
			break;
		case SELECT_BOX_REQUEST_CODE://ѡ�������
			if(resultCode == RESULT_OK){
				int position = data.getIntExtra("position", 0);
				boxName = mApp.getListBox().get(position).getBoxName();
				btnSelectBox.setText(boxName);
				boxId = mApp.getListBox().get(position).getBoxid();
			}
			break;

		default:
			break;
		}
		
	}
	

	private final int SELECT_BOX_REQUEST_CODE = 2 ;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageButton_postinfo_back:
			finish();
			break;
		case R.id.imageView_post_order_sys://ɨ���ݵ�
			Intent intent = new Intent(PostInfoActivity.this, MipcaActivityCapture.class);
			intent.putExtra("mode", 0);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_ORDER_CODE);
			break;
		case R.id.imageView_post_lock_sys://ɨ���ݹ�
			Intent intentLock = new Intent(PostInfoActivity.this, MipcaActivityCapture.class);
			intentLock.putExtra("mode", 1);
			intentLock.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intentLock, SCANNIN_LOCK_CODE);
			break;
		case R.id.button_post_select_box://ѡ�����
			//ѡ������֮ǰҪ���ж����Ƿ�ɨ�������
			if(selectFlag){
				Intent toSelect = new Intent(PostInfoActivity.this, SelectBoxActivity.class);
				startActivityForResult(toSelect, SELECT_BOX_REQUEST_CODE);
			}else{
				Toast.makeText(mApp, "����ɨ������ź��ٲ���", 0).show();
			}
			break;
		case R.id.button_post_commit:
			orderNo = editOrder.getText().toString().trim();
			tel = editTel.getText().toString().trim();
			if(orderNo == null || orderNo.length() < 1){
				Toast.makeText(mApp, "�������ݵ���", 0).show();
				return;
			}
			if(tel == null || tel.length() < 1){
				Toast.makeText(mApp, "�������ռ��˵绰", 0).show();
				return;
			}
			if(boxId == null || boxId.length() < 1){
				Toast.makeText(mApp, "��ѡ���ݹ�����", 0).show();
				return;
			}
			//����
//			if(!openBox){
//				openBox = true ;
//				
				httpHelper.openBox(mApp.getUser(), mApp.getPassword(), Integer.valueOf(lockCode), boxId, new OpenBoxCall() {
					
					@Override
					public void call(final String errorCode) {
						Log.e("errorCode----", errorCode);
						mHandler.post(new Runnable() {
							
							@Override
							public void run() {
								if("0".equals(errorCode)){
									createDialog();
								}
								TipsHttpError.toastError(mApp, errorCode);
								
							}
						});
						
						
					}
				});
//			}
			break;

		default:
			break;
		}
	}
	
	String boxName = "";
	//����ȷ�϶Ի���
	private void createDialog(){
		Builder builder = new Builder(this);
		LayoutInflater inflater = LayoutInflater.from(this);
		View view  = inflater.inflate(R.layout.dialog_post_info, null);
		builder.setView(view);
		TextView tvDialogLock = (TextView) view.findViewById(R.id.textView_dialog_lockname);
		TextView tvDialogOrder = (TextView) view.findViewById(R.id.textView_dialog_orderno);
		TextView tvDialogTel = (TextView) view.findViewById(R.id.textView_dialog_tel);
		TextView tvDialogBoxName = (TextView) view.findViewById(R.id.textView_dialog_boxname);
		tvDialogLock.setText(lockName);
		tvDialogOrder.setText(orderNo);
		tvDialogTel.setText(tel);
		tvDialogBoxName.setText(boxName);
		builder.setPositiveButton("ȷ��Ͷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				openBox = false ;
				//�ύ
				httpHelper.postPackages(mApp.getUser(), mApp.getPassword(),
						Integer.valueOf(lockCode), boxId, tel, orderNo, new OpenBoxCall() {
					
					@Override
					public void call(String errorCode) {
						Log.e("55", errorCode);
						if("0".equals(errorCode.trim())){
							mHandler.post(new Runnable() {
								
								@Override
								public void run() {
									Toast.makeText(mApp, "Ͷ�ݳɹ�", 0).show();
									openBox = false ;
									editOrder.setText("");
									editTel.setText("");
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
		builder.setNegativeButton("ȡ��", null);
		builder.create().show();
		
	}
	
}
