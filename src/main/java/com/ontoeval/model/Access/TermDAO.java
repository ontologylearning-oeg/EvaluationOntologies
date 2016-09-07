package com.ontoeval.model.Access;

import com.ontoeval.model.TermVO;

import java.util.ArrayList;

/**
 * Created by dachafra on 30/06/16.
 */
public interface TermDAO {

    boolean InsertTerm(TermVO t);
    boolean InsertTerms(ArrayList<TermVO> terms);
    ArrayList<TermVO> loadTerms(String ontology);
    boolean updateTerms(ArrayList<TermVO> terms);
    ArrayList<TermVO> loadRelevant(String ontology);
    ArrayList<TermVO> loadControl(String ontology);



}
