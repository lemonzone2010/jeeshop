package com.zhiyu.service.product;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hum.hawaii.dao.bean.QueryWrapper;
import org.hum.hawaii.dao.core.AbstractDao;
import org.springframework.stereotype.Service;

import com.zhiyu.bean.TReply;

@Service
public class ProductService {

	@Resource
	private AbstractDao hawaiiDao;
	
	public List<String> getProductList() {
		try {
			System.out.println(hawaiiDao.list(new QueryWrapper<TReply>(TReply.class), 1, 11));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
}
