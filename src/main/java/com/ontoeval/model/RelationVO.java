package com.ontoeval.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by dchavesf on 1/09/16.
 */
@DatabaseTable(tableName = "Relations")
public class RelationVO extends OntologyVO{
    @DatabaseField(columnName="term1",canBeNull=false)
    private String term1;
    @DatabaseField(columnName="term2",canBeNull=false)
    private String term2;
    @DatabaseField(columnName="isGS")
    private boolean isGS;
    @DatabaseField(columnName="isRandom")
    private boolean isRandom;
    @DatabaseField(columnName = "yes")
    private Integer yes;
    @DatabaseField(columnName = "no")
    private Integer no;



    public RelationVO(String name, String term1, String term2, String domain, boolean isRandom) {
        super(name,domain);
        this.term1 = term1;
        this.term2 = term2;
        this.isGS = false;
        this.isRandom= isRandom;
        yes=0;
        no=0;
    }

    public RelationVO(String name, String term1, String term2, String domain, boolean isGS, boolean isRandom) {
        super(name,domain);
        this.term1 = term1;
        this.term2 = term2;
        this.isGS = isGS;
        this.isRandom= isRandom;
        yes=0;
        no=0;
    }

    public RelationVO() {
        super("example", "domain");
        this.term1 = "example1";
        this.term2 = "example2";
        this.isGS = false;
        this.isRandom = false;
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

    public boolean isRandom() { return isRandom; }

    public void setRandom(boolean random) { isRandom = random; }

    public Integer getYes() { return yes; }

    public void setYes(Integer yes) { this.yes = yes; }

    public Integer getNo() { return no; }

    public void setNo(Integer no) { this.no = no; }
}
