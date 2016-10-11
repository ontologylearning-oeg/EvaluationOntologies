package com.ontoeval.model.Access;

import com.ontoeval.model.OntologyVO;

import java.util.ArrayList;

/**
 * Created by dchavesf on 1/09/16.
 */
public interface OntologyDAO {


    boolean insertOntology(OntologyVO ontology);
    ArrayList<OntologyVO> recuperarOntologias();
    OntologyVO recuperarOntologias(String name);
    ArrayList<OntologyVO> getOntologiesByUser(String name);
    void updateOntology(OntologyVO o);
    boolean removeOntology(String name);
    void close();

}
