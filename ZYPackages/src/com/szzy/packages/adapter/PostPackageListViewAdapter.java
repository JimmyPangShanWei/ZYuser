package com.szzy.packages.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.szzy.packages.R;
import com.szzy.packages.activity.MApplication;
import com.szzy.packages.entity.PostPackages;
import com.szzy.packages.http.HttpHelper;
import com.szzy.packages.http.OpenBoxCall;

public class PostPackageListViewAdapter extends BaseAdapter {

	private LayoutInflater inflater ;//注入器
	private List<PostPackages> listPackages ;
	
	private Context context ;
	private MApplication mApp ;
	private HttpHelper httpHelper ;
	private Handler mHandler = new Handler();
	
	public PostPackageListViewAdapter(MApplication mApp, Context context,List<PostPackages> listPost){
		inflater = LayoutInflater.from(context);
		this.listPackages = listPost ;
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
			//注入布局文件
			convertView = inflater.inflate(R.layout.listview_item_data_post, null);
			holder.tvOrder = (TextView) convertView.findViewById(R.id.textView_listview_item_post_orderid);
			holder.tvPostDate = (TextView) convertView.findViewById(R.id.textView_listview_item_post_post_time);
			holder.tvAddress = (TextView) convertView.findViewById(R.id.textView_listview_item_post_address);
			holder.tvState = (TextView) convertView.findViewById(R.id.textView_listview_item_post_state);
			holder.imgOrder = (ImageView) convertView.findViewById(R.id.imageView_listview_item_post_ogo);
			holder.tvBoxid = (TextView) convertView.findViewById(R.id.textView_listview_item_post_boxid);
			holder.tvGetTime = (TextView) convertView.findViewById(R.id.textView_listview_item_post_gettime);
			holder.tvTel = (TextView) convertView.findViewById(R.id.textView_listview_item_post_tel);
			holder.btnGet = (Button) convertView.findViewById(R.id.button_listview_item_post_get_package);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//将数据填入item
		if(listPackages.get(position).getState().trim().equals("0")){
			holder.tvState.setText("未签收");
			holder.tvOrder.setText(listPackages.get(position).getOrderNo());
			holder.tvPostDate.setText(listPackages.get(position).getTime());
			holder.tvAddress.setText(listPackages.get(position).getAddress());
			holder.tvTel.setText(listPackages.get(position).getuTel());
			holder.tvBoxid.setText("箱号：" + listPackages.get(position).getBoxName());
			holder.btnGet.setVisibility(View.VISIBLE);
			holder.btnGet.setOnClickListener(new MyonClick(position));
		}else{
			holder.tvState.setText("已签收");
			holder.tvBoxid.setText("箱号：" + listPackages.get(position).getBoxName());
			holder.tvGetTime.setText(listPackages.get(position).getGettime());
			holder.tvOrder.setText(listPackages.get(position).getOrderNo());
			holder.tvPostDate.setText(listPackages.get(position).getTime());
			holder.tvAddress.setText(listPackages.get(position).getAddress());
			holder.tvTel.setText(listPackages.get(position).getuTel());
			holder.btnGet.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	//取件按钮监听
	private class MyonClick implements OnClickListener{
		private int position ;
		public MyonClick(int position){
			this.position = position;
		}
		@Override
		public void onClick(View v) {
			createDialog(position);
		}
		
	}
	
	//取件对话框
	private void createDialog(final int position){
		Builder builder = new Builder(context);
		builder.setMessage("确认是否取件？");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int lock = Integer.valueOf(listPackages.get(position).getLockCode());
				int boxid = Integer.valueOf(listPackages.get(position).getBoxId());
				//取件
				httpHelper.getPackage(mApp.getUser(), mApp.getPassword(), lock, boxid, 2, new OpenBoxCall() {
					
					@Override
					public void call(String errorCode) {
						Log.e("确认是否取件","" + errorCode);
						if("0".equals(errorCode.trim())){
							mHandler.post(new Runnable() {
								
								@Override
								public void run() {
									Toast.makeText(context, "取件成功" , 0).show();
									//发送广播
									Intent intent = new Intent();
									intent.setAction("update");
									context.sendBroadcast(intent);
//									removeItem(position);
									
								}
							});
						}else{
							Toast.makeText(context, "取件失败，err=" + errorCode , 0).show();
						}
					}
				});
				
			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();
	}

	
	private void removeItem(int position){
//		this.listPackages.remove(position);
		List<PostPackages> mList = new ArrayList<PostPackages>();
		for(int i = 0 ; i < listPackages.size() ; i++){
			if(i != position){
				mList.add(mList.get(i));
			}
		}
		listPackages.clear();
		listPackages.addAll(mList);
		this.notifyDataSetChanged();
	}
	private class ViewHolder {
		TextView tvOrder ;
		TextView tvPostDate ;
		TextView tvAddress ;
		TextView tvState ;
		ImageView imgOrder ;
		TextView tvBoxid ;
		TextView tvGetTime ;
		TextView tvTel;
		Button btnGet ;
		
	}
}
