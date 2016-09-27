package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dchavesf on 27/09/16.
 */
@DatabaseTable(tableName = "UserEval")
public class UserEvalVO {
    @DatabaseField(columnName="user",canBeNull=false)
    private String user;
    @DatabaseField(columnName="ontology",canBeNull=false)
    private String ontology;
    @DatabaseField(columnName="valid",canBeNull=false)
    private boolean valid;

    public UserEvalVO(String user, String ontology, boolean valid) {
        this.user = user;
        this.ontology = ontology;
        this.valid = valid;
    }

    public UserEvalVO() {
        this.user = "";
        this.ontology = "";
        this.valid = false;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOntology() {
        return ontology;
    }

    public void setOntology(String ontology) {
        this.ontology = ontology;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
