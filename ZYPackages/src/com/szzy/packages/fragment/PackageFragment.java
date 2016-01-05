package com.szzy.packages.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.zxing.oned.rss.FinderPattern;
import com.szzy.packages.R;
import com.szzy.packages.activity.GetPackageActivity;
import com.szzy.packages.activity.PostInfoActivity;
import com.szzy.packages.activity.UserPostActivity;
import com.szzy.packages.tool.Utils;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * �Ű�����,����������ת����
 * @author mac
 *
 */
public class PackageFragment extends Fragment {

	private View rootView ;
	private Context context ; 
	
	private TextView tvTitle ;//����
	private ListView listViewMenu ;//�Ű��˵�
	private List<Map<String, Object>> listData ; //�˵����������
	//�˵�ͼ��
	private int[] imgMenus = new int[]{R.drawable.home_ic_postor_post,
			R.drawable.home_ic_get_package,
			R.drawable.home_ic_user_post};
	//������
	private String[] strMenus = new String[]{ "ȡ��ǩ��", "ҵ���Ĵ�", "�ص���ӡ"} ;
	//������˵��
	private String[] strSubMenus = new String[]{ "��Ҫ�ռ�", "���ּĴ棬���ٵȺ�", "ˮ����嵥��ʱ��ӡ�����Լ��ֽ"};
	private ListViewPkAdapter adapter ;//������
	@Override
	public void onCreate(Bundle savedInstanceState) {
		context = getActivity() ;
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = LayoutInflater.from(context).inflate(R.layout.fragment_package, null) ;
		initView() ;
		return rootView;
	}
	
	private void initView(){
		tvTitle = (TextView) rootView.findViewById(R.id.textView_m_title) ;
		tvTitle.setText("�Ű���");
		listData = new ArrayList<Map<String,Object>>();
		//���listVIEW����
		for(int i = 0 ; i < strMenus.length ; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("imgMenu", imgMenus[i]) ;
			map.put("textSub", strSubMenus[i]) ;
			map.put("textMenu", strMenus[i]) ;
			listData.add(map) ;
		}
		adapter = new ListViewPkAdapter() ;
		listViewMenu = (ListView) rootView.findViewById(R.id.listView_frag_package) ;
		listViewMenu.setAdapter(adapter) ;
		//����listView������Ϣ
		listViewMenu.setOnItemClickListener(new ItemOnClick()) ;
	}
	
	//����item
	private class ItemOnClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,
				long id) {
			Intent intent = new Intent() ;
			switch (position) {
//			case 0://�Ű�Ͷ��
//				intent.setClass(context, PostInfoActivity.class) ;
//				startActivity(intent) ;
//				break;
			case 0://ȡ��ǩ��
				Intent iToGetPackage = new Intent(context, GetPackageActivity.class);
				startActivity(iToGetPackage) ;
				break;
			case 1://ҵ���Ĵ�
				intent.setClass(context, UserPostActivity.class) ;
				startActivity(intent) ;
				break;
			case 2://�ص���ӡ
				//��ʾ������δ��ͨ
				Utils.tipsUnfinish(context); 
				break;

			default:
				break;
			}
			
		}
		
	}
	
	//listview������
	private class ListViewPkAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listData.get(position);
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
				//ע�벼��
				convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_package_menu, null);
//				hold.img = (ImageView) convertView.findViewById(R.id.imageView_listview_item_package_menu) ;
				hold.tvMenu = (TextView) convertView.findViewById(R.id.textView_listview_item_package_menu) ;
				hold.tvSub = (TextView) convertView.findViewById(R.id.textView_listview_item_package_menu_sub) ;
				convertView.setTag(hold) ;
			}else{
				hold = (ViewHold) convertView.getTag() ;
			}
			//�������
//			hold.img.setImageResource((Integer)listData.get(position).get("imgMenu")) ;
			hold.tvMenu.setText((String) listData.get(position).get("textMenu")) ;
			hold.tvSub.setText((String) listData.get(position).get("textSub")) ;
			return convertView;
		}
		
		private class ViewHold{
//			ImageView img ;
			TextView tvSub ; //�˵�������
			TextView tvMenu ;//�˵�������
		}
		
	}
}
