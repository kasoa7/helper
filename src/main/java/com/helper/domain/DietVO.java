package com.helper.domain;

import java.sql.Date;

public class DietVO {
	private String emailAddress;
	private String Bmenu;
	private String Lmenu;
	private String Dmenu;
	private int Bkcal;
	private int Lkcal;
	private int Dkcal;
	private String Bmealtime;
	private String Lmealtime;
	private String Dmealtime;
	private Date MealDays;
	private int Totalkcal;

	public int getTotalkcal() {
		return Totalkcal;
	}

	public void setTotalkcal(int totalkcal) {
		Totalkcal = totalkcal;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getBmenu() {
		return Bmenu;
	}

	public void setBmenu(String bmenu) {
		Bmenu = bmenu;
	}

	public String getLmenu() {
		return Lmenu;
	}

	public void setLmenu(String lmenu) {
		Lmenu = lmenu;
	}

	public String getDmenu() {
		return Dmenu;
	}

	public void setDmenu(String dmenu) {
		Dmenu = dmenu;
	}

	public int getBkcal() {
		return Bkcal;
	}

	public void setBkcal(int bkcal) {
		Bkcal = bkcal;
	}

	public int getLkcal() {
		return Lkcal;
	}

	public void setLkcal(int lkcal) {
		Lkcal = lkcal;
	}

	public int getDkcal() {
		return Dkcal;
	}

	public void setDkcal(int dkcal) {
		Dkcal = dkcal;
	}

	public String getBmealtime() {
		return Bmealtime;
	}

	public void setBmealtime(String bmealtime) {
		Bmealtime = bmealtime;
	}

	public String getLmealtime() {
		return Lmealtime;
	}

	public void setLmealtime(String lmealtime) {
		Lmealtime = lmealtime;
	}

	public String getDmealtime() {
		return Dmealtime;
	}

	public void setDmealtime(String dmealtime) {
		Dmealtime = dmealtime;
	}

	public Date getMealDays() {
		return MealDays;
	}

	public void setMealDays(Date mealDays) {
		MealDays = mealDays;
	}

	@Override
	public String toString() {
		return "DietVO [emailAddress=" + emailAddress + ", Bmenu=" + Bmenu + ", Lmenu=" + Lmenu + ", Dmenu=" + Dmenu
				+ ", Bkcal=" + Bkcal + ", Lkcal=" + Lkcal + ", Dkcal=" + Dkcal + ", Bmealtime=" + Bmealtime
				+ ", Lmealtime=" + Lmealtime + ", Dmealtime=" + Dmealtime + ", MealDays=" + MealDays + ", Totalkcal="
				+ Totalkcal + "]";
	}

}