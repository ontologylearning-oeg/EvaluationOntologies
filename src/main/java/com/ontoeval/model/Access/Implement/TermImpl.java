package com.ontoeval.model.Access.Implement;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.Access.TermDAO;
import com.ontoeval.model.TermVO;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by dachafra on 30/06/16.
 */
public class TermImpl extends BaseDaoImpl<TermVO, Integer> implements TermDAO {
    private final Dao<TermVO, Integer> termDAO;
    private ConnectionSource connectionSource;

    public TermImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, TermVO.class);
        this.connectionSource=connectionSource;
        termDAO = DaoManager.createDao(connectionSource, TermVO.class);
        TableUtils.createTableIfNotExists(connectionSource, TermVO.class);
    }

    public boolean InsertTerm(TermVO t){
        try{
            if(termDAO.create(t)==0) {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Error en importarFichero "+ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean InsertTerms(ArrayList<TermVO> terms){
        for(TermVO term : terms){
            if(this.InsertTerm(term) == false){
                return false;
            }
        }

        return true;
    }

    public ArrayList<TermVO> loadTerms(String ontology){

        try{
            return (ArrayList<TermVO>)termDAO.queryForEq("Ontology",ontology);
        }catch (SQLException e){
            System.out.println("Error en loadTerms/TermImpl "+e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<TermVO> loadNormal(String ontology) {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Ontology",ontology);
        m.put("isControl",false);
        try{
            return (ArrayList<TermVO>)termDAO.queryForFieldValuesArgs(m);
        }catch (SQLException e){
            System.out.println("Error en loadNormal/TermImpl "+e.getMessage());
            return null;
        }
    }

    public boolean updateTerms(ArrayList<TermVO> terms) {
        try{
            termDAO.executeRawNoArgs("delete from Terms;");
            for(TermVO term : terms){
               if(termDAO.create(term)== 0){
                    return false;
                }
            }
        }catch (SQLException e){
            System.out.println("Error en updateTerms/TermImpl "+e.getMessage());
        }

        return true;
    }

    public ArrayList<TermVO> loadGS(String ontology) {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Ontology",ontology);
        m.put("isGS", true);
        m.put("isControl",false);
        try{
            return (ArrayList<TermVO>)termDAO.queryForFieldValuesArgs(m);
        }catch (SQLException e){
            System.out.println("Error en loadGS/TermImpl "+e.getMessage());
            return null;
        }
    }

    @Override
    public ArrayList<TermVO> loadControl(String ontology) {
        HashMap<String, Object> m = new HashMap<>();
        m.put("Ontology",ontology);
        m.put("isControl",true);
        try{
            return (ArrayList<TermVO>)termDAO.queryForFieldValuesArgs(m);
        }catch (SQLException e){
            System.out.println("Error en loadControl/TermImpl "+e.getMessage());
            return null;
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
