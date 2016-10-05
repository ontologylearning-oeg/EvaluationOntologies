package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dchaves on 4/10/16.
 */

@DatabaseTable(tableName = "Instructions")
public class InstructionsVO {
    @DatabaseField(columnName="RelevantTerm")
    private String relevantTerm;
    @DatabaseField(columnName="NoRelevantTerm")
    private String noRelevantTerm;
    @DatabaseField(columnName="NoStrictlyRelevantTerm")
    private String noStrictlyRelevantTerm;
    @DatabaseField(columnName="Reason")
    private String reason;
    @DatabaseField(columnName="Ontology", foreign = true)
    private OntologyVO ontology;


    public InstructionsVO(String relevantTerm, String noRelevantTerm, String noStrictlyRelevantTerm, String reason, OntologyVO ontology) {
        this.relevantTerm = relevantTerm;
        this.noRelevantTerm = noRelevantTerm;
        this.noStrictlyRelevantTerm = noStrictlyRelevantTerm;
        this.reason = reason;
        this.ontology = ontology;
    }

    public InstructionsVO() {
        this.relevantTerm = "";
        this.noRelevantTerm = "";
        this.noStrictlyRelevantTerm = "";
        this.reason = "";
        this.ontology = null;
    }

    public String getRelevantTerm() {
        return relevantTerm;
    }

    public void setRelevantTerm(String relevantTerm) {
        this.relevantTerm = relevantTerm;
    }

    public String getNoRelevantTerm() {
        return noRelevantTerm;
    }

    public void setNoRelevantTerm(String noRelevantTerm) {
        this.noRelevantTerm = noRelevantTerm;
    }

    public String getNoStrictlyRelevantTerm() {
        return noStrictlyRelevantTerm;
    }

    public void setNoStrictlyRelevantTerm(String noStrictlyRelevantTerm) {
        this.noStrictlyRelevantTerm = noStrictlyRelevantTerm;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public OntologyVO getOntology() {
        return ontology;
    }

    public void setOntology(OntologyVO ontology) {
        this.ontology = ontology;
    }
}
