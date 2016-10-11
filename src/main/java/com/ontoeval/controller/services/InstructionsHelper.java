package com.ontoeval.controller.services;

import com.ontoeval.model.Access.Implement.EncryptConnection;
import com.ontoeval.model.Access.Implement.InstructionsImpl;
import com.ontoeval.model.Access.Implement.OntologyImpl;
import com.ontoeval.model.Access.InstructionsDAO;
import com.ontoeval.model.Access.OntologyDAO;
import com.ontoeval.model.InstructionsVO;
import com.ontoeval.model.OntologyVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchaves on 4/10/16.
 */
public class InstructionsHelper {
    private final HttpServletRequest request;
    private final InstructionsDAO instructions;

    public InstructionsHelper (HttpServletRequest request) throws SQLException, IOException {
        this.request = request;
        instructions = new InstructionsImpl(EncryptConnection.CrearConexion());
    }

    public void close(){
       instructions.close();
    }


    public boolean loadInstructions(String relevant,String norelevant, String strictly, String reason){
        OntologyVO o = (OntologyVO) request.getSession().getAttribute("ontology");
        InstructionsVO i = new InstructionsVO(relevant, norelevant, strictly,reason, o);
        return instructions.insertIntruccion(i);
    }

    public InstructionsVO getInstructions(OntologyVO ontology){
        return instructions.getMeasure(ontology);
    }
}
