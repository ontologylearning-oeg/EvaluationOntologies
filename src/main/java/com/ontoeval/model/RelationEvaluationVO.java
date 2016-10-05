package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dchavesf on 5/09/16.
 */
@DatabaseTable(tableName = "TaxonomicEvaluation")
public class RelationEvaluationVO{
    @DatabaseField(columnName="term1",canBeNull=false)
    private String term1;
    @DatabaseField(columnName="term2",canBeNull=false)
    private String term2;
    @DatabaseField(columnName="isRelevant")
    private boolean isRelevant;
    @DatabaseField(columnName = "Ontology" , foreign = true)
    private OntologyVO ontology;
    @DatabaseField(columnName = "User" , foreign = true)
    private UserVO user;

    public RelationEvaluationVO(OntologyVO o, String term1, String term2, UserVO user, boolean isRelevant) {
        this.ontology=o;
        this.user=user;
        this.term1 = term1;
        this.term2 = term2;
        this.isRelevant = isRelevant;
    }

    public RelationEvaluationVO() {
        this.ontology=null;
        this.user=null;
        this.term1 = "";
        this.term2 = "";
        this.isRelevant = false;
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

    public OntologyVO getOntology() {
        return ontology;
    }

    public void setOntology(OntologyVO ontology) {
        this.ontology = ontology;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
}
