package com.library.servlet;

import com.library.dao.BookDAO;
import com.library.model.Book;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.SQLException;

@WebServlet("/api/books")
public class BookServlet extends HttpServlet {
    private final BookDAO dao=new BookDAO();

    protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException{
        res.setContentType("application/json");
        try{
            var list=dao.findAll(req.getParameter("search"));
            PrintWriter out=res.getWriter();out.print("[");
            for(int i=0;i<list.size();i++){
                Book b=list.get(i);
                if(i>0)out.print(",");
                out.printf("{\"id\":%d,\"title\":\"%s\",\"author\":\"%s\",\"category\":\"%s\",\"isbn\":\"%s\",\"total\":%d,\"available\":%d}",
                    b.getId(),esc(b.getTitle()),esc(b.getAuthor()),esc(b.getCategory()),esc(b.getIsbn()),b.getTotalCopies(),b.getAvailableCopies());
            }out.print("]");
        }catch(SQLException e){res.sendError(500,e.getMessage());}
    }

    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try{
            Book b=new Book();b.setTitle(req.getParameter("title"));b.setAuthor(req.getParameter("author"));
            b.setCategory(req.getParameter("category"));b.setIsbn(req.getParameter("isbn"));
            b.setTotalCopies(Integer.parseInt(req.getParameter("copies")));dao.add(b);res.sendRedirect("../books.html");
        }catch(Exception e){res.sendError(400,e.getMessage());}
    }

    protected void doDelete(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try{dao.delete(Integer.parseInt(req.getParameter("id")));res.setStatus(204);}
        catch(Exception e){res.sendError(400,e.getMessage());}
    }
    private String esc(String s){return s==null?"":s.replace("\\","\\\\").replace("\"","\\\"");}
}
