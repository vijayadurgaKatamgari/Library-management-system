package com.library.servlet;

import com.library.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.*;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
        String u=req.getParameter("username"),pw=req.getParameter("password");
        try(Connection c=DBConnection.getConnection();
            PreparedStatement p=c.prepareStatement("SELECT id FROM admins WHERE username=? AND password=?")){
            p.setString(1,u);p.setString(2,pw);
            ResultSet r=p.executeQuery();
            if(r.next()){
                req.getSession().setAttribute("admin",u);
                res.sendRedirect(req.getContextPath()+"/dashboard.html");
            }else res.sendRedirect(req.getContextPath()+"/index.html?error=1");
        }catch(SQLException e){res.sendError(500,e.getMessage());}
    }
}
