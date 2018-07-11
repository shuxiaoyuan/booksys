package com.qianfeng.base;

import java.util.List;

public interface IBaseDao<T> {
	
	public void add(T t);
	
	public void delete(T t);
	
	public void update(T t);
	
	public List<T> findAll();
	
	public int count();
}
