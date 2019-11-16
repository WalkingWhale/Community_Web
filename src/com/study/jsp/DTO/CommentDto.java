package com.study.jsp.DTO;

import java.sql.Timestamp;

public class CommentDto {
	
	private int comment_num;		// 댓글 글번호
	private int comment_board;		// 게시글 번호
	private String comment_id;		// 댓글 작성자
	private Timestamp comment_date;	// 댓글 작성일
	private int comment_parent;		// 부모글
	private String comment_content;	// 댓글 내용
	private int comment_level;		// 댓글- 답변글 깊이
	private String parent_title;	// 부모글 제목
	private boolean report = false;	// 신고 여부
	
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public int getComment_board() {
		return comment_board;
	}
	public void setComment_board(int comment_board) {
		this.comment_board = comment_board;
	}
	public String getComment_id() {
		return comment_id;
	}
	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}
	public Timestamp getComment_date() {
		return comment_date;
	}
	public void setComment_date(Timestamp comment_date) {
		this.comment_date = comment_date;
	}
	public int getComment_parent() {
		return comment_parent;
	}
	public void setComment_parent(int comment_parent) {
		this.comment_parent = comment_parent;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public int getComment_level() {
		return comment_level;
	}
	public void setComment_level(int comment_level) {
		this.comment_level = comment_level;
	}
	public String getParent_title() {
		return parent_title;
	}
	public void setParent_title(String parent_title) {
		this.parent_title = parent_title;
	}
	public boolean isReport() {
		return report;
	}
	public void setReport(boolean report) {
		this.report = report;
	}
	
	
}
