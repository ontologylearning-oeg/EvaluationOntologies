package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dchavesf on 27/09/16.
 */
@DatabaseTable(tableName = "UserEval")
public class UserEvalVO {
    @DatabaseField(columnName="user",canBeNull=false, foreign = true)
    private UserVO user;
    @DatabaseField(columnName="ontology",canBeNull=false, foreign = true)
    private OntologyVO ontology;
    @DatabaseField(columnName="valid",canBeNull=false)
    private boolean valid;

    public UserEvalVO(UserVO user, OntologyVO ontology, boolean valid) {
        this.user = user;
        this.ontology = ontology;
        this.valid = valid;
    }

    public UserEvalVO (){
        this.user = null;
        this.ontology = null;
        this.valid = false;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public OntologyVO getOntology() {
        return ontology;
    }

    public void setOntology(OntologyVO ontology) {
        this.ontology = ontology;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    }
