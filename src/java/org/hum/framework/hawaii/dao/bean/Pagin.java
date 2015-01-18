package org.hum.framework.hawaii.dao.bean;

import java.util.ArrayList;
import java.util.List;

public class Pagin<T> {

	private int pageSize;
	private int pageNo;
	private int totalItem;
	private int pageCount;
	private List<T> dataList;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public int getPageCount() {
		if (totalItem == 0 || pageSize == 0) {
			return 0;
		}
		if (totalItem % pageSize == 0) {
			pageCount = totalItem / pageSize;
		} else {
			pageCount = totalItem / pageSize + 1;
		}
		return pageCount;
	}

	public static <T> Pagin<T> emptyPagin(Integer pageNo, Integer pageSize) {
		Pagin<T> pagin = new Pagin<T>();
		pagin.setDataList(new ArrayList<T>());
		pagin.setTotalItem(0);
		pagin.setPageSize(pageSize);
		pagin.setPageNo(pageNo);
		return pagin;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pagin [dataList=").append(dataList).append(", pageCount=").append(pageCount).append(", pageNo=").append(pageNo).append(", pageSize=").append(pageSize).append(", totalItem=").append(totalItem).append("]");
		return builder.toString();
	}
}
