package com.study.jsp.DTO;

import java.sql.Timestamp;

public class MnDTO {
	
	private String id;
	private String pw;
	private String name;
	private String nickname;
	private String email;
	private Timestamp rDate;
	private String address;
	private String ban;
	private int nContent;	// 쓴 개시글 갯수
	private int nReply;		// 쓴 답글 갯수
	private String bId;		// 일정 기간동안 제일 많이 작성한 사람 아이디 표시용
	private String count;	// 일정 기간동안 제일 많이 작성한 사람이 작성한 갯수
	
	public MnDTO() {}	
	
	public MnDTO(String id, String pw, String name, String nickname, String email, Timestamp rDate, String address,
			String ban, int nContent, int nReply) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.rDate = rDate;
		this.address = address;
		this.ban = ban;
		this.nContent = nContent;
		this.nReply = nReply;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getrDate() {
		return rDate;
	}

	public void setrDate(Timestamp rDate) {
		this.rDate = rDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBan() {
		return ban;
	}

	public void setBan(String ban) {
		this.ban = ban;
	}

	public int getnContent() {
		return nContent;
	}

	public void setnContent(int nContent) {
		this.nContent = nContent;
	}

	public int getnReply() {
		return nReply;
	}

	public void setnReply(int nReply) {
		this.nReply = nReply;
	}
	
	
	
	
}
