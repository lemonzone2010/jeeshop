package com.zhiyu.bean;

import java.util.Date;

import org.hum.framework.hawaii.spring.annotation.Column;
import org.hum.framework.hawaii.spring.annotation.Table;
import org.hum.hawaii.orm.enumtype.ColumnTypeEnum;

@Table(tableName="t_reply")
public class TReply {

	private Integer id;
	@Column(colName = "commentID", colType = ColumnTypeEnum.Int)
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TReply [id=").append(id).append(", commentID=").append(commentID).append(", content=").append(content).append(", createdate=").append(createdate).append("]");
		return builder.toString();
	}
}
