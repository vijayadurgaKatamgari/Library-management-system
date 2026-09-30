package com.library.dao;

import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;

public class PaymentDAO {
    public void add(int issueId,double amount,String method,String ref)throws SQLException{
        String sql="INSERT INTO payments(issue_id,amount,payment_method,payment_status,transaction_ref) VALUES(?,?,?,'PAID',?)";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql)){
            p.setInt(1,issueId);p.setDouble(2,amount);p.setString(3,method);p.setString(4,ref);p.executeUpdate();
        }
    }

    public void updateMethod(int paymentId,String method)throws SQLException{
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(
                "UPDATE payments SET payment_method=? WHERE id=?")){
            p.setString(1,method);p.setInt(2,paymentId);p.executeUpdate();
        }
    }

    public List<Map<String,Object>> findAll()throws SQLException{
        List<Map<String,Object>> list=new ArrayList<>();
        String sql="SELECT p.*,i.id AS issue_no,m.name AS member,b.title FROM payments p "+
                   "JOIN issues i ON p.issue_id=i.id JOIN members m ON i.member_id=m.id "+
                   "JOIN books b ON i.book_id=b.id ORDER BY p.id DESC";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql);ResultSet r=p.executeQuery()){
            while(r.next()){
                Map<String,Object>x=new LinkedHashMap<>();
                x.put("id",r.getInt("id"));x.put("issueNo",r.getInt("issue_no"));
                x.put("member",r.getString("member"));x.put("title",r.getString("title"));
                x.put("amount",r.getDouble("amount"));x.put("method",r.getString("payment_method"));
                x.put("status",r.getString("payment_status"));x.put("ref",r.getString("transaction_ref"));
                x.put("paidAt",r.getTimestamp("paid_at"));list.add(x);
            }
        }
        return list;
    }
}
