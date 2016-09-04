package com.ontoeval.model;

/**
 * Created by dachafra on 30/06/16.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "Terms")
public class TermVO extends OntologyVO{

    @DatabaseField(columnName="term",canBeNull=false, unique = true)
    private String word;
    @DatabaseField(columnName="isRelevant")
    private boolean isRelevant;

    public TermVO(String word, String ontology, String domain) {
        super(ontology, domain);
        this.word = word;
        this.isRelevant=false;
    }

    public TermVO(){
        super("example", "example");
        this.word="example";
        this.isRelevant=false;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isRelevant() { return isRelevant; }

    public void setRelevant(boolean relevant) { isRelevant = relevant; }
}
