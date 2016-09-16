package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ontoeval.controller.services.OntologyHelper;

/**
 * Created by dchavesf on 2/09/16.
 */
@DatabaseTable(tableName = "LexicalEvaluation")
public class TermEvaluationVO extends OntologyVO{

    @DatabaseField(columnName="Term",canBeNull=false)
    private String term;
    @DatabaseField(columnName="Relevant")
    private boolean relevant;

    public TermEvaluationVO(String ontology, String domain,String term, String user, boolean isRelvant) {
        super(ontology,domain,user);
        this.term = term;
        this.relevant = isRelvant;
    }

    public TermEvaluationVO(){
        super("example","domain","user");
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
}
