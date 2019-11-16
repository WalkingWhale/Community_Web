package com.study.jsp.DTO;

import java.sql.Timestamp;

public class PeriodAsearchDto {
		
	private String id;
	private String name;
	private String nickname;
	private String ban;
	private int NumArticle;

	public PeriodAsearchDto() {
		// TODO Auto-generated constructor stub
	}
	
	public PeriodAsearchDto(String id, String name, String nickname, String ban, int numArticle) {
		super();
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.ban = ban;
		this.NumArticle = numArticle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public int getNumArticle() {
		return NumArticle;
	}

	public void setNumArticle(int numArticle) {
		this.NumArticle = numArticle;
	}
}
