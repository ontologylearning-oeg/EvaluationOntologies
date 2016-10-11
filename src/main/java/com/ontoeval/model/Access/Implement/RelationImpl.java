package com.ontoeval.model.Access.Implement;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.Access.RelationDAO;
import com.ontoeval.model.RelationVO;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by dchavesf on 1/09/16.
 */
public class RelationImpl extends BaseDaoImpl<RelationVO, Integer> implements RelationDAO {
    private final Dao<RelationVO, Integer> relationDAO;
    private ConnectionSource connectionSource;

    public RelationImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, RelationVO.class);
        this.connectionSource=connectionSource;
        relationDAO = DaoManager.createDao(connectionSource, RelationVO.class);
        TableUtils.createTableIfNotExists(connectionSource, RelationVO.class);
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
            relationDAO.executeRawNoArgs("delete from Relations where Ontology='" + r.getOntology().getName() + "' and term1='" + r.getTerm1() + "' and term2='" + r.getTerm2() + "' and isRandom=1;");
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

    public ArrayList<RelationVO> getRandomRelations(String ontology) {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Ontology",ontology);
        m.put("isRandom", true);
        try{
            return (ArrayList<RelationVO>)relationDAO.queryForFieldValuesArgs(m);
        }catch (SQLException e){
            System.out.println("Error en getRandomrelations/RelationImpl "+e.getMessage());
            return null;
        }
    }

    public ArrayList<RelationVO> getRelations(String ontology) {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Ontology",ontology);
        try{
            return (ArrayList<RelationVO>)relationDAO.queryForFieldValuesArgs(m);
        }catch (SQLException e){
            System.out.println("Error en getRelations/RelationImpl "+e.getMessage());
            return null;
        }
    }

    public boolean updateRelations(ArrayList<RelationVO> r) {
        boolean flag=true;
        for(RelationVO relation : r){
            if(relation.isRandom())
                flag=this.insertRandomRelation(relation);
            }
        return flag;
    }

    public void close(){
        try {
            connectionSource.close();
        } catch (SQLException e) {
            System.out.println("Error en close "+e.getMessage());
        }
    }


}
