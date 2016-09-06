package com.ontoeval.model.Access;

import com.ontoeval.model.TermEvaluationVO;

import java.util.ArrayList;

/**
 * Created by dchavesf on 2/09/16.
 */
public interface TermEvaluationDAO {

    ArrayList<TermEvaluationVO> evaluatedTermsUser(String ontology, String user);
    ArrayList<TermEvaluationVO> evaluatedTerms(String ontology);
    boolean insertTerms(ArrayList<TermEvaluationVO> t);

}
