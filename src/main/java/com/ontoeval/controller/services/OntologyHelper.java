package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by dchavesf on 1/09/16.
 */
public class OntologyHelper {
    private final HttpServletRequest request;
    private final OntologyDAO ontology;
    private final LexicalHelper lexical;
    private final ResultsHelper results;
    private final TaxonomicHelper taxonomic;

    public OntologyHelper (HttpServletRequest request) throws SQLException, IOException {
        this.request = request;
        lexical = new LexicalHelper(request);
        taxonomic = new TaxonomicHelper(request);
        results = new ResultsHelper(request);
        ontology = new OntologyImpl(OntologyImpl.CrearConexion());
    }

    public boolean loadOntologies() throws  SQLException{
        ServletContext context = request.getSession().getServletContext();
        ArrayList<OntologyVO> onts = ontology.recuperarOntologias();
        if(onts==null){
            throw  new SQLException("Error en OntologyHelper");
        }
        else{
            context.setAttribute("ontos",onts);
            return true;
        }

    }

    public String loadFeatures(String name) throws  IOException{
        ServletContext context = request.getSession().getServletContext();
        UserVO user = (UserVO) context.getAttribute("user");
        OntologyVO o = ontology.recuperarOntologias(name);
        context.setAttribute("ontology",o);
        String page=lexical.comprobarLexical(o,user);
        if(page==null) {
            taxonomic.createGSRelations(lexical.recuperarRelevants(o),o);
            page=taxonomic.comprobarTaxonomic(o,user);
            if(page==null){
                results.setTerms(lexical.recuperar(o.getName()));
                results.setRelations(taxonomic.recuperar(o.getName()));
                results.calcularResultados();
                return "./eval/results.jsp";
            }
            else {
                return page;
            }
        }
        else{
            return page;
        }

    }


    public boolean insertOntology(String text, String filename) throws  SQLException{
        String domain = loadDomain(text);
        if(!ontology.insertOntology(new OntologyVO(filename, domain)) ||
                !lexical.loadTerms(text,filename,domain) ||
                    !taxonomic.loadRelations(text,filename, domain))
            throw  new SQLException("Error en insertOntology/OntologyHelper");
        else{
             return true;
        }
    }
    private String loadDomain(String text){
        StringTokenizer tokenizer = new StringTokenizer(text,"\n");
        tokenizer = new StringTokenizer(tokenizer.nextToken(),";");
        tokenizer.nextToken();
        return tokenizer.nextToken();
    }


}
