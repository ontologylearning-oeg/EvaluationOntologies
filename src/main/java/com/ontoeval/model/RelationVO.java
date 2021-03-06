package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dchavesf on 1/09/16.
 */
@DatabaseTable(tableName = "Relations")
public class RelationVO{
    @DatabaseField(columnName="term1",canBeNull=false)
    private String term1;
    @DatabaseField(columnName="term2",canBeNull=false)
    private String term2;
    @DatabaseField(columnName="relation",canBeNull=false)
    private String relation;
    @DatabaseField(columnName="isGS")
    private boolean isGS;
    @DatabaseField(columnName = "yes")
    private Integer yes;
    @DatabaseField(columnName = "no")
    private Integer no;
    @DatabaseField(columnName = "Ontology" , foreign = true)
    private OntologyVO ontology;


    public RelationVO(OntologyVO o, String term1,String relation, String term2) {
        this.ontology = o;
        this.term1 = term1;
        this.term2 = term2;
        this.relation=relation;
        this.isGS = false;
        yes=0;
        no=0;
    }

    public RelationVO() {
        this.term1 = "example1";
        this.term2 = "example2";
        this.relation="relation";
        this.isGS = false;
        yes=0;
        no=0;
    }

    public String getTerm1() {
        return term1;
    }

    public void setTerm1(String term1) {
        this.term1 = term1;
    }

    public String getTerm2() { return term2; }

    public void setTerm2(String term2) {
        this.term2 = term2;
    }

    public boolean isGS() {
        return isGS;
    }

    public void setGS(boolean GS) {
        isGS = GS;
    }

    public Integer getYes() { return yes; }

    public void setYes(Integer yes) { this.yes = yes; }

    public Integer getNo() { return no; }

    public void setNo(Integer no) { this.no = no; }

    public OntologyVO getOntology() {
        return ontology;
    }

    public void setOntology(OntologyVO ontology) {
        this.ontology = ontology;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
