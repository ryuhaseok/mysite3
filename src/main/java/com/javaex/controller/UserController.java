package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;
import com.javaex.vo.UserVo;


@WebServlet("/User")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("userController");
		
		//user에서 업무구분
		String action = request.getParameter("action");
		System.out.println(action);
		
		
		if("joinform".equals(action)) {
			System.out.println("user>joinform");
			
			//회원가입폼
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
		} else if("join".equals(action)) {
			System.out.println("user>join");
			
			//파라미터에서 데이터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//데이터를 vo로 묶어준다
			
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo);
			
			//dao 실행
			UserDao userDao = new UserDao();
			userDao.insertUser(userVo);
			
			//joinOk.jsp로 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
			
		} else if("loginform".equals(action)) {
			System.out.println("joinOk>loginform");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		} else if("addList".equals(action)) {
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
			
			WebUtil.redirect(request, response, "http://localhost:8080/mysite3/User?action=addList");
			
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
			
			WebUtil.redirect(request, response, "http://localhost:8080/mysite3/User?action=addList");
			
		} else {
			System.out.println("action값을 다시 확인해주세요");
		}
		
		//회원가입
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
