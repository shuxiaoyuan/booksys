package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.OrderItem;

public interface IOrderItemDao extends IBaseDao<OrderItem>{
    
    // ²éÑ¯¶©µ¥Ã÷Ï¸
    public List<OrderItem> findByIndex(Map<String, Object> info);

}
