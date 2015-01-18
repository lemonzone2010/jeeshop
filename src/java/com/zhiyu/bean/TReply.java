package com.zhiyu.bean;

import java.util.Date;

import org.hum.framework.hawaii.orm.annotation.Column;
import org.hum.framework.hawaii.orm.annotation.Table;
import org.hum.framework.hawaii.orm.enumtype.ColumnType;

@Table(tableName="t_reply")
public class TReply {

	private Integer id;
	@Column(colName = "commentID", colType = ColumnType.Int)
	private Integer commentID;
	private String content;
	private Date createdate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommentID() {
		return commentID;
	}

	public void setCommentID(Integer commentID) {
		this.commentID = commentID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
}
