package com.ontoeval.model;

/**
 * Created by dachafra on 30/06/16.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "Term")
public class TermVO{

    @DatabaseField(columnName="noun",canBeNull=false, unique = true)
    private String word;
    @DatabaseField(columnName="Termhood",canBeNull=false)
    private Double termhood;
    @DatabaseField(columnName = "Yes")
    private Integer yes;
    @DatabaseField(columnName = "No")
    private Integer no;


    public TermVO(String word, Double termhood, Integer no, Integer yes) {
        this.word = word;
        this.termhood = termhood;
        this.no = no;
        this.yes = yes;
    }

    public TermVO(String word, Double termhood) {
        this.word = word;
        this.termhood = termhood;
    }

    public String getWord() {
        return word;
    }

    public Double getTermhood() {
        return termhood;
    }

    public Integer getYes() {
        return yes;
    }

    public Integer getNo() {
        return no;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTermhood(Double termhood) {
        this.termhood = termhood;
    }

    public void setYes(Integer yes) {
        this.yes = yes;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}
