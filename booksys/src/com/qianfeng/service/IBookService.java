package com.qianfeng.service;


import com.qianfeng.entity.Books;
import com.qianfeng.vo.PageBean;

public interface IBookService {
	
	public PageBean<Books> findByPage(Integer page);
	
	// ������� id �������Ӧ�Ŀ��
	public Integer findStock(Integer bid);
	
	// ���¿��
	public void updateStock(Integer[] bids, Integer[] stocks);
	
	// ���ͼ��
	public void addBook(Books book);
	
	// ��ѯĳ�����ڶ������е�����
	public Integer findCountOfOrderItems(Integer id);
	
}
