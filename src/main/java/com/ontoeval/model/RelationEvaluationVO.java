package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dchavesf on 5/09/16.
 */
@DatabaseTable(tableName = "TaxonomicEvaluation")
public class RelationEvaluationVO extends OntologyVO {
    @DatabaseField(columnName="term1",canBeNull=false, uniqueCombo = true)
    private String term1;
    @DatabaseField(columnName="term2",canBeNull=false, uniqueCombo = true)
    private String term2;
    @DatabaseField(columnName="User",canBeNull=false, uniqueCombo = true)
    private String user;
    @DatabaseField(columnName="isRelevant")
    private boolean isRelevant;

    public RelationEvaluationVO(String name, String domain, String term1, String term2, String user, boolean isRelevant) {
        super(name, domain);
        this.term1 = term1;
        this.term2 = term2;
        this.user = user;
        this.isRelevant = isRelevant;
    }

    public RelationEvaluationVO(){
        super("exmaple","example");
        this.term1="term1";
        this.term2="term2";
        this.user=null;
        this.isRelevant=false;
    }

    public String getTerm1() { return term1; }

    public void setTerm1(String term1) {
        this.term1 = term1;
    }

    public String getTerm2() {
        return term2;
    }

    public void setTerm2(String term2) {
        this.term2 = term2;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isRelevant() {
        return isRelevant;
    }

    public void setRelevant(boolean relevant) {
        isRelevant = relevant;
    }
}
