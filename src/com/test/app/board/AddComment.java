package com.test.app.board;

import com.test.app.dao.BoardDAO;
import com.test.app.dto.CommentDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/board/addcomment.do")
public class AddComment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 데이터 가져오기(content, pesq)
        String pseq = req.getParameter("pseq");  // 보고있던 글번호(=작성중인 댓글의 부모 글번호)
        String content = req.getParameter("content");

        // 2. DB작업 > DAO 위임 > insert
        BoardDAO dao = new BoardDAO();
        CommentDTO dto = new CommentDTO();

        HttpSession session = req.getSession();

        dto.setId(session.getAttribute("id").toString());
        dto.setPseq(pseq);
        dto.setContent(content);

        int result = dao.addComment(dto); // 1, 0

        // 3. 돌아가기 > view.do?seq=10
        if (result == 1){
            resp.sendRedirect("/myapp/board/view.do?seq=" + pseq);
        }else {
            resp.setCharacterEncoding("UTF-8");

            PrintWriter writer  = resp.getWriter();

            writer.print("<html>");
            writer.print("<body>");
            writer.print("<script>");
            writer.print("alert('댓글 쓰기 실패');");
            writer.print("history.back();");
            writer.print("</script>");
            writer.print("</body>");
            writer.print("</html>");

            writer.close();
        }



    }
}
