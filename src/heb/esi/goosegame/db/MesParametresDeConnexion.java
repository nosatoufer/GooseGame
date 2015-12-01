/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.db;

/**
 * Enumeration des connexions utilisables
 *
 * @author HONEY
 */
public enum MesParametresDeConnexion {

    ORACLE("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@oracle9:1521:esidb", "???", "????"),
    ACCESS("sun.jdbc.odbc.JdbcOdbcDriver", "jdbc:odbc:anagramme", "", ""),
    MYSQL("", "", "", ""),
    JAVADB("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527//GoosegameDB","root", "rootroot" /*"app", "app"*/);

    MesParametresDeConnexion(String driver, String url, String uid, String psw) {
        this.driver = driver;
        this.uid = uid;
        this.url = url;
        this.psw = psw;
    }
    private String driver, url, uid, psw;

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUid() {
        return uid;
    }

    public String getPsw() {
        return psw;
    }

}
