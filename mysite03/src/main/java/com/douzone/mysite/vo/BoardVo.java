package com.douzone.mysite.vo;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

public class BoardVo {
	private Long no;
	
	@NotEmpty
	@Length(min=2,max=10)
	private String title;
	@NotEmpty
	@Length(min=2)
	private String content;
	
	private String regDate;
	private int hit;
	private int groupNo;
	private int orderNo;
	private int depth;
	private Long userNo;
	private String userName;
	private Long parentNo;
	
	public Long getParentNo() {
		return parentNo;
	}

	public void setParentNo(Long parentNo) {
		this.parentNo = parentNo;
	}



	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
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

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", regDate=" + regDate + ", hit="
				+ hit + ", groupNo=" + groupNo + ", orderNo=" + orderNo + ", depth=" + depth + ", userNo=" + userNo
				+ ", userName=" + userName + ", parentNo=" + parentNo + "]";
	}

}
