package com.example.avatarmind.robotmotion;


public class Attendee {

    public String name;
    public String age;
    public String phone;
    public String ecname;
    public String ecphone;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Attendee(){

    }

    public Attendee(String name, String  age, String phone, String ecname, String ecphone){
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.ecname = ecname;
        this.ecphone = ecphone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEcname() {
        return ecname;
    }

    public void setEcname(String ecname) {
        this.ecname = ecname;
    }

    public String getEcphone() {
        return ecphone;
    }

    public void setEcphone(String ecphone) {
        this.ecphone = ecphone;
    }
}
