package com.library.servlet;

import com.library.dao.MemberDAO;
import com.library.model.Member;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.SQLException;

@WebServlet("/api/members")
public class MemberServlet extends HttpServlet {
    private final MemberDAO dao=new MemberDAO();

    protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException{
        res.setContentType("application/json");
        try{
            var list=dao.findAll();PrintWriter out=res.getWriter();out.print("[");
            for(int i=0;i<list.size();i++){Member m=list.get(i);if(i>0)out.print(",");
                out.printf("{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\",\"phone\":\"%s\",\"joined\":\"%s\"}",
                    m.getId(),esc(m.getName()),esc(m.getEmail()),esc(m.getPhone()),esc(m.getJoinedDate()));}
            out.print("]");
        }catch(SQLException e){res.sendError(500,e.getMessage());}
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try{Member m=new Member();m.setName(req.getParameter("name"));m.setEmail(req.getParameter("email"));
            m.setPhone(req.getParameter("phone"));dao.add(m);res.sendRedirect("../members.html");
        }catch(Exception e){res.sendError(400,e.getMessage());}
    }
    protected void doDelete(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try{dao.delete(Integer.parseInt(req.getParameter("id")));res.setStatus(204);}
        catch(Exception e){res.sendError(400,e.getMessage());}
    }
    private String esc(String s){return s==null?"":s.replace("\\","\\\\").replace("\"","\\\"");}
}
