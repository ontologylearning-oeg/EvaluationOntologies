package com.ontoeval.model.Access;

import com.ontoeval.model.TermEvaluationVO;

import java.util.ArrayList;

/**
 * Created by dchavesf on 2/09/16.
 */
public interface TermEvaluationDAO {

    public ArrayList<TermEvaluationVO> evaluatedTermsUser(String ontology, String user);
    public ArrayList<TermEvaluationVO> evaluatedTerms(String ontology);

}
