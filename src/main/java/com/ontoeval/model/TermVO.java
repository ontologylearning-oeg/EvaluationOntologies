package com.ontoeval.model;

/**
 * Created by dachafra on 30/06/16.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "Terms")
public class TermVO extends OntologyVO{
    @DatabaseField(columnName="term",canBeNull=false)
    private String word;
    @DatabaseField(columnName="isRelevant")
    private boolean relevant;
    @DatabaseField(columnName="isGS")
    private boolean isGoldStandad;
    @DatabaseField(columnName = "isControl")
    private boolean control;
    @DatabaseField(columnName = "yes")
    private Integer yes;
    @DatabaseField(columnName = "no")
    private Integer no;

    public TermVO(String word, String ontology, String domain, String control, String isRelevant,String user) {
        super(ontology, domain,user);
        this.word = word;
        if(isRelevant.equals("yes"))
            this.relevant=true;
        else
            this.relevant=false;
        if(control.equals("yes")){
            this.control=true;
        }
        else{
            this.control=false;
        }

        this.isGoldStandad=false;
        this.yes=0;
        this.no=0;
    }

    public TermVO(){
        super("example", "example","user");
        this.word="example";
        this.relevant=true;
        this.control=false;
        this.isGoldStandad=false;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean getRelevant() { return relevant; }

    public void setRelevant(boolean relevant) { this.relevant = relevant; }

    public boolean getControl() {return control;}

    public void setControl(boolean control) { this.control = control; }

    public Integer getYes() {
        return yes;
    }

    public void setYes(Integer yes) {
        this.yes = yes;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public boolean isGoldStandad() {
        return isGoldStandad;
    }

    public void setGoldStandad(boolean goldStandad) {
        isGoldStandad = goldStandad;
    }
}
