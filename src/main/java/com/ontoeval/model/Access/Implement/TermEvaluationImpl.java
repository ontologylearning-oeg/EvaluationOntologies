package com.ontoeval.model.Access.Implement;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.Access.TermEvaluationDAO;
import com.ontoeval.model.TermEvaluationVO;

import java.io.IOException;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dchavesf on 2/09/16.
 */
public class TermEvaluationImpl extends BaseDaoImpl<TermEvaluationVO, Integer> implements TermEvaluationDAO {
    private static final String url = "jdbc:mysql://localhost/DrOntoEval?useSSL=false";
    private final Dao<TermEvaluationVO, Integer> termEvalDAO;
    private ConnectionSource connectionSource;

    public TermEvaluationImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, TermEvaluationVO.class);
        this.connectionSource=connectionSource;
        termEvalDAO = DaoManager.createDao(connectionSource, TermEvaluationVO.class);
        TableUtils.createTableIfNotExists(connectionSource, TermEvaluationVO.class);
    }

    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url,"root","root");
        return connectionSource;
    }


    public ArrayList<TermEvaluationVO> evaluatedTermsUser(String ontology, String user){
        HashMap<String, Object> m = new HashMap<>();
        m.put("Ontology",ontology);
        m.put("User", user);
        try{
            return (ArrayList<TermEvaluationVO>)termEvalDAO.queryForFieldValuesArgs(m);
        }catch (SQLException e){
            System.out.println("Error en evaluatedTermsUser/TermEvaluationImpl "+e.getMessage());
            return new ArrayList<>();
        }
    }

    public ArrayList<TermEvaluationVO> evaluatedTerms(String ontology){
        try{
            return (ArrayList<TermEvaluationVO>)termEvalDAO.queryForEq("Ontology", ontology);
        }catch (SQLException e){
            System.out.println("Error en evaluatedTerms/TermEvaluationImpl "+e.getMessage());
            return new ArrayList<>();
        }

    }

    public boolean insertTerms(ArrayList<TermEvaluationVO> t) {
        for(TermEvaluationVO v : t){
            try {
                if (termEvalDAO.create(v) == 0) {
                    return false;
                }
            }catch (SQLException e){
                System.out.println("Error en insertTerms/TermEvaluationImpl "+e.getMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    public void deleteTerms(String user) {
        try{
            termEvalDAO.executeRawNoArgs("delete from LexicalEvaluation where User='"+user+"';");
        }catch (SQLException e){
            System.out.println("Error en updateTerms/TermImpl "+e.getMessage());
        }
    }

    public void close(){
        try {
            connectionSource.close();
        } catch (SQLException e) {
            System.out.println("Error en close "+e.getMessage());
        }
    }

}
