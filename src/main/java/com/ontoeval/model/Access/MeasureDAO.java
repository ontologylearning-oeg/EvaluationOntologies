package com.ontoeval.model.Access;

import com.ontoeval.model.MeasureVO;
import com.ontoeval.model.OntologyVO;

/**
 * Created by dchavesf on 5/09/16.
 */
public interface MeasureDAO {

    boolean insertMeasure(MeasureVO v);
    MeasureVO getMeasure(OntologyVO o);
    void close();
}
