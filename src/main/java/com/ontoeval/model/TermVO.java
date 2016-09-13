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
    private boolean isRelevant;
    @DatabaseField(columnName="isGS")
    private boolean isGoldStandad;
    @DatabaseField(columnName = "isControl")
    private boolean isControl;
    @DatabaseField(columnName = "yes")
    private Integer yes;
    @DatabaseField(columnName = "no")
    private Integer no;

    public TermVO(String word, String ontology, String domain, String control, String isRelevant) {
        super(ontology, domain);
        this.word = word;
        if(isRelevant.equals("yes"))
            this.isRelevant=true;
        else
            this.isRelevant=false;
        if(control.equals("yes")){
            this.isControl=true;
        }
        else{
            this.isControl=false;
        }

        this.isGoldStandad=false;
    }

    public TermVO(){
        super("example", "example");
        this.word="example";
        this.isRelevant=true;
        this.isControl=false;
        this.isGoldStandad=false;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isRelevant() { return isRelevant; }

    public void setRelevant(boolean relevant) { isRelevant = relevant; }

    public boolean isControl() {return isControl;}

    public void setControl(boolean control) { isControl = control; }

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
