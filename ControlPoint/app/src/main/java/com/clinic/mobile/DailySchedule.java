package com.clinic.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DailySchedule extends Activity {

	private TextView txtFullname;
	private TextView txtPhone;
	private TextView txtHealthPlan;
	private TextView txtHealthPlanNumber;
	private TextView txtSchedulingDate;
	private TextView txtSchedulingHour;

	private Client client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily_schedule);

		client = (Client) getIntent().getSerializableExtra("client");
		
		txtFullname = (TextView) findViewById(R.id.txt_fullname);
		txtPhone = (TextView) findViewById(R.id.txt_phone);
		txtHealthPlan = (TextView) findViewById(R.id.txt_health_plan);
		txtHealthPlanNumber = (TextView) findViewById(R.id.txt_health_plan_number);
		txtSchedulingDate = (TextView) findViewById(R.id.txt_scheduling_date);
		txtSchedulingHour = (TextView) findViewById(R.id.txt_scheduling_hour);

		txtFullname.setText(client.getFullname());
		txtPhone.setText(client.getPhone());
		txtHealthPlan.setText(client.getHealthPlan());
		txtHealthPlanNumber.setText(client.getHealthPlanNumber());
		txtSchedulingDate.setText(client.getSchedulingDate());
		txtSchedulingHour.setText(client.getSchedulingHour());
	}

}
