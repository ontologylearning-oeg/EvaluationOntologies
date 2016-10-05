package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dchavesf on 1/09/16.
 */
@DatabaseTable(tableName = "Users")
public class UserVO {
    @DatabaseField(columnName="email",canBeNull=false, unique = true, id=true)
    private String email;
    @DatabaseField(columnName = "pass", canBeNull =false)
    private String password;

    public UserVO(String email, String passw){
        this.email=email;
        this.password=passw;
    }

    public UserVO(){
        this.email="example@example.com";
        this.password="pass";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
