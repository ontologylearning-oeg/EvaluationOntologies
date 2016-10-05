package com.ontoeval.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.StringTokenizer;

/**
 * Created by dchavesf on 1/09/16.
 */
@DatabaseTable(tableName = "Ontologies")
public class OntologyVO {
    @DatabaseField(columnName="Name",canBeNull=false, id=true)
    private String name;
    @DatabaseField(columnName="Domain", canBeNull = false)
    private String domain;
    @DatabaseField(columnName="State", canBeNull = false)
    private String state;
    @DatabaseField(columnName="User", foreign = true)
    private UserVO user;


    public OntologyVO(String name, String domain, UserVO user) {
        StringTokenizer tokenizer = new StringTokenizer(name,".");
        this.name = tokenizer.nextToken();
        this.domain=domain;
        this.user=user;
        this.state = "Eval lexical layer";
    }

    public OntologyVO() {
        this.name = "example";
        this.domain = "example";
        this.user=null;
        this.state = "Eval lexical layer";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() { return domain; }

    public void setDomain(String domain) { this.domain = domain; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
}
