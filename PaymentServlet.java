package com.library.servlet;

import com.library.dao.PaymentDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/api/payments")
public class PaymentServlet extends HttpServlet {
    private final PaymentDAO dao=new PaymentDAO();

    protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException{
        res.setContentType("application/json");
        try{
            var list=dao.findAll();PrintWriter out=res.getWriter();out.print("[");
            for(int i=0;i<list.size();i++){if(i>0)out.print(",");var x=list.get(i);
                out.print("{\"id\":"+x.get("id")+",\"issueNo\":"+x.get("issueNo")+",\"member\":\""+esc(""+x.get("member"))+
                "\",\"title\":\""+esc(""+x.get("title"))+"\",\"amount\":"+x.get("amount")+",\"method\":\""+x.get("method")+
                "\",\"status\":\""+x.get("status")+"\",\"ref\":\""+esc(""+x.get("ref"))+"\",\"paidAt\":\""+x.get("paidAt")+"\"}");}
            out.print("]");
        }catch(Exception e){res.sendError(500,e.getMessage());}
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try{dao.add(Integer.parseInt(req.getParameter("issueId")),Double.parseDouble(req.getParameter("amount")),
            req.getParameter("method"),req.getParameter("ref"));res.sendRedirect("../payments.html");}
        catch(Exception e){res.sendError(400,e.getMessage());}
    }
    protected void doPut(HttpServletRequest req,HttpServletResponse res)throws IOException{
        try{dao.updateMethod(Integer.parseInt(req.getParameter("paymentId")),req.getParameter("method"));res.setStatus(204);}
        catch(Exception e){res.sendError(400,e.getMessage());}
    }
    private String esc(String s){return s==null?"":s.replace("\\","\\\\").replace("\"","\\\"");}
}
