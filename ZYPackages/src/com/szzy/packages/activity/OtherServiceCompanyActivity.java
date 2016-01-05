package com.szzy.packages.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.szzy.packages.R;
import com.szzy.packages.entity.PostBoxInfo;
import com.szzy.packages.http.HttpHelper;
import com.szzy.packages.http.OpenBoxCall;
import com.szzy.packages.http.QueryBoxCall;
import com.szzy.packages.tool.TipsHttpError;
import com.szzy.packages.tool.Utils;
/**
 * ����������˾
 * @author mac
 *
 */
public class OtherServiceCompanyActivity extends Activity implements OnClickListener{

	private TextView tvTitle ;//����
	private ImageView imgBack ; //���ؼ�
	
	private GridView gridCompany ;
	//��˾
	private int[] companys = new int[] {R.drawable.other_company_edx};
	private GridviewAdapter adapter ;
	
	private EditText editLock ;//����
	private ImageView imgSys ;//ɨһɨ
	private TextView tvCompany ; //����˾
	private Button btnSelectBox ; //ѡ�����
	private Button btnOpenBox ; //����Ͷ��
	
	private boolean isSelectLock ;//�Ƿ�ɨ�������
	private final static int SCANNIN_LOCK_CODE = 11;   //ɨ�������
	private String lockCode ;  //�����
	private String lockName ;  //�������
	private int postTel = 0 ;
	
	private String boxName  ; //���ź�
	private String boxId ; //����ID 
	private MApplication mApp ;
	private HttpHelper httpHelper ;
	private Handler mHandler = new Handler() ;
	
	private int serverTel = 0 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_service_company) ;
		mApp = (MApplication) getApplication();
		httpHelper = new HttpHelper();
		initView() ;
	}
	
	private void initView(){
		tvTitle = (TextView) findViewById(R.id.textView_title) ;
		tvTitle.setText("����������˾");
		imgBack = (ImageView) findViewById(R.id.imageButton_back) ;
		imgBack.setOnClickListener(this) ;
		
		editLock = (EditText) findViewById(R.id.editText_other_service_company_lock) ;
		imgSys = (ImageView) findViewById(R.id.imageView_other_service_company_sys);
		tvCompany = (TextView) findViewById(R.id.textView_other_service_company);
		btnSelectBox = (Button) findViewById(R.id.button_other_service_company_select_box);
		btnOpenBox = (Button) findViewById(R.id.button_other_service_company_commit ) ;
		
		imgSys.setOnClickListener(this) ;
		btnSelectBox.setOnClickListener(this) ;
		btnOpenBox.setOnClickListener(this) ;
		gridCompany = (GridView) findViewById(R.id.gridView_other_service_company) ;
		adapter = new GridviewAdapter() ;
		gridCompany.setAdapter(adapter) ;
		//���Item
		gridCompany.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				serverTel = position + 10 ;
				switch (position) {
				case 0:
					tvCompany.setText("e��ϴ");
					
					break;

				default:
					break;
				}
				
			}
		});
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
	
	//gridView������
	private class GridviewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return companys.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return companys[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHold hold = null ;
			if(convertView == null){
				hold = new ViewHold() ;
				convertView = LayoutInflater.from(OtherServiceCompanyActivity.this).inflate(R.layout.gridview_item_post_company, null);
				hold.img = (ImageView) convertView.findViewById(R.id.imageView_gridview_item_post_company) ;
				convertView.setTag(hold) ;
			}else{
				hold = (ViewHold) convertView.getTag() ;
			}
			//���ͼƬ
			hold.img.setImageResource(companys[position]) ;
			return convertView;
		}
		
		private class ViewHold{
			ImageView img ;
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageButton_back://����
			finish() ;
			break;
		case R.id.imageView_other_service_company_sys://ɨһɨ
			Intent intent = new Intent(OtherServiceCompanyActivity.this, MipcaActivityCapture.class);
			intent.putExtra("mode", 1);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_LOCK_CODE);
			break;
		case R.id.button_other_service_company_select_box://ѡ������
			//ѡ������֮ǰҪ���ж����Ƿ�ɨ�������
			if(isSelectLock){
				Intent toSelect = new Intent(OtherServiceCompanyActivity.this, SelectBoxActivity.class);
				startActivityForResult(toSelect, SELECT_BOX_REQUEST_CODE);
			}else{
				Toast.makeText(getApplicationContext(), "����ɨ�����", 0).show() ;
			}
			break;
		case R.id.button_other_service_company_commit://����Ͷ��
			Utils.tipsUnfinish(this) ;
//			if(serverTel == 0){
//				Toast.makeText(mApp, "��ѡ�����˾", 0).show() ;
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
		tvDiaCompanyType.setText("����������˾:");
		tvDiaBox = (TextView) view.findViewById(R.id.textView_dialog_user_express_boxname) ;
		tvDiaLock.setText(lockName);
		tvDiaCompany.setText(tvCompany.getText().toString());
		tvDiaBox.setText(boxName);
		builder.setPositiveButton("ȷ��Ͷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.e("TEL", postTel + "") ;
				String tel = "000000000" + serverTel ;
				//�ύ
				httpHelper.postPackages(mApp.getUser(), mApp.getPassword(),
						Integer.valueOf(lockCode), boxId, tel, "", new OpenBoxCall() {
					
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
