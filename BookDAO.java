package com.library.dao;

import com.library.model.Book;
import com.library.util.DBConnection;
import java.sql.*;
import java.util.*;

public class BookDAO {
    public List<Book> findAll(String search) throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ? ORDER BY id DESC";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            String s="%"+(search==null?"":search)+"%";
            p.setString(1,s);p.setString(2,s);p.setString(3,s);
            ResultSet r=p.executeQuery();
            while(r.next()) list.add(map(r));
        }
        return list;
    }

    public void add(Book b) throws SQLException {
        String sql="INSERT INTO books(title,author,category,isbn,total_copies,available_copies) VALUES(?,?,?,?,?,?)";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,b.getTitle());p.setString(2,b.getAuthor());p.setString(3,b.getCategory());
            p.setString(4,b.getIsbn());p.setInt(5,b.getTotalCopies());p.setInt(6,b.getTotalCopies());
            p.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("DELETE FROM books WHERE id=?")){
            p.setInt(1,id);p.executeUpdate();
        }
    }

    private Book map(ResultSet r)throws SQLException{
        return new Book(r.getInt("id"),r.getString("title"),r.getString("author"),
                r.getString("category"),r.getString("isbn"),r.getInt("total_copies"),
                r.getInt("available_copies"));
    }
}
