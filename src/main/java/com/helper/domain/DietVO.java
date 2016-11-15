package com.helper.domain;

public class DietVO {
	private String emailAddress;
	private String menu;
	private int calorie;
	private int eatTime;
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public int getCalorie() {
		return calorie;
	}
	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}
	public int getEatTime() {
		return eatTime;
	}
	public void setEatTime(int eatTime) {
		this.eatTime = eatTime;
	}
	@Override
	public String toString() {
		return "DietVO [emailAddress=" + emailAddress + ", menu=" + menu
				+ ", calorie=" + calorie + ", eatTime=" + eatTime + "]";
	}
	
	
}
