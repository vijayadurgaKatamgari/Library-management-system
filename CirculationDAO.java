package com.library.dao;

import com.library.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class CirculationDAO {
    public void issue(int bookId,int memberId)throws SQLException{
        String check="SELECT available_copies FROM books WHERE id=? FOR UPDATE";
        try(Connection c=DBConnection.getConnection()){
            c.setAutoCommit(false);
            try(PreparedStatement p=c.prepareStatement(check)){
                p.setInt(1,bookId);
                ResultSet r=p.executeQuery();
                if(!r.next() || r.getInt(1)<=0) throw new SQLException("No available copies.");
            }
            try(PreparedStatement p=c.prepareStatement(
                "INSERT INTO issues(book_id,member_id,issue_date,due_date) VALUES(?,?,?,?)")){
                p.setInt(1,bookId);p.setInt(2,memberId);
                p.setDate(3,Date.valueOf(LocalDate.now()));
                p.setDate(4,Date.valueOf(LocalDate.now().plusDays(14)));
                p.executeUpdate();
            }
            try(PreparedStatement p=c.prepareStatement(
                "UPDATE books SET available_copies=available_copies-1 WHERE id=?")){
                p.setInt(1,bookId);p.executeUpdate();
            }
            c.commit();
        }
    }

    public void returnBook(int issueId)throws SQLException{
        try(Connection c=DBConnection.getConnection()){
            c.setAutoCommit(false);
            int bookId;
            Date due;
            try(PreparedStatement p=c.prepareStatement(
                "SELECT book_id,due_date,status FROM issues WHERE id=? FOR UPDATE")){
                p.setInt(1,issueId);ResultSet r=p.executeQuery();
                if(!r.next()) throw new SQLException("Issue not found.");
                if("RETURNED".equals(r.getString("status"))) throw new SQLException("Book already returned.");
                bookId=r.getInt("book_id"); due=r.getDate("due_date");
            }
            long late=Math.max(0, java.time.temporal.ChronoUnit.DAYS.between(
                    due.toLocalDate(), LocalDate.now()));
            double fine=late*5.0;
            try(PreparedStatement p=c.prepareStatement(
                "UPDATE issues SET return_date=?,fine=?,status='RETURNED' WHERE id=?")){
                p.setDate(1,Date.valueOf(LocalDate.now()));p.setDouble(2,fine);p.setInt(3,issueId);
                p.executeUpdate();
            }
            try(PreparedStatement p=c.prepareStatement(
                "UPDATE books SET available_copies=available_copies+1 WHERE id=?")){
                p.setInt(1,bookId);p.executeUpdate();
            }
            c.commit();
        }
    }

    public List<Map<String,Object>> findAll()throws SQLException{
        List<Map<String,Object>> list=new ArrayList<>();
        String sql="SELECT i.id,b.title,m.name,i.issue_date,i.due_date,i.return_date,i.fine,i.status "+
                   "FROM issues i JOIN books b ON i.book_id=b.id JOIN members m ON i.member_id=m.id ORDER BY i.id DESC";
        try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(sql);ResultSet r=p.executeQuery()){
            while(r.next()){
                Map<String,Object> x=new LinkedHashMap<>();
                x.put("id",r.getInt("id"));x.put("title",r.getString("title"));x.put("member",r.getString("name"));
                x.put("issueDate",r.getDate("issue_date"));x.put("dueDate",r.getDate("due_date"));
                x.put("returnDate",r.getDate("return_date"));x.put("fine",r.getDouble("fine"));x.put("status",r.getString("status"));
                list.add(x);
            }
        }
        return list;
    }
}
