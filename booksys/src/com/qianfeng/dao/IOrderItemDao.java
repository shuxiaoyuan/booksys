package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.base.IBaseDao;
import com.qianfeng.entity.OrderItem;

public interface IOrderItemDao extends IBaseDao<OrderItem>{
    
    // ��ѯ������ϸ
    public List<OrderItem> findByIndex(Map<String, Object> info);
    /*
     * 
     * �Ż�
     */
    public List<OrderItem> adminFindByIndex(Map<String, Object> map);

}
