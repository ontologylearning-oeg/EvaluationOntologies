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
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("drinventor");
        Properties props = new EncryptableProperties(encryptor);
        props.load(new FileInputStream("file"));
        String datasourceUsername = props.getProperty("datasource.username");
        String datasourcePassword = props.getProperty("datasource.password");
        String url = props.getProperty("datasource.url");

        ConnectionSource connectionSource = new JdbcConnectionSource(url,datasourceUsername,datasourcePassword);
        return connectionSource;
    }
}
