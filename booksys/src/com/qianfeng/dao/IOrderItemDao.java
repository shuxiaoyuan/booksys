package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.OrderItem;

public interface IOrderItemDao extends IBaseDao<OrderItem>{
    
    // 查询订单明细
    public List<OrderItem> findByIndex(Map<String, Object> info);
    /*
     * 
     * 张慧
     */
    public List<OrderItem> adminFindByIndex(Map<String, Object> map);

}
