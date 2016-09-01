package com.ontoeval.model;

/**
 * Created by dchavesf on 1/09/16.
 */
public class RelationVO extends OntologyVO{
    private String term1;
    private String term2;
    private boolean isGS;

    public RelationVO(String name, String term1, String term2) {
        super(name);
        this.term1 = term1;
        this.term2 = term2;
        this.isGS = false;
    }

    public RelationVO() {
        super("example");
        this.term1 = "example1";
        this.term2 = "example2";
        this.isGS = false;
    }

    public String getTerm1() {
        return term1;
    }

    public void setTerm1(String term1) {
        this.term1 = term1;
    }

    public String getTerm2() {
        return term2;
    }

    public void setTerm2(String term2) {
        this.term2 = term2;
    }

    public boolean isGS() {
        return isGS;
    }

    public void setGS(boolean GS) {
        isGS = GS;
    }
}
