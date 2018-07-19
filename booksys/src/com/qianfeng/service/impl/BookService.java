package com.qianfeng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.IBookDao;
import com.qianfeng.dao.IOrderItemDao;
import com.qianfeng.entity.Books;
import com.qianfeng.service.IBookService;
import com.qianfeng.vo.PageBean;

@Service
public class BookService  implements IBookService{
	@Autowired
	private IBookDao bookDao;
	
	@Autowired
	private IOrderItemDao orderItemDao;

	@Override
	public PageBean<Books> findByPage(Integer page) {
		// TODO Auto-generated method stub	
		if(page == null || page < 1) {
			throw new RuntimeException("Ò³ÂëÊý¾ÝÓÐÎó");
		}
		PageBean<Books> pageBean = new PageBean<>();
		pageBean.setCurrentPage(page);
		
		int count = 0;
		try {
			count = bookDao.count();
			pageBean.setCount(count);
		}catch(Exception e) {
			throw e;
		}
		
		if(count % pageBean.getSize() == 0) {
			pageBean.setTotalPage(count / pageBean.getSize());
		}else {
			pageBean.setTotalPage(count / pageBean.getSize() + 1);
		}
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("size", pageBean.getSize());
		int index = (page - 1) * pageBean.getSize();
		map.put("index", index);
		try {
			List<Books> books = bookDao.findByIndex(map);
			pageBean.setPageInfos(books);
		}catch(Exception e) {
			throw e;
		}
		
		return pageBean;
	}

    @Override
    public Integer findStock(Integer bid) {
        // TODO Auto-generated method stub
        return bookDao.findStock(bid);
    }

    @Override
    public void updateStock(Integer[] bids, Integer[] stocks) {
        // TODO Auto-generated method stub
        for(int i = 0; i < bids.length; i++) {
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("id", bids[i]);
                map.put("stock", stocks[i]);
                bookDao.updateStock(map);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addBook(Books book) {
        // TODO Auto-generated method stub
        try {
            bookDao.add(book);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Integer findCountOfOrderItems(Integer id) {
        // TODO Auto-generated method stub
        Integer count = orderItemDao.countByBookId(id);
        return count;
    }

}
