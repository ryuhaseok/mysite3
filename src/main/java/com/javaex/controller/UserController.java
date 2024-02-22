package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			
		} else if("login".equals(action)) {
			System.out.println("loginform>login");
			
			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			
			UserVo userVo = new UserVo(id, password);
			
			UserDao userDao = new UserDao();
			UserVo authUser = userDao.selectUserByIdPw(userVo); //userVo는 id, pw값
			//authUser는 no, name 값
			
			if(authUser != null) {//로그인 성공
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);
				
				WebUtil.redirect(request, response, "/mysite3/Main");
				
			} else {//로그인 실패
				System.out.println("로그인 실패");
				
				WebUtil.redirect(request, response, "/mysite3/User?action=loginform");
			}
			
		} else if("logout".equals(action)) {
			System.out.println("user>logout");
			
			HttpSession session = request.getSession();
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite3/Main");
			
		} else if("modifyform".equals(action)) {
			System.out.println("user>modifyform");
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			System.out.println(authUser.getNo());
			// System.out.println(authUser.getId()); //null값- authUser는 no, name값만 가지고있기때문

			//db에서 Id값 가져오기
			UserDao userDao = new UserDao();
			UserVo userId = userDao.selectUserById(authUser.getNo());
			
			session.setAttribute("userId", userId);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
		} else if("modify".equals(action)) {
			System.out.println("modifyform>modify");
			
			//세션 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			System.out.println(authUser.getNo());
			
			UserVo userId = (UserVo)session.getAttribute("userId");//이거 안써서 로그인 풀림
			System.out.println(userId.getId());
			
			//인풋 파라미터 가져오기
			String id = userId.getId(); //userId가 있어야 id값 가져오는데 id값을 못 가져와서
			int no = authUser.getNo();
			String password = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//vo에 담고
			UserVo userVo = new UserVo(no, password, name, gender);
			
			//dbㅇ연결
			UserDao userDao = new UserDao();
			//수정 메소드
			userDao.update(userVo);
			
			UserVo userVoByIdPw = new UserVo(id, password);//이게 null이었고
			
			
			authUser = userDao.selectUserByIdPw(userVoByIdPw);//그래서 여기서 authUser가 null값이었던 것으로 추측
			session.setAttribute("authUser", authUser);
			
			WebUtil.redirect(request, response, "/mysite3/Main");
			
		} else {
			System.out.println("action값을 다시 확인해주세요");
		}
		
		//회원가입
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
