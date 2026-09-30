package com.library.servlet;

import com.library.dao.CirculationDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/api/circulation")
public class CirculationServlet extends HttpServlet {
    private final CirculationDAO dao=new CirculationDAO();

    protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException{
        res.setContentType("application/json");
        try{
            var list=dao.findAll();PrintWriter out=res.getWriter();out.print("[");
            for(int i=0;i<list.size();i++){if(i>0)out.print(",");var x=list.get(i);
                out.print("{\"id\":"+x.get("id")+",\"title\":\""+esc(""+x.get("title"))+"\",\"member\":\""+esc(""+x.get("member"))+
                "\",\"issueDate\":\""+x.get("issueDate")+"\",\"dueDate\":\""+x.get("dueDate")+"\",\"returnDate\":\""+
                (x.get("returnDate")==null?"":x.get("returnDate"))+"\",\"fine\":"+x.get("fine")+",\"status\":\""+x.get("status")+"\"}");}
            out.print("]");
        }catch(Exception e){res.sendError(500,e.getMessage());}
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try{dao.issue(Integer.parseInt(req.getParameter("bookId")),Integer.parseInt(req.getParameter("memberId")));
            res.sendRedirect("../circulation.html");}catch(Exception e){res.sendError(400,e.getMessage());}
    }
    protected void doPut(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try{dao.returnBook(Integer.parseInt(req.getParameter("issueId")));res.setStatus(204);}
        catch(Exception e){res.sendError(400,e.getMessage());}
    }
    private String esc(String s){return s.replace("\\","\\\\").replace("\"","\\\"");}
}
