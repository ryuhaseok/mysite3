package com.javaex.vo;

public class GuestVo {

	private int no;
	private String name;
	private String pw;
	private String content;
	private String regDate;
	
	
	public GuestVo() {
	}
	
	public GuestVo(int no, String pw) {
		this.no = no;
		this.pw = pw;
	}
	
	public GuestVo(String name, String pw, String content) {
		this.name = name;
		this.pw = pw;
		this.content = content;
	}
	
	public GuestVo(int no, String name, String regDate, String content) {
		this.no = no;
		this.name = name;
		this.regDate = regDate;
		this.content = content;
	}
	
	public GuestVo(int no, String name, String pw, String content, String regDate) {
		this.no = no;
		this.name = name;
		this.pw = pw;
		this.content = content;
		this.regDate = regDate;
	}
	
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	
	@Override
	public String toString() {
		return "GuestVo [no=" + no + ", name=" + name + ", pw=" + pw + ", content=" + content + ", regDate=" + regDate
				+ "]";
	}
	
	
	
}
