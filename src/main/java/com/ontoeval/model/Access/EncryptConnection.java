package com.ontoeval.model.Access;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by dchaves on 11/10/16.
 */
public class EncryptConnection {


    public static ConnectionSource CrearConexion() throws SQLException, IOException {
        String envUser = System.getenv("EVAL_USER");
        String envPwd = System.getenv("EVAL_PWD");
        String envHost = System.getenv("EVAL_DB");

        //mysql://localhost/DrOntoEval?useSSL=false
        String jdbcUrl = "mysql://"+envHost+"/DrOntoEval?useSSL=false";

        ConnectionSource connectionSource = new JdbcConnectionSource(jdbcUrl,envUser,envPwd);
        return connectionSource;
    }
}
