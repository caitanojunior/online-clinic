package com.clinic.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class InformationsActivity extends Activity {

	private TextView txtFullname;
	private TextView txtPhone;
	private Client client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informacoes);

		client = (Client) getIntent().getSerializableExtra("client");
		
		txtFullname = (TextView) findViewById(R.id.txtFullname);
		txtPhone = (TextView) findViewById(R.id.txtPhone);
		
		txtFullname.setText(client.getFullname());
		txtPhone.setText(client.getPhone());

	}

}
