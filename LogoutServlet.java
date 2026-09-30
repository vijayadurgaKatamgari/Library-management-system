package com.library.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/api/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException{
        HttpSession s=req.getSession(false);
        if(s!=null)s.invalidate();
        res.sendRedirect(req.getContextPath()+"/index.html");
    }
}
