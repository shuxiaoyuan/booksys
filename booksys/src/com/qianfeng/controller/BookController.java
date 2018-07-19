package com.qianfeng.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.Books;
import com.qianfeng.service.IBookService;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class BookController {
	@Autowired
	private IBookService bookService;
	
	@RequestMapping(value="/books/page/{page}", method=RequestMethod.GET)
	public @ResponseBody JsonBean findByPage(@PathVariable("page") Integer page) {
		// @PathVariable("page") 为从路径中取对应变量的值
		
		JsonBean bean = new JsonBean();
		try {
			PageBean<Books> infos = bookService.findByPage(page);
			bean.setCode(1);
			bean.setMsg(infos);
		}catch(Exception e) {
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;	
	}
	
    @RequestMapping(value="/stock", method=RequestMethod.POST)
    public @ResponseBody JsonBean findStock(Integer bid, HttpServletResponse response) {
        
        JsonBean bean = new JsonBean();
        try {
           Integer stock = bookService.findStock(bid);
            bean.setCode(1);
            bean.setMsg(stock);
        }catch(Exception e) {
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }
        return bean;    
    }
	
    
    //BASE64解码成File文件
    public static void base64ToFile(String destPath,String base64, String fileName) {
        File file = null;
        //创建文件目录
        String filePath=destPath;
        File  dir=new File(filePath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            file=new File(filePath+"/"+fileName);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    
    
    @RequestMapping(value="/addBook", method=RequestMethod.POST)
    public @ResponseBody JsonBean addBook(String bookName, Double price, Integer stock, String img) {
        JsonBean bean = new JsonBean();
        try {
            // 图片重命名
            //String fileExtensionName = img.substring(img.lastIndexOf(".")+1);
            String fileExtensionName = "gif";
            String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;

            // 目标路径
            String destPath = "images/book";
            
            img = img.replace("data:image/gif;base64,", "");
            base64ToFile(destPath, img, uploadFileName);
            
            Books book = new Books();
            book.setBookName(bookName);
            book.setPrice(price);
            book.setStock(stock);
            book.setImg(destPath + "/" + uploadFileName);
            
            bookService.addBook(book);
            bean.setCode(1);
            bean.setMsg(img);
        }catch(Exception e) {
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }
        return bean;    
    }
    
    
    @RequestMapping(value="/deleteBook", method=RequestMethod.POST)
    public @ResponseBody JsonBean deleteBook(Integer bid) {
        
        JsonBean bean = new JsonBean();
        try {

            // 查询这本书是否有购买信息
            int count = bookService.findCountOfOrderItems(bid);
            if(count > 0) {
                bean.setCode(0);
                bean.setMsg("此书有购买信息，不能删除！");
                return bean;
            }
            Integer bids[] = new Integer[] {bid};
            Integer stocks[] = new Integer[] {-1};
            bookService.updateStock(bids, stocks);
            bean.setCode(1);
        }catch(Exception e) {
            bean.setCode(0);
            bean.setMsg(e.getMessage());
        }
        return bean;    
    }
    
}
