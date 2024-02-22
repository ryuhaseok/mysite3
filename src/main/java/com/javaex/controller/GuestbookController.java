package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;


@WebServlet("/Guestbook")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GuestbookController");
		
		//user에서 업무구분
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addList".equals(action)) {
			System.out.println("guestbook:addList");
			
			GuestDao guestDao = new GuestDao();
			
			//리스트 가져오기
			List<GuestVo> guestList = guestDao.guestSelect();
			System.out.println(guestList);
			
			//데이터 담기 포워드
			request.setAttribute("guestList", guestList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
						
		}else if("add".equals(action)) {
			System.out.println("addList>add");
			
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			
			GuestVo guestVo = new GuestVo(name, password, content);
			
			GuestDao guestDao = new GuestDao();
			guestDao.insertGuestbook(guestVo);
			
			WebUtil.redirect(request, response, "http://localhost:8080/mysite3/Guestbook?action=addList");
			
		} else if("deleteform".equals(action)) {
			System.out.println("deleteform");
			
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println(no);
			
			request.setAttribute("no", no);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		} else if("delete".equals(action)) {
			System.out.println("deleteform>delete");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("pass");
			
			System.out.println(no);
			
			request.setAttribute("no", no);
			
			GuestVo guestVo = new GuestVo(no, password);
			
			GuestDao guestDao = new GuestDao();
			guestDao.guestDelete(guestVo);
			
			WebUtil.redirect(request, response, "http://localhost:8080/mysite3/Guestbook?action=addList");
			
		} 
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
