package com.ontoeval.model.Access;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.RelationVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dchavesf on 1/09/16.
 */
public class RelationImpl extends BaseDaoImpl<RelationVO, Integer> implements RelationDAO {
    private static final String url = "jdbc:mysql://localhost/DrOntoEval?autoReconnect=true&useSSL=false";
    private final Dao<RelationVO, Integer> relationDAO;


    public RelationImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, RelationVO.class);
        relationDAO = DaoManager.createDao(connectionSource, RelationVO.class);
        TableUtils.createTableIfNotExists(connectionSource, RelationVO.class);
    }

    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url,"root","root");
        return connectionSource;
    }

    public boolean insertRelation(RelationVO r){
        try{
            if(relationDAO.create(r)==0){
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error en importar relaciones "+ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean insertRandomRelation(RelationVO r) {
        try {
            relationDAO.executeRawNoArgs("delete from Relations where Ontology='" + r.getName() + "' and term1='" + r.getTerm1() + "' and term2='" + r.getTerm2() + "' and isRandom=1;");
            if(relationDAO.create(r)==0)
                return false;
        }catch (SQLException e){
            System.out.println("Error en insertRadomRelation/RelationImpl "+e.getMessage());
            return false;
        }
        return true;
    }

    public boolean insertRelations(ArrayList<RelationVO> rs){
        for(RelationVO relation : rs){
            if(this.insertRelation(relation) == false){
                return false;
            }
        }
        return true;
    }

    public boolean checkRandomRelations() {
        try{
            if(relationDAO.queryForEq("isRandom",true).size()==0){
                return true;
            }
            else
                return false;
        }catch (SQLException e){
            System.out.println("Error en getRandomRelations/RelationImpl "+e.getMessage());
            return false;
        }
    }

    public ArrayList<RelationVO> getRandomRelations(String ontology) {
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("Ontology",ontology);
        m.put("isRandom", true);
        try{
            return (ArrayList<RelationVO>)relationDAO.queryForFieldValuesArgs(m);
        }catch (SQLException e){
            System.out.println("Error en evaluatedTermsUser/TermEvaluationImpl "+e.getMessage());
            return null;
        }
    }

    public ArrayList<RelationVO> getRelations(String ontology) {
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("Ontology",ontology);
        try{
            return (ArrayList<RelationVO>)relationDAO.queryForFieldValuesArgs(m);
        }catch (SQLException e){
            System.out.println("Error en evaluatedTermsUser/TermEvaluationImpl "+e.getMessage());
            return null;
        }
    }

    public boolean updateRelations(ArrayList<RelationVO> r) {
        try{
            for(RelationVO relation : r){
                if(relationDAO.update(relation) == 0){
                    return false;
                }
            }
        }catch (SQLException e){
            System.out.println("Error en updateRelations/TermEvaluationImpl "+e.getMessage());
            return false;
        }
        return true;
    }


}
