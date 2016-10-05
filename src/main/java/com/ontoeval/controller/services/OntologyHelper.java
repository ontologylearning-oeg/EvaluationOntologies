package com.ontoeval.controller.services;

import com.ontoeval.model.*;
import com.ontoeval.model.Access.*;
import com.ontoeval.model.Access.Implement.OntologyImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private final InstructionsHelper instructions;
    private final ResultsHelper results;
    private final TaxonomicHelper taxonomic;

    public OntologyHelper (HttpServletRequest request) throws SQLException, IOException {
        this.request = request;
        lexical = new LexicalHelper(request);
        taxonomic = new TaxonomicHelper(request);
        results = new ResultsHelper(request);
        instructions = new InstructionsHelper(request);
        ontology = new OntologyImpl(OntologyImpl.CrearConexion());
    }

    public boolean loadOntologies(String name){
        HttpSession context = request.getSession();
        if(name!=null){
            ArrayList<OntologyVO> onts = ontology.getOntologiesByUser(((UserVO)context.getAttribute("user")).getEmail());
            if (onts == null || onts.size() == 0) {
                return false;
            } else {
                context.setAttribute("userOnts", onts);
                return true;
            }
        }
        else {
            ArrayList<OntologyVO> onts = ontology.recuperarOntologias();
            if (onts == null || onts.size() == 0) {
                return false;
            } else {
                context.setAttribute("ontos", onts);
                return true;
            }
        }
    }

    public String loadFeatures(String name){
        HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        OntologyVO o = ontology.recuperarOntologias(name);
        session.setAttribute("instructions",instructions.getInstructions(o));
        String page="";
        if(o.getState().equals("Eval lexical layer"))
            page=lexical.comprobarLexical(o,user);
        if(o.getState().equals("Eval Taxonomic Layer")) {
            if(lexical.checkUser(o,user)){
                taxonomic.createGSRelations(lexical.recuperarGS(o), o,user.getEmail());
                page = taxonomic.comprobarTaxonomic(o, user);
            }
            else {
                page = "notUser";
            }
        }
        if(o.getState().equals("Results")) {
            results.setTerms(lexical.recuperar(o.getName()));
            results.setRelations(taxonomic.recuperar(o.getName()));
            page=results.calcularResultados(o);
            o.setState("See Results");

        }
        if(o.getState().equals("See Results")){
            results.insertMeasures(o,null);
            session.setAttribute("sterms",lexical.recuperar(o.getName()).size());
            session.setAttribute("value",lexical.recuperar(o.getName()).size());
            page="./eval/results.jsp";
        }
        ontology.updateOntology(o);
        session.setAttribute("ontology",o);
        return page;

    }

    public boolean insertOntology(String text, String filename){
        String domain = loadDomain(text);
        UserVO u = (UserVO)request.getSession().getAttribute("user");
        OntologyVO o = new OntologyVO(filename, domain,u);
        if(!ontology.insertOntology(o) ||
                !lexical.loadTerms(text,o) ||
                    !taxonomic.loadRelations(text,o)){
            return false;
        }
        else{
             request.getSession().setAttribute("ontology",o);
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
