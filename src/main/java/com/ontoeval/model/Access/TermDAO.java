package com.ontoeval.model.Access;

import com.ontoeval.model.TermVO;

import java.util.ArrayList;

/**
 * Created by dachafra on 30/06/16.
 */
public interface TermDAO {

    public boolean InsertTerm(TermVO t);

    public boolean InsertTerms(ArrayList<TermVO> terms);

    public ArrayList<TermVO> loadTerms(String ontology);


}
