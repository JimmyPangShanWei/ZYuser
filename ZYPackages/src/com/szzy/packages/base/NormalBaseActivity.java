package com.szzy.packages.base;

import com.szzy.packages.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ��������,���ú��������ý���
 * @author mac
 *
 */
public abstract class NormalBaseActivity extends Activity implements OnClickListener{

	private TextView tvTitle ;  //����
	private ImageView imgBack ; //����
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}
	
	public void setTitle(String title){
		tvTitle = (TextView) findViewById(R.id.textView_title) ;
		//���ñ���
		tvTitle.setText(title) ;
		imgBack = (ImageView) findViewById(R.id.imageButton_back) ;
		imgBack.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageButton_back:
			finish() ;
			break;

		default:
			break;
		}
		
	}
}
