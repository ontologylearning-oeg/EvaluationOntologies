package com.ontoeval.model.Access;

import com.ontoeval.model.OntologyVO;

import java.util.ArrayList;

/**
 * Created by dchavesf on 1/09/16.
 */
public interface OntologyDAO {


    public boolean insertOntology(OntologyVO ontology);
    public ArrayList<OntologyVO> recuperarOntologias();
    public OntologyVO recuperarOntologias(String name);
}
