package com.douzone.mysite.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class SiteVo {
	@NotEmpty
	private String title;
	@NotEmpty
	private String welcome;
	private String profile;
	@NotEmpty
	private String description;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWelcome() {
		return welcome;
	}
	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "SiteVo [title=" + title + ", welcome=" + welcome + ", profile=" + profile + ", description="
				+ description + "]";
	}
	
	
}
