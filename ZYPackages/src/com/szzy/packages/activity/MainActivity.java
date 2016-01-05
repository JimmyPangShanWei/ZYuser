package com.szzy.packages.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.szzy.packages.R;
import com.szzy.packages.base.MBaseActivity;
import com.szzy.packages.fragment.GetPackageFragment;
import com.szzy.packages.fragment.HomeFragment;
import com.szzy.packages.fragment.PackageFragment;
import com.szzy.packages.fragment.PostFragment;
import com.szzy.packages.fragment.ShopFragment;
import com.szzy.packages.fragment.UserFragment;
/**
 * ������
 * @author mac
 *
 */
public class MainActivity extends MBaseActivity{

	private RelativeLayout layout ;
	private Button btnHome ;
	private Button btnGet ;
	private Button btnPost ;
	private Button btnUser ;
	
	private FragmentManager fragManager ;
	private FragmentTransaction fragTran;
	
	private Fragment  getPackageFrag ; //ȡ������
	private Fragment homeFrag ;//������
	private Fragment postFrag ;//Ͷ�ݽ���
	private Fragment userFrag ;//�û�����
	private Fragment shopFrag ;//�������
	
	private Fragment packageFrag ;//�Ű�����
	
	private MyReceiver mReceiver ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
		fragManager = getFragmentManager();
		getPackageFrag = new GetPackageFragment();
		homeFrag = new HomeFragment();
		postFrag = new PostFragment() ;
		userFrag = new UserFragment() ;
		shopFrag = new ShopFragment() ;
		packageFrag = new PackageFragment() ;
		repleaceFragment(homeFrag);//Ĭ��Ϊ������
		mReceiver = new MyReceiver() ;
		IntentFilter filter = new IntentFilter();
		filter.addAction("toshop");
		registerReceiver(mReceiver, filter);
		
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	
	@Override
	public void initView() {
		btnHome = (Button) findViewById(R.id.tab_home);
		btnGet = (Button) findViewById(R.id.tab_getpackage);
		btnPost = (Button) findViewById(R.id.tab_post);
		btnUser = (Button) findViewById(R.id.tab_user);
		
		btnHome.setOnClickListener(this);
		btnGet.setOnClickListener(this);
		btnPost.setOnClickListener(this);
		btnUser.setOnClickListener(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		return super.twoPressExit(keyCode, event);//�����η��ؼ����˳�����
	}
	
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tab_home:
			repleaceFragment(homeFrag);
			btnHome.setBackgroundResource(R.drawable.tab_home_selected);
			btnGet.setBackgroundResource(R.drawable.tab_post_normal);
			btnPost.setBackgroundResource(R.drawable.tab_shop_normal);
			btnUser.setBackgroundResource(R.drawable.tab_user_normal);
			break;
		case R.id.tab_getpackage:
//			repleaceFragment(getPackageFrag);//��ʱע�͵���δ���
			repleaceFragment(packageFrag);
			btnHome.setBackgroundResource(R.drawable.tab_home_normal);
			btnGet.setBackgroundResource(R.drawable.tab_post_selected);
			btnPost.setBackgroundResource(R.drawable.tab_shop_normal);
			btnUser.setBackgroundResource(R.drawable.tab_user_normal);
			break;
		case R.id.tab_post:
//			repleaceFragment(postFrag);//��Ͷ�������滻�ɹ������
			repleaceFragment(shopFrag) ;
			btnHome.setBackgroundResource(R.drawable.tab_home_normal);
			btnGet.setBackgroundResource(R.drawable.tab_post_normal);
			btnPost.setBackgroundResource(R.drawable.tab_shop_selected);
			btnUser.setBackgroundResource(R.drawable.tab_user_normal);
			break;
		case R.id.tab_user:
			repleaceFragment(userFrag);
			btnHome.setBackgroundResource(R.drawable.tab_home_normal);
			btnGet.setBackgroundResource(R.drawable.tab_post_normal);
			btnPost.setBackgroundResource(R.drawable.tab_shop_normal);
			btnUser.setBackgroundResource(R.drawable.tab_user_selected);
			break;

		default:
			break;
		}
	}
	
	//�л�Fragment
	private void repleaceFragment(Fragment fragment){
		fragTran = fragManager.beginTransaction();
		fragTran.replace(R.id.layout_fragment, fragment);
		fragTran.commit();
	}
	
	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
//			Log.e("MyReceiver  ---- ", "BroadcastReceiver");
			//��ת���̵�
			repleaceFragment(shopFrag) ;
			btnHome.setBackgroundResource(R.drawable.tab_home_normal);
			btnGet.setBackgroundResource(R.drawable.tab_post_normal);
			btnPost.setBackgroundResource(R.drawable.tab_shop_selected);
			btnUser.setBackgroundResource(R.drawable.tab_user_normal);
			
		}
		
	}
}
