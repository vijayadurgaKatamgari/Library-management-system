package com.library.model;

public class Book {
    private int id;
    private String title, author, category, isbn;
    private int totalCopies, availableCopies;

    public Book() {}

    public Book(int id, String title, String author, String category, String isbn,
                int totalCopies, int availableCopies) {
        this.id=id; this.title=title; this.author=author; this.category=category;
        this.isbn=isbn; this.totalCopies=totalCopies; this.availableCopies=availableCopies;
    }

    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getAuthor(){return author;} public void setAuthor(String v){author=v;}
    public String getCategory(){return category;} public void setCategory(String v){category=v;}
    public String getIsbn(){return isbn;} public void setIsbn(String v){isbn=v;}
    public int getTotalCopies(){return totalCopies;} public void setTotalCopies(int v){totalCopies=v;}
    public int getAvailableCopies(){return availableCopies;} public void setAvailableCopies(int v){availableCopies=v;}
}
