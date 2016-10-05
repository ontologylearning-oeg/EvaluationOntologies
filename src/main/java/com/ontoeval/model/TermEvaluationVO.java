package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ontoeval.controller.services.OntologyHelper;

/**
 * Created by dchavesf on 2/09/16.
 */
@DatabaseTable(tableName = "LexicalEvaluation")
public class TermEvaluationVO{
    @DatabaseField(columnName="Term",canBeNull=false)
    private String term;
    @DatabaseField(columnName="Relevant")
    private boolean relevant;
    @DatabaseField(columnName = "Ontology", foreign = true)
    private OntologyVO ontology;
    @DatabaseField(columnName = "User", foreign = true)
    private UserVO user;

    public TermEvaluationVO(OntologyVO ontology, UserVO u, String term, boolean isRelvant) {
        this.ontology=ontology;
        this.user=u;
        this.term = term;
        this.relevant = isRelvant;
    }

    public TermEvaluationVO(){
        this.term="example";
        this.relevant=false;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public boolean isRelevant() {
        return relevant;
    }

    public void setRelevant(boolean relevant) {
        this.relevant = relevant;
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
