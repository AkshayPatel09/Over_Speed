package com.example.final_main;

import java.util.Date;

public class UserFeedback {

    private String message, email;
    private String curDate;

    public UserFeedback() {

    }

    public String getMessage() {
        return this.message;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDate() {
        return this.curDate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(String curDate) {
        this.curDate = curDate;
    }


}
