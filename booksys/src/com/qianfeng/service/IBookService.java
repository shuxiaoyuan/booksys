package com.qianfeng.service;


import com.qianfeng.entity.Books;
import com.qianfeng.vo.PageBean;

public interface IBookService {
	
	public PageBean<Books> findByPage(Integer page);
	
	// 根据书的 id 查找其对应的库存
	public Integer findStock(Integer bid);
	
	// 更新库存
	public void updateStock(Integer[] bids, Integer[] stocks);
	
	// 添加图书
	public void addBook(Books book);
	
	// 查询某本书在订单项中的数量
	public Integer findCountOfOrderItems(Integer id);
	
}
