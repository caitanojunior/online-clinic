package com.clinic.mobile;

import java.io.Serializable;

public class Client implements Serializable {
	
	/**
	 * POJO 
	 */
	private static final long serialVersionUID = 1L;
	private String fullname;
	private String phone;
	private String healthPlan;
	private String healthPlanNumber;
	private String schedulingDate;
	private String schedulingHour;


	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHealthPlan() {
		return healthPlan;
	}
	public void setHealthPlan(String healthPlan) {
		this.healthPlan = healthPlan;
	}

	public String getHealthPlanNumber() {
		return healthPlanNumber;
	}
	public void setHealthPlanNumber(String healthPlanNumber) {this.phone = healthPlanNumber;}

	public String getSchedulingDate() {
		return schedulingDate;
	}
	public void setSchedulingDate(String schedulingDate) {
		this.schedulingDate = schedulingDate;
	}

	public String getSchedulinghour() {
		return schedulingHour;
	}
	public void getSchedulinghour(String schedulingHour) {
		this.schedulingHour = schedulingHour;
	}

	@Override
	public String toString() {
		return  fullname;
	}

}
