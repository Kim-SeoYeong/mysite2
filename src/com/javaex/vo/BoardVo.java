package com.javaex.vo;

public class BoardVo {
	
	private int no;		//식별번호
	private String title;	//글제목
	private String content;		//글내용
	private String name;	//글쓴이
	private int hit;	//조회수
	private String regDate;	//등록일
	private int user_no;	//회원식별번호
	
	
	public BoardVo() {}
	
	public BoardVo(String title, String content, String name, int hit, String regDate) {
		super();
		this.title = title;
		this.content = content;
		this.name = name;
		this.hit = hit;
		this.regDate = regDate;
	}

	public BoardVo(int no, String title, String content, String name, int hit, String regDate) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.name = name;
		this.hit = hit;
		this.regDate = regDate;
	}

	public BoardVo(int no, String title, String content, String name, int hit, String regDate, int user_no) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.name = name;
		this.hit = hit;
		this.regDate = regDate;
		this.user_no = user_no;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", name=" + name + ", hit=" + hit
				+ ", regDate=" + regDate + ", user_no=" + user_no + "]";
	}
	
}
