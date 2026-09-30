package com.library.servlet;

import com.library.util.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/api/dashboard")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException{
        res.setContentType("application/json");
        String sql="SELECT (SELECT COUNT(*) FROM books) books,(SELECT COALESCE(SUM(available_copies),0) FROM books) available,"+
                   "(SELECT COUNT(*) FROM members) members,(SELECT COUNT(*) FROM issues WHERE status='ISSUED') active,"+
                   "(SELECT COALESCE(SUM(i.fine),0) FROM issues i LEFT JOIN payments p ON p.issue_id=i.id "+
                   "WHERE i.fine>0 AND p.id IS NULL) pending";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql);ResultSet r=p.executeQuery()){
            r.next();res.getWriter().printf("{\"books\":%d,\"available\":%d,\"members\":%d,\"active\":%d,\"pending\":%.2f}",
                r.getInt("books"),r.getInt("available"),r.getInt("members"),r.getInt("active"),r.getDouble("pending"));
        }catch(Exception e){res.sendError(500,e.getMessage());}
    }
}
