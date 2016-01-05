package com.szzy.packages.activity;

import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.szzy.packages.R;
/**
 * ע�����
 * @author mac
 *
 */
public class RegisterActivity extends Activity {
	/**
	 * �û�ע�������
	 * u_tel		�û��ֻ�
	 * u_loginName 	��¼��
	 * u_name		��ʵ����
	 * u_IDcard 	���֤��
	 * u_Email		����
	 * u_password	��¼����
	 */
	private EditText editTel ;//�ֻ���
	private EditText editLoginName ;//��¼��
	private EditText editRealName ;//��ʵ����
	private EditText editID ;//���֤��
	private EditText editEmail ;//�����
	private EditText editPassword ;//��¼����
	private EditText editEnterPassword ;//ȷ������
	private Button btnRegister ;//ע��
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register) ;
		initView();
	}
	
	private void initView(){
		editTel = (EditText) findViewById(R.id.edit_register_tel);
		editLoginName = (EditText) findViewById(R.id.edit_register_tel);
		editRealName = (EditText) findViewById(R.id.edit_register_tel);
		editID = (EditText) findViewById(R.id.edit_register_tel);
		editEmail = (EditText) findViewById(R.id.edit_register_tel);
		editPassword = (EditText) findViewById(R.id.edit_register_tel);
		editEnterPassword = (EditText) findViewById(R.id.edit_register_tel);
		btnRegister = (Button) findViewById(R.id.button_register_register);
		//�绰���Ӽ���
		editTel.addTextChangedListener(telWatcher);
		//���֤�ż���
		editID.addTextChangedListener(idWatcher) ;
		
	}
	
	//�����ֻ�������λ��
	private TextWatcher telWatcher = new TextWatcher() {
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
				Toast.makeText(RegisterActivity.this, "���Ѿ�������11λ�绰������", 0).show();
				s.delete(editStart - 1, editEnd);
				int tempSelection = editStart;
				editTel.setText(s);
				editTel.setSelection(tempSelection);
			}
			
		}
	};
	
	//�������֤��
	private TextWatcher idWatcher = new TextWatcher() {
		private CharSequence temp;//����ǰ���ı�  
	       private int editStart;//��꿪ʼλ��  
	       private int editEnd;//������λ��  
	       private final int charMaxNum = 18; //����ı���
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
			editStart = editID.getSelectionStart();
			editEnd = editID.getSelectionEnd() ;
			if (temp.length() > charMaxNum) { 
				Toast.makeText(RegisterActivity.this, "���Ѿ�������18λ���֤����", 0).show();
				s.delete(editStart - 1, editEnd);
				int tempSelection = editStart;
				editID.setText(s);
				editID.setSelection(tempSelection);
			}
			
		}
	};
	
	/**
	 * ����ƥ�����֤��
	 */
	private boolean checkID(String id){
		//�ж����֤��,ǰ17λһ��Ϊ���֣����һλ������x����X
		String rex = "[0-9]+[x?X?]";
		Pattern p = Pattern.compile(rex);
		return p.matcher(id).matches() ;
	}
	
	
	
}
