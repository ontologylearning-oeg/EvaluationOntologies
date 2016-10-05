package com.ontoeval.model.Access;

import com.ontoeval.model.InstructionsVO;
import com.ontoeval.model.OntologyVO;

/**
 * Created by dchaves on 4/10/16.
 */
public interface InstructionsDAO {

    boolean insertIntruccion(InstructionsVO v);
    InstructionsVO getMeasure(OntologyVO o);
}
