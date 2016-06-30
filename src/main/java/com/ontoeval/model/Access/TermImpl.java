package com.ontoeval.model.Access;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.ModelVO;
import com.ontoeval.model.TermVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by dachafra on 30/06/16.
 */
public class TermImpl extends BaseDaoImpl<TermVO, Integer> implements TermDAO {
    private static final String url = "jdbc:mysql://localhost/DrOntoEval";
    private final Dao<TermVO, Integer> termDAO;


    public TermImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, TermVO.class);
        termDAO = DaoManager.createDao(connectionSource, TermVO.class);
        TableUtils.createTableIfNotExists(connectionSource, TermVO.class);
    }

    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url,"root","root");
        return connectionSource;
    }


    public boolean InsertTerm(TermVO t){
        try{
            if(termDAO.create(t)==0){
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

    public ArrayList<TermVO> buscarTerms(ModelVO m){
        try {
            return (ArrayList<TermVO>)termDAO.queryForAll();
        } catch (SQLException ex) {
            System.out.println("Error en buscarFicheros "+ex.getMessage());
            return null;
        }
    }
}
