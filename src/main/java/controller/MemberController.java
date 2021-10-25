package controller;

import domain.Blog;
import domain.Member;
import repository.BlogDAOImpl;
import repository.MemberDAOImpl;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/member/signup.do", "/member/signin.do", "/member/list.do", "/member/datail.do",
        "/member/update.do", "/member/delete.do", "/member/signout.do", "/member/idcheck.do", "/member/memberlist.do"},
        name = "MemberController")
public class MemberController extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(); // 있는 경우 반환, 없는 경우 만들어서 반환
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1);

        MemberDAOImpl repository = null;
        List<Member> memberList = null;

        MemberDAOImpl dao = new MemberDAOImpl();
        if(action.equals("signin.do")) {
            session.setAttribute("login", null);
            String email = request.getParameter("email");
            String pw = request.getParameter("pw");
            String checked = request.getParameter("checked");

            Member member = new Member(); // 전송하기 위한
            member.setEmail(email);
            member.setPw(pw);

            Member retMember = null; // 데이터베이스에서 가져온 레코드를 객체에 저장
            if((retMember = dao.read(member)) != null && pw.equals(retMember.getPw())) {
                //로그인 성공
                session.setAttribute("login", retMember);
                if (checked != null && checked.equals("yes")) { // cookie로 email 자동 완성
                    Cookie cookie_id = new Cookie("email", email);
                    response.addCookie(cookie_id);
                } else {
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals("email")) {
                                cookie.setMaxAge(0);
                                response.addCookie(cookie);
                            }
                        }
                    }
                }
                request.getRequestDispatcher("../main/index.jsp").forward(request, response);
            } else {
                //로그인 실패
                request.setAttribute("message", "아이디 또는 암호를 확인하십시요");
                request.getRequestDispatcher("../errors/message.jsp").forward(request, response);
            }
        }
        else if(action.equals("signout.do")) {
            session.invalidate();
            response.sendRedirect("../member/signin-form.jsp");
        }
        else if(action.equals("signup.do")) {
            String pw = request.getParameter("pw");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            Member member = new Member(); // 전송하기 위한 객체 : DTO
            member.setEmail(email);
            member.setPw(pw);
            member.setName(name);
            member.setPhone(phone);
            member.setAddress(address);

            int row = 0;
            if((row = dao.create(member)) > 0) { // 등록 성공
                request.getRequestDispatcher("../member/signin-form.jsp").forward(request, response);
            } else { // 등록 실패
                request.setAttribute("message", "가입 정보를 확인하십시요");
                request.getRequestDispatcher("../errors/message.jsp").forward(request, response);
            }
        }
        else if(action.equals("update.do")) {
            String email = request.getParameter("email");
            String pw = request.getParameter("pw");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            Member member = new Member(); // 전송하기 위한 객체
            member.setEmail(email);
            member.setPw(pw);
            member.setName(name);
            member.setPhone(phone);
            member.setAddress(address);

            int row = 0;
            if((row = dao.update(member)) > 0) { // 업데이트 성공
                session.setAttribute("login", member); // 세션 정보 업데이트
                request.getRequestDispatcher("../member/detail-form.jsp").forward(request, response);
            } else { // 업데이트 실패
                request.setAttribute("message", "업데이트 암호를 확인하십시요");
                request.getRequestDispatcher("../errors/message.jsp").forward(request, response);
            }
        }
        else if(action.equals("delete.do")) {
            String email = request.getParameter("email");
            String pw = request.getParameter("pw");
            Member member = new Member(); // 전송하기 위한 객체
            member.setEmail(email);
            member.setPw(pw);
            int row = 0;
            if((row = dao.delete(member)) > 0) { // 삭제 성공
                session.invalidate();
                request.setAttribute("message", "회원탈퇴 : 다음에 좋은 서비스로 뵙겠습니다.");
                request.getRequestDispatcher("../member/delete-view.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "회원탈퇴 실패 : 암호를 확인하십시요.");
                request.getRequestDispatcher("../errors/message.jsp").forward(request, response);
            }
        }
        else if(action.equals("idcheck.do")) {
            String email = request.getParameter("email");
            String pw = request.getParameter("pw");

            Member member = new Member(); // 전송하기 위한
            member.setEmail(email);
            member.setPw(pw);

            Member retMember = null; // 데이터베이스에서 가져온 레코드를 객체에 저장
            if ((retMember = dao.read(member)) != null && pw.equals(retMember.getPw())) {
                request.getRequestDispatcher("../blog/delete.do").forward(request, response);
            } else {
                request.setAttribute("message", "암호를 확인하십시요");
                request.getRequestDispatcher("../errors/message.jsp").forward(request, response);
            }
        }
        else if(action.equals("memberlist.do")){
            ArrayList<Member> memberLit = dao.getMemberList();
            request.setAttribute("memberList", memberLit);
            request.getRequestDispatcher("../member/member-list.jsp").forward(request, response);
        }
        else {
            request.setAttribute("message", "잘못된 요청입니다. 확인하십시요.");
            request.getRequestDispatcher("../errors/message.jsp").forward(request, response);
        }

    }
}
