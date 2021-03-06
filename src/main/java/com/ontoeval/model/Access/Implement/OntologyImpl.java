package com.ontoeval.model.Access.Implement;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.Access.OntologyDAO;
import com.ontoeval.model.OntologyVO;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by dchavesf on 1/09/16.
 */
public class OntologyImpl extends BaseDaoImpl<OntologyVO, Integer> implements OntologyDAO {
    private final Dao<OntologyVO, Integer> ontoDAO;
    private ConnectionSource connectionSource;

    public OntologyImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, OntologyVO.class);
        this.connectionSource=connectionSource;
        ontoDAO = DaoManager.createDao(connectionSource, OntologyVO.class);
        TableUtils.createTableIfNotExists(connectionSource, OntologyVO.class);
    }

    public boolean insertOntology(OntologyVO ontology){
        try{
            if(ontoDAO.create(ontology)==0){
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error en OntologyImpl, insert "+ex.getMessage());
            return false;
        }
        return true;
    }

    public ArrayList<OntologyVO> recuperarOntologias(){
        try{
            return (ArrayList<OntologyVO>) ontoDAO.queryForAll();
        }catch (SQLException e){
            System.out.println("Error en OntologyImpl, recuperar2 "+ e.getMessage());
            return null;
        }
    }

    public OntologyVO recuperarOntologias(String name){
        try{
            return ontoDAO.queryForEq("Name",name).get(0);
        }catch (SQLException e){
            System.out.println("Error en OntologyImpl, recuperar1 "+ e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<OntologyVO> getOntologiesByUser(String name) {
        try{
            return (ArrayList<OntologyVO>)ontoDAO.queryForEq("User",name);
        }catch (SQLException e){
            System.out.println("Error en OntologyImpl, ontobyuser"+ e.getMessage());
            return null;
        }
    }

    @Override
    public void updateOntology(OntologyVO o) {
        try{
            ontoDAO.delete(o);
            insertOntology(o);
        }catch (SQLException e){
            System.out.println("Error en OntologyImpl, updateOntology "+ e.getMessage());
        }
    }

    @Override
    public boolean removeOntology(String name) {
       try{
           ontoDAO.executeRaw("delete from Ontologies where Name='"+name+"';");
           ontoDAO.executeRaw("delete from Terms where Ontology='"+name+"';");
           ontoDAO.executeRaw("delete from Relations where Ontology='"+name+"';");
           ontoDAO.executeRaw("delete from Measure where Ontology='"+name+"';");
           ontoDAO.executeRaw("delete from LexicalEvaluation where Ontology='"+name+"';");
           ontoDAO.executeRaw("delete from TaxonomicEvaluation where Ontology='"+name+"';");
           ontoDAO.executeRaw("delete from UserEval where Ontology='"+name+"';");
           ontoDAO.executeRaw("delete from Instructions where Ontology='"+name+"';");
           return true;
       }catch (SQLException e){
           System.out.println("Error en OntologyImpl, removeOntology "+ e.getMessage());
           return false;
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
