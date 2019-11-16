package com.study.jsp.DTO;

import java.sql.Timestamp;

public class PeriodCsearchDto {
		
	private String id;
	private String name;
	private String nickname;
	private String ban;
	private int NumComment;

	public PeriodCsearchDto() {
		// TODO Auto-generated constructor stub
	}
	
	public PeriodCsearchDto(String id, String name, String nickname, String ban, int NumComment) {
		super();
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.ban = ban;
		this.NumComment = NumComment;
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

	public int getNumComment() {
		return this.NumComment;
	}

	public void setNumComment(int NumComment) {
		this.NumComment = NumComment;
	}
}
