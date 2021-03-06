package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc") //url에 들어갈 주소 값 ~.jsp 자리에 들어가면 서블렛으로 연결된다
public class PhoneController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PhoneDao phoneDao = new PhoneDao();
		//파라미터의 action 값을 읽어서 구분한다
		String action = request.getParameter("action");
//		System.out.println(action);
		
		switch(action) {
			case "list":
			{//변수명의 중복을 막기위해 지역변수로 만들 목적으로 case에 중괄호 사용
				//리스트 가져오기
				List<PersonVo> personList = phoneDao.getList();
				
				//데이터 넣기 --> request 어트리뷰트에 데이터를 넣는다
				request.setAttribute("personList", personList);
				
				//html로 표현 --> jsp로 포워드한다
//				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp"); //해당 주소에서 일을 대신하게 한다(위치를 정확하게 입력해야한다)
//				rd.forward(request, response);	//리퀘스트와 리스폰스를 rd의 주소로 넘겨준다
				String path = "/WEB-INF/list.jsp";
				WebUtil.forword(request, response, path);
			}	
				break;
			
			case "insertForm":
			{
				//insertForm으로 포워드
				String path = "/WEB-INF/insertForm.jsp";
				WebUtil.forword(request, response, path);
			}	
				break;
			
			case "insert":
			{
				//파라미터를 꺼낸다
				String name = request.getParameter("name");
				String hp = request.getParameter("hp");
				String company = request.getParameter("company");
				
				//vo로 묶어준다
				PersonVo personInsert = new PersonVo(name, hp, company);
	//			System.out.println(personInsert);	Vo 확인용 출력
				
				//dao --> 저장
				phoneDao.insert(personInsert);
				
				//리다이렉트
				String url = "/phonebook2/pbc?action=list";
				WebUtil.redirect(request, response, url);
			}
				break;
				
			case "delete":
			{
				//파라미터를 꺼낸다
				int personId = Integer.parseInt(request.getParameter("personId"));
				
				//dao --> 삭제
				phoneDao.delete(personId);
				
				//리다이렉트
				String url = "/phonebook2/pbc?action=list";
				WebUtil.redirect(request, response, url);
			}	
				break;
				
			case "updateForm":
			{
				//list의 수정 버튼으로 personId 값을 받아서 옮긴다
				int personId = Integer.parseInt(request.getParameter("personId"));
	//			System.out.println(personId + "case:updateForm");	personId가 전송되지 않는 에러 발생, 확인용 출력 --> 해결
				
				//personId를 통한 호출
				PersonVo personInfo = phoneDao.getPerson(personId);
				
				//setAttribute로 값 저장
				request.setAttribute("personInfo", personInfo);
				
				//updateForm으로 포워드
				String path = "/WEB-INF/updateForm.jsp";
				WebUtil.forword(request, response, path);
			}
				break;
				
			case "update":
			{
				//파라미터를 꺼낸다
				int personId = Integer.parseInt(request.getParameter("personId"));
				String name = request.getParameter("name");
				String hp = request.getParameter("hp");
				String company = request.getParameter("company");
				
				//vo로 묶어준다
				PersonVo personUpdate = new PersonVo(personId, name, hp, company);
				
				//dao --> 수정
				phoneDao.update(personUpdate);
				
				//리다이렉트
				String url = "/phonebook2/pbc?action=list";
				WebUtil.redirect(request, response, url);
			}	
			break;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
