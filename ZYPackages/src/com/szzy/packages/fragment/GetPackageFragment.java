package com.szzy.packages.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.szzy.packages.R;
import com.szzy.packages.activity.MApplication;
import com.szzy.packages.adapter.NotGetPackageListViewAdapter;
import com.szzy.packages.entity.GetPackages;
import com.szzy.packages.http.HttpCallBack;
import com.szzy.packages.http.HttpHelper;
import com.szzy.packages.tool.TipsHttpError;

/**
 * ������fragment
 * 
 * @author mac
 * 
 */
public class GetPackageFragment extends Fragment implements OnClickListener {

	private Context context;

	private View rootView;
	private TextView tvTitleNotRecv;// δǩ��
	private TextView tvTitleRecv;// ��ǩ��
	private TextView tvTitleNotRecvBar;// δǩ��bar
	private TextView tvTitleRecvBar;// ��ǩ��bar

	// private MyListView listViewData ;//�Զ���listView
	private PullToRefreshLayout ptrl;// ����ˢ�¿ؼ�
	private ListView listViewData;
	private NotGetPackageListViewAdapter adapter;// listview������
	private List<GetPackages> listNotGet = new ArrayList<GetPackages>();; // δǩ������
	private List<GetPackages> listGet = new ArrayList<GetPackages>();// ��ǩ������
	// private List<GetPackages> listData = new ArrayList<GetPackages>();
	private HttpHelper httpHelper;
	private MApplication mApp;
	private Handler mHandler = new Handler(); // ��Ϣ�����������ڸ���http������ʾ
	private int mode = 1;// 1δǩ��ģʽ, 2��ǩ��ģʽ

	private boolean first = true; // �Ƿ��״ν������

	private MyReceiver mReceiver; // �㲥�����ߣ����ڸ���ȡ�������ݵ�ˢ��
	private GestureDetector gesture ;//����
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context = getActivity();
		httpHelper = new HttpHelper();
		mApp = (MApplication) context.getApplicationContext();
		mReceiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("update");
		context.registerReceiver(mReceiver, filter);
	}

	@Override
	public void onDestroy() {
		context.unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (View) inflater.inflate(R.layout.fragment_getpackage,
				container, false);
		initView();
		return rootView;
	}

	//��ʼ��UI
	private void initView() {
		tvTitleNotRecv = (TextView) rootView
				.findViewById(R.id.textView_get_not_receive);
		tvTitleRecv = (TextView) rootView
				.findViewById(R.id.textView_get_received);
		tvTitleNotRecvBar = (TextView) rootView
				.findViewById(R.id.textView_get_not_receive_bar);
		tvTitleRecvBar = (TextView) rootView
				.findViewById(R.id.textView_get_received_bar);
		// �жϱ�ǩ���ĸ�λ��
		if (mode == 1) {
			adapter = new NotGetPackageListViewAdapter(mApp, context,
					listNotGet);
		} else {
			adapter = new NotGetPackageListViewAdapter(mApp, context, listGet);
		}

		ptrl = (PullToRefreshLayout) rootView
				.findViewById(R.id.listView_frag_get_data);
		listViewData = (ListView) ptrl.getPullableView();
		// ��������
		ptrl.setOnPullListener(new PullToRefreshLayout.OnPullListener() {

			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				httpHelper.queryPackage(mApp.getUser(), mApp.getPassword(),
						mode, new HttpCallBack() {

							@Override
							public void call(Object obj, final String err) {
								if (!err.equals("0")) {
									mHandler.post(new Runnable() {
										public void run() {
											TipsHttpError.toastError(mApp, err);
											ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
										}
									});
									return;
								}
								if (mode == 1) {
									listNotGet = (List<GetPackages>) obj;
									// listData = listNotGet ;
								} else {
									// listGet = obj ;
									List<GetPackages> listTemp = (List<GetPackages>) obj;
									listGet = new ArrayList<GetPackages>();
									for (GetPackages get : listTemp) {
										if (!get.getState().equals("0")) {
											listGet.add(get);
										}
									}
									// listGet = listData ;
								}

								new AsyncTask<Void, Void, Void>() {
									protected Void doInBackground(
											Void... params) {

										// data.add("ˢ�º���ӵ�����");
										Log.e("onRefresh", "onRefresh");
										return null;
									}

									@Override
									protected void onPostExecute(Void result) {
										Log.e("onPostExecute", "onPostExecute");
										Toast.makeText(context, "���ݸ��³ɹ�", 0)
												.show();
										if (mode == 1) {
											adapter = new NotGetPackageListViewAdapter(
													mApp, context, listNotGet);
										} else {
											adapter = new NotGetPackageListViewAdapter(
													mApp, context, listGet);
										}
										// adapter = new
										// NotGetPackageListViewAdapter(mApp,context,
										// listData);
										listViewData.setAdapter(adapter);
										ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
									}

								}.execute();

							}
						});

			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				new Handler().post(new Runnable() {

					@Override
					public void run() {
						ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);

					}
				});

			}
		});

		/**
		 * ��һ�ν������ʱ���ȷ��ͻ�ȡ���е����ݵ�http�������������������ֶ�����ˢ��
		 */
		if (first) {
			first = false;
			httpHelper.queryPackage(mApp.getUser(), mApp.getPassword(), 1,
					new HttpCallBack() {

						@Override
						public void call(Object obj, final String err) {
							if (!err.equals("0")) {
								mHandler.post(new Runnable() {
									public void run() {
										ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
										TipsHttpError.toastError(mApp, err);
									}
								});
								return;
							}
							listNotGet = (List<GetPackages>) obj;
							// listData = listNotget ;
							mHandler.post(new Runnable() {

								@Override
								public void run() {
									adapter = new NotGetPackageListViewAdapter(
											mApp, context, listNotGet);
									listViewData.setAdapter(adapter);
								}
							});

						}
					});
			// ��ȡ����
			httpHelper.queryPackage(mApp.getUser(), mApp.getPassword(), 2,
					new HttpCallBack() {

						@Override
						public void call(Object obj, final String err) {
							if (!err.equals("0")) {
								mHandler.post(new Runnable() {
									public void run() {
										TipsHttpError.toastError(mApp, err);
										ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
									}
								});
								return;
							}
							// listGet = obj ;
							listGet = new ArrayList<GetPackages>();
							List<GetPackages> listTemp = (List<GetPackages>) obj;
							for (GetPackages get : listTemp) {
								if (!get.getState().equals("0")) {
									listGet.add(get);
								}
							}
							// listGet = listData ;

							new AsyncTask<Void, Void, Void>() {
								protected Void doInBackground(Void... params) {

									// data.add("ˢ�º���ӵ�����");
									Log.e("onRefresh", "onRefresh");
									return null;
								}

								@Override
								protected void onPostExecute(Void result) {
									Log.e("onPostExecute", "onPostExecute");
									Toast.makeText(context, "���ݸ��³ɹ�", 0).show();
									// adapter = new
									// NotGetPackageListViewAdapter(mApp,context,
									// listData);
									// listViewData.setAdapter(adapter);
									ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
								}

							}.execute();

						}
					});
		}

		if (mode == 1) {
			tvTitleNotRecvBar.setBackgroundColor(context.getResources()
					.getColor(R.color.title_font_bg));
			tvTitleRecvBar.setBackgroundColor(context.getResources().getColor(
					R.color.title_bg));
		} else {
			tvTitleNotRecvBar.setBackgroundColor(context.getResources()
					.getColor(R.color.title_bg));
			tvTitleRecvBar.setBackgroundColor(context.getResources().getColor(
					R.color.title_font_bg));
		}

		listViewData.setAdapter(adapter);
		tvTitleNotRecv.setOnClickListener(this);
		tvTitleRecv.setOnClickListener(this);
		
