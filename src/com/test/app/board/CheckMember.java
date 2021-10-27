package com.test.app.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class CheckMember {

    public void check(HttpServletRequest req, HttpServletResponse resp){
        try{
            //로그인 안한사람 찾기
            HttpSession session =req.getSession();

            if(session.getAttribute("id") == null || session.getAttribute("id").toString().equals("")){

                resp.setContentType("text/html;charset=utf-8");
                resp.setCharacterEncoding("UTF-8");

                PrintWriter writer = resp.getWriter();

                writer.print("<html>");
                writer.print("<head>");
                writer.print("<meta charset='utf-8'>");
                writer.print("</head>");
                writer.print("<body>");
                writer.print("<script>");
                writer.print("alert('로그인 후 사용이 가능합니다. ');");
                writer.print("location.href='/myapp/index.do';");
                writer.print("</script>");
                writer.print("</html>");

                writer.close();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("로그인 체크 예외 처리");
        }
    }
}
