package com.szzy.packages.activity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.szzy.packages.R;
import com.szzy.packages.http.HttpHelper;
import com.szzy.packages.http.OpenBoxCall;
/**
 * �������
 * @author mac
 *
 */
public class ShopActicity extends Activity implements OnClickListener{
	private ImageView imgBack ;
	private EditText editInput ;
	private ImageView imgSys ;
	private GridView gridViewShop ; //�����б�
	private HttpHelper httphelper ;//http����
	private Handler mHandler = new Handler();
	
	private MApplication mApp ;
	//ͼƬ��Դ
	private int[] imgs = new int[]{R.drawable.shop_z01, R.drawable.shop_z02, R.drawable.shop_z03, 
			R.drawable.shop_z05, R.drawable.shop_z04,  R.drawable.shop_z06, 
			R.drawable.shop_z07, R.drawable.shop_z08, R.drawable.shop_z09, 
			R.drawable.shop_z10, R.drawable.shop_z11, R.drawable.shop_z12};
	//��Ӧ�����ź�
	private int[] boxIDs = new int[] {19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 47, 48};
	//��������
	private String[] boxNames = new String[]{"A01", "A02", "A03", "A04", "A05", "A06", "A07", "A08", "A09", "A10", "B01", "B02"};
	private GridViewAdapter adapter ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop) ;
		httphelper = new HttpHelper();
		mApp = (MApplication) getApplication();
		initView() ;
	}
	
	private void initView(){
		imgBack = (ImageView) findViewById(R.id.imageButton_back) ;
		editInput = (EditText) findViewById(R.id.editText_shop) ;
		imgSys = (ImageView) findViewById(R.id.imageView_shop_sys) ;
		gridViewShop = (GridView) findViewById(R.id.gridview_shop) ;
		adapter = new GridViewAdapter() ;
		gridViewShop.setAdapter(adapter) ;
		
		imgBack.setOnClickListener(this) ;
		//��������¼�
		gridViewShop.setOnItemClickListener(new GridViewItemClick());
	}
	
	 int mPosition  ;
	 float mPrice ;
	 String mGoods ;
	//���gridView Item�¼�
	private class GridViewItemClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			try{
				int lockid = Integer.valueOf(mApp.getLockId());
//				if()
			}catch(Exception e){
				Toast.makeText(mApp, "�����벻��ȷ����ɨ����ȷ���", 0).show() ;
				return ;
			}
			
			mPosition = position ;
			switch (position) {
			case 0:
				mGoods = "���ĥ����֥����ҷ�";
				mPrice = 167.83f ;
				break;
			case 1:
				mGoods = "���׿�����";
				mPrice = 40.00f ;
				break;
			case 2:
				mGoods = "����������";
				mPrice = 19.00f ;
				break;
			case 3:
				mGoods = "��ʦ������ѩ��";
				mPrice = 4.00f ;
				break;
			case 4:
				mGoods = "��Ҥ���Ȳ���ֱ����պ�";
				mPrice = 19.80f ;
				break;
			case 5:
				mGoods = "���鶫����";
				mPrice = 62.00f ;
				break;
			case 6:
				mGoods = "�ٲ�һ�� 10Ԫ����";
				mPrice = 10.00f ;
				break;
			case 7:
				mGoods = "��ʦ������ˮ 350ml*24";
				mPrice = 20.9f ;
				break;
			case 8:
				mGoods = "��ʦ������ţ���� 105g*12";
				mPrice = 39.80f ;
				break;
			case 9:
				mGoods = "���ϼ� ����310ml*12";
				mPrice = 34.90f ;
				break;
			case 10:
				mGoods = "��ʦ�����ʴ�ͷ����� 98g";
				mPrice = 5.5f ;
				break;
			case 11:
				mGoods = "��ţά���ع������� 250ml*6";
				mPrice = 42.5f ;
				break;

			default:
				break;
			}
			//�����Ի����Ƿ�Ҫ֧��
//			createDialog(mGoods, mPrice, mPosition);
		}
		
	}
	//�Ի�����UI�ؼ�
	private TextView tvGoods ;
	private TextView tvPrice ;
	private TextView tvBoxName ;
	
	private void createDialog(final String goods, final float price ,final int position){
		Builder builder = new Builder(this);
		LayoutInflater inflater = LayoutInflater.from(this);
		View view  = inflater.inflate(R.layout.dialog_shop, null);
		builder.setView(view);
		tvGoods = (TextView) view.findViewById(R.id.textView_goods) ;
		tvPrice = (TextView) view.findViewById(R.id.textView_goods_price) ;
		tvBoxName = (TextView) view.findViewById(R.id.textView_goods_box) ;
		tvGoods.setText(goods);
		tvPrice.setText(Float.valueOf(price).toString() + "Ԫ");
		tvBoxName.setText(boxNames[position]) ;
		builder.setPositiveButton("֧��ȡ��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int lockId = Integer.valueOf(mApp.getLockId());
				httphelper.openBox(mApp.getUser(), mApp.getPassword(), lockId	, Integer.valueOf(boxIDs[position]).toString(), new OpenBoxCall() {
					
					@Override
					public void call(String errorCode) {
						Log.e("", errorCode) ;
						if("0".equals(errorCode)){
							mHandler.post(new Runnable() {
								
								@Override
								public void run() {
									Toast.makeText(mApp, "�����Ѵ򿪣���ȡ����Ʒ", 0).show() ;
									
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
	
	/**
	 * gridview������
	 * @author mac
	 *
	 */
	private class GridViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgs.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return imgs[position];
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
				convertView = LayoutInflater.from(ShopActicity.this).inflate(R.layout.gridview_item_post_company, null);
				hold.img = (ImageView) convertView.findViewById(R.id.imageView_gridview_item_post_company) ;
				convertView.setTag(hold) ;
			}else{
				hold = (ViewHold) convertView.getTag() ;
			}
			//���ͼƬ
			hold.img.setImageResource(imgs[position]) ;
			return convertView;
		}
		
		private class ViewHold{
			ImageView img ;
		}
		
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
