package com.contactsfetcher;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Activity1 extends Activity {

	EditText inputNum, inputName;
	ListView lvContactsList;
	ArrayList<String> contactName = new ArrayList<String>();
	ArrayList<String> contactNumber = new ArrayList<String>();
	ArrayList<String> combo = new ArrayList<String>();
	ArrayAdapter<String> listOfContacts;
	int flag = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_activity1);
		inputNum = (EditText) findViewById(R.id.editText1);
		lvContactsList = (ListView) findViewById(R.id.listView1);
		inputName = (EditText) findViewById(R.id.nameTxt);
		getContactsInArray();
		inputName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				ArrayList<String> nameSearchArray = new ArrayList<String>();
				nameSearchArray.clear();
				for (int i = 0; i < contactName.size(); i++) {
					if (contactName.get(i).toString().contains(s)) {
						nameSearchArray.add(contactName.get(i)+"\n"+contactNumber.get(i));
					}
				}

				listOfContacts = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_list_item_1, nameSearchArray);
				lvContactsList.setAdapter(listOfContacts);

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			
		});
		
		inputNum.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				ArrayList<String> numSearchArray=new ArrayList<String>();
				numSearchArray.clear();
				for (int i = 0; i < contactNumber.size(); i++){
					if(contactNumber.get(i).toString().contains(s)){
						numSearchArray.add(contactName.get(i)+"\n"+contactNumber.get(i));
					}
				}
				listOfContacts = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_list_item_1, numSearchArray);
				lvContactsList.setAdapter(listOfContacts);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

	public void Fetcher(View v) {

		//getContactsInArray();

		switch (v.getId()) {

		case R.id.btnName:
			inputNum.setText("");
			ArrayList<String> nameArray = new ArrayList<String>();
			nameArray.clear();
			int flagName = 0;
			String nameStr = inputName.getText().toString();
			for (int i = 0; i < contactName.size(); i++) {
				if (contactName.get(i).equalsIgnoreCase(nameStr)) {
					nameArray.add(contactName.get(i) + "\n"
							+ contactNumber.get(i));
					flagName = 1;
				}
			}

			if (flagName == 0) {
				Toast.makeText(getApplicationContext(),
						"No Contact exists of this Name", Toast.LENGTH_LONG)
						.show();
				return;
			}

			listOfContacts = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.simple_list_item_1, nameArray);
			lvContactsList.setAdapter(listOfContacts);

			break;
		}

	}

	public void getContactsInArray() {

		combo.clear();
		contactName.clear();
		contactNumber.clear();

		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		ContentResolver cr = getContentResolver();
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
				+ " Collate Localized Asc";
		
		Cursor c = cr.query(uri, null, null, null, sortOrder);
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				contactName
						.add(c.getString(c
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

				contactNumber
						.add(c.getString(c
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

//				combo.add(c.getString(c
//						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
//						+ "\n"
//						+ c.getString(c
//								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

			}
		}
	}
}
