package com.ontoeval.model.Access;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.TermEvaluationVO;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchavesf on 2/09/16.
 */
public class TermEvaluationImpl extends BaseDaoImpl<TermEvaluationVO, Integer> implements TermEvaluationDAO {
    private static final String url = "jdbc:mysql://localhost/DrOntoEval";
    private final Dao<TermEvaluationVO, Integer> termEvalDAO;

    public TermEvaluationImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, TermEvaluationVO.class);
        termEvalDAO = DaoManager.createDao(connectionSource, TermEvaluationVO.class);
        TableUtils.createTableIfNotExists(connectionSource, TermEvaluationVO.class);
    }

    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url,"root","root");
        return connectionSource;
    }


}