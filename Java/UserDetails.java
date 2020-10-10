/* This is the package name. */
package com.example.healthylivingapp;
/* This class is used to allow the user's details to be stored in the Firebase database. */
public class UserDetails {
    public String userName;
    public String userEmail;
    public String userType;

/* A constructor is created. This constructor is a method which has the same name as the above public class. When an object of the UserDetails is created, this
method will be executed. */
public UserDetails(String userName, String userEmail, String userType) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userType = userType;
    }
}