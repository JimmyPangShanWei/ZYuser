package com.szzy.packages.activity;

import com.szzy.packages.R;
import com.szzy.packages.tool.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ��ѯ���棬����ҳ��ת����
 * @author mac
 *
 */
public class SearchPackageActivity extends Activity implements OnClickListener{
	
	private ImageView imgBack ;
	private TextView tvTitle ;//����
	private Button btnSearch ;//��ѯ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_package) ;
		initView();
	}
	
	private void initView(){
		imgBack = (ImageView) findViewById(R.id.imageButton_back) ;
		tvTitle = (TextView) findViewById(R.id.textView_title ) ;
		//���ñ���
		tvTitle.setText(R.string.search) ;
		btnSearch = (Button) findViewById(R.id.button_search_commit) ;
		
		imgBack.setOnClickListener(this) ;
		btnSearch.setOnClickListener(this) ;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageButton_back://����
			finish();
			break;
		case R.id.button_search_commit:
//			Toast.makeText(this, "�ù�����δʵ�֣������ڴ�", 0).show() ;
			//��ʾ������δ��ͨ
			Utils.tipsUnfinish(this);
			break; 

		default:
			break;
		}
		
	}
	
	
}
