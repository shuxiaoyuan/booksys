package com.qianfeng.service;

import com.qianfeng.entity.OrderItem;
import com.qianfeng.entity.Orders;
import com.qianfeng.vo.PageBean;

public interface IOrderService {
	// ��Ӷ���
	public Orders addOrderInfo(Double price, String name);
	// �����ϸ
	public void addOrderItems(String[] ids, String[] nums, Orders orders);
	
    public PageBean<OrderItem> findItemByIndex(String name, int page);
	
}
