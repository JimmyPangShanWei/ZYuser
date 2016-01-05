package com.szzy.packages.adapter;

import java.util.List;

import com.szzy.packages.R;
import com.szzy.packages.activity.MApplication;
import com.szzy.packages.entity.GetPackages;
import com.szzy.packages.http.HttpHelper;
import com.szzy.packages.http.OpenBoxCall;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NotGetPackageListViewAdapter extends BaseAdapter {
	
	private LayoutInflater inflater ;//ע����
	private List<GetPackages> listPackages ;
	private Context context ;
	private MApplication mApp ;
	private HttpHelper httpHelper ;
	private Handler mHandler = new Handler();
	
	public NotGetPackageListViewAdapter(MApplication mApp, Context context, List<GetPackages> listPackages){
		this.inflater = LayoutInflater.from(context);
		this.listPackages = listPackages;
		this.context = context ;
		this.mApp = mApp;
		httpHelper = new HttpHelper();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listPackages.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listPackages.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			//ע�벼���ļ�
			convertView = inflater.inflate(R.layout.listview_item_data_get, null);
			holder.tvOrder = (TextView) convertView.findViewById(R.id.textView_listview_item_orderid);
			holder.tvPostDate = (TextView) convertView.findViewById(R.id.textView_listview_item_post_time);
			holder.tvAddress = (TextView) convertView.findViewById(R.id.textView_listview_item_address);
			holder.tvState = (TextView) convertView.findViewById(R.id.textView_listview_item_state);
			holder.imgOrder = (ImageView) convertView.findViewById(R.id.imageView_listview_item_ogo);
			holder.tvBoxid = (TextView) convertView.findViewById(R.id.textView_listview_item_boxid);
			holder.tvGetTime = (TextView) convertView.findViewById(R.id.textView_listview_item_gettime);
			holder.btnGet = (Button) convertView.findViewById(R.id.button_listview_item_get_get);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//����������item
		if(listPackages.get(position).getState().trim().equals("0")){
			holder.tvState.setText("δǩ��");
			holder.tvOrder.setText(listPackages.get(position).getOrderNo());
			holder.tvPostDate.setText(listPackages.get(position).getTime());
			holder.tvAddress.setText(listPackages.get(position).getAddress());
			holder.tvBoxid.setText("��ţ�" + listPackages.get(position).getBoxName());
			holder.btnGet.setVisibility(View.VISIBLE);
			holder.btnGet.setOnClickListener(new MyonClick(position));
			
		}else{
			holder.tvState.setText("��ǩ��");
			holder.tvBoxid.setText("��ţ�" + listPackages.get(position).getBoxName());
			holder.tvGetTime.setText(listPackages.get(position).getGettime());
			holder.tvOrder.setText(listPackages.get(position).getOrderNo());
			holder.tvPostDate.setText(listPackages.get(position).getTime());
			holder.tvAddress.setText(listPackages.get(position).getAddress());
		}
			

			
		return convertView;
	}
	
	//ȡ���Ի���
	private void createDialog(final int position){
		Builder builder = new Builder(context);
		builder.setMessage("ȷ���Ƿ�ȡ����");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int lock = Integer.valueOf(listPackages.get(position).getLockCode());
				int boxid = Integer.valueOf(listPackages.get(position).getBoxId());
				//ȡ��
				httpHelper.getPackage(mApp.getUser(), mApp.getPassword(), lock, boxid, 1, new OpenBoxCall() {
					
					@Override
					public void call(final String errorCode) {
						Log.e("ȷ���Ƿ�ȡ��","" + errorCode);
						if("0".equals(errorCode.trim())){
//							removeItem(position);
							mHandler.post(new Runnable() {
								
								@Override
								public void run() {
									Toast.makeText(context, "ȡ���ɹ�" , 0).show();
									//���͹㲥
									Intent intent = new Intent();
									intent.setAction("update");
									context.sendBroadcast(intent);
//									removeItem(position);
									
								}
							});
						}else{
							mHandler.post(new Runnable() {
								
								@Override
								public void run() {
//									Toast.makeText(context, "ȡ��ʧ�ܣ�err=" + errorCode , 0).show();
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
	
	//ȡ����ť����
	private class MyonClick implements OnClickListener{
		private int position ;
		public MyonClick(int position){
			this.position = position;
		}
		@Override
		public void onClick(View v) {
//			Toast.makeText(context, ""+ listPackages.get(position).getBoxName(), 0).show();
			createDialog(position);
		}
		
	}
	
	
	private class ViewHolder {
		TextView tvOrder ;
		TextView tvPostDate ;
		TextView tvAddress ;
		TextView tvState ;
		ImageView imgOrder ;
		TextView tvBoxid ;
		TextView tvGetTime ;
		Button btnGet ;
		
	}

}
