package com.study.jsp.DTO;

import java.sql.Timestamp;

public class MemberDTO {
	
	private String id;
	private String pw;
	private String name;
	private String nickname;
	private String email;
	private Timestamp rDate;
	private String address;
	private String ban;
	private String sns;
	
	public MemberDTO() {}
	
	/*public MemberDTO(String id, String pw, String name, String phone, String gender) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.phone = phone;
		this.gender = gender;
	}*/
	
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

	public String getSns() {
		return sns;
	}

	public void setSns(String sns) {
		this.sns = sns;
	}
	
}
