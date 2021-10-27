package com.test.app.board;

import com.test.app.dao.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/board/delcomment.do")
public class DelComment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 데이터 가져오기 (seq, pseq)
        String seq = req.getParameter("seq");   // 삭제할 글번호
        String pseq = req.getParameter("pseq"); //보고있던 글번호 (=작성중인 댓글의 부모 글번호)

        // 2. DB 작업 > DAO 위임 > DELETE
        BoardDAO dao = new BoardDAO();

        int result = dao.delComment(seq);  // 1, 0

        // 3. 결과 후 처리
        if (result == 1){
            resp.sendRedirect("/myapp/board/view.do?seq=" + pseq); //보고 있던 글번호를 가지고 돌아가기
        }else {
            resp.setCharacterEncoding("UTF-8");

            PrintWriter writer = resp.getWriter();

            writer.print("<html>");
            writer.print("<body>");
            writer.print("<script>");
            writer.print("alert('댓글 삭제 실패');");
            writer.print("history.back();");
            writer.print("</script>");
            writer.print("</body>");
            writer.print("</html>");

            writer.close();
        }
    }
}
