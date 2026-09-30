package com.library.model;

public class Member {
    private int id;
    private String name, email, phone, joinedDate;

    public Member() {}

    public Member(int id,String name,String email,String phone,String joinedDate){
        this.id=id;this.name=name;this.email=email;this.phone=phone;this.joinedDate=joinedDate;
    }

    public int getId(){return id;} public void setId(int v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getJoinedDate(){return joinedDate;} public void setJoinedDate(String v){joinedDate=v;}
}