//		gesture = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
//			@Override
//			public boolean onFling(MotionEvent e1, MotionEvent e2,
//					float velocityX, float velocityY) {
//				Log.e("GestureDetector", "GestureDetector right");
//				return super.onFling(e1, e2, velocityX, velocityY);
//			}
//		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textView_get_not_receive:
			tvTitleNotRecvBar.setBackgroundColor(context.getResources()
					.getColor(R.color.title_font_bg));
			tvTitleRecvBar.setBackgroundColor(context.getResources().getColor(
					R.color.title_bg));
			mode = 1;
			// listData = listNotGet ;
			adapter = new NotGetPackageListViewAdapter(mApp, context,
					listNotGet);
			listViewData.setAdapter(adapter);
			break;
		case R.id.textView_get_received:
			tvTitleNotRecvBar.setBackgroundColor(context.getResources()
					.getColor(R.color.title_bg));
			tvTitleRecvBar.setBackgroundColor(context.getResources().getColor(
					R.color.title_font_bg));
			mode = 2;
			// listData = listGet ;
			adapter = new NotGetPackageListViewAdapter(mApp, context, listGet);
			listViewData.setAdapter(adapter);
			break;

		default:
			break;
		}

	}

	// �㲥������,����ˢ����Ϣ
	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e("onReceive", "onReceive");
			// ˢ��Ͷ����Ϣ
			httpHelper.queryPackage(mApp.getUser(), mApp.getPassword(), 1,
					new HttpCallBack() {

						@Override
						public void call(Object obj, final String err) {
							if (!err.equals("0")) {
								mHandler.post(new Runnable() {
									public void run() {
										TipsHttpError.toastError(mApp, err);
										
									}
								});
								return;
							}
							listNotGet = (List<GetPackages>) obj;
							// listData = listNotget ;
							mHandler.post(new Runnable() {

								@Override
								public void run() {
									if (listNotGet == null
											|| listNotGet.isEmpty()) {
										listNotGet = new ArrayList<GetPackages>();
									}
									adapter = new NotGetPackageListViewAdapter(
											mApp, getActivity(), listNotGet);
									listViewData.setAdapter(adapter);
								}
							});

						}
					});
		}

	}
}
