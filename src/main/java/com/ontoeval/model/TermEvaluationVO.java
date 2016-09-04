package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dchavesf on 2/09/16.
 */
@DatabaseTable(tableName = "TermEvaluation")
public class TermEvaluationVO {

    @DatabaseField(columnName="Term",canBeNull=false, uniqueCombo = true)
    private String term;
    @DatabaseField(columnName="User",canBeNull=false, uniqueCombo = true)
    private String user;
    @DatabaseField(columnName="Relevant")
    private boolean relevant;

    public TermEvaluationVO(String term, String user) {
        this.term = term;
        this.user = user;
        this.relevant = false;
    }

    public TermEvaluationVO() {
        term = "example";
        user = "example@example.com";
        relevant=false;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isRelevant() {
        return relevant;
    }

    public void setRelevant(boolean relevant) {
        this.relevant = relevant;
    }
}
