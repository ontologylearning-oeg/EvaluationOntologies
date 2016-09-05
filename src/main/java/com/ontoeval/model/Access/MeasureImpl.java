package com.ontoeval.model.Access;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ontoeval.model.MeasureVO;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dchavesf on 5/09/16.
 */
public class MeasureImpl extends BaseDaoImpl<MeasureVO, Integer> implements MeasureDAO {
    private static final String url = "jdbc:mysql://localhost/DrOntoEval";
    private final Dao<MeasureVO, Integer> measureDAO;


    public MeasureImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, MeasureVO.class);
        measureDAO = DaoManager.createDao(connectionSource, MeasureVO.class);
        TableUtils.createTableIfNotExists(connectionSource, MeasureVO.class);
    }

    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url,"root","root");
        return connectionSource;
    }


}