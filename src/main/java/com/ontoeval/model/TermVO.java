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
    @DatabaseField(columnName = "isControl")
    private boolean isControl;

    public TermVO(String word, String ontology, String domain, String control) {
        super(ontology, domain);
        this.word = word;
        this.isRelevant=false;
        if(control.equals("yes")){
            this.isControl=true;
        }
        else{
            this.isControl=false;
        }
    }

    public TermVO(){
        super("example", "example");
        this.word="example";
        this.isRelevant=false;
        this.isControl=false;
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
}
