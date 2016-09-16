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
    @DatabaseField(columnName="isRelevant")
    private boolean isRelevant;

    public RelationEvaluationVO(String name, String domain, String term1, String term2, String user, boolean isRelevant) {
        super(name, domain,user);
        this.term1 = term1;
        this.term2 = term2;
        this.isRelevant = isRelevant;
    }

    public RelationEvaluationVO(){
        super("exmaple","example","user");
        this.term1="term1";
        this.term2="term2";
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

    public boolean isRelevant() {
        return isRelevant;
    }

    public void setRelevant(boolean relevant) {
        isRelevant = relevant;
    }
}
