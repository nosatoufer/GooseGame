package heb.esi.goosegame.db;

/**
 * Enumeration des connexions utilisables
 *
 * @author HONEY
 */
public enum DBInfo {
    /**
     *
     */
    JAVADB("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/GooseGameDB","root", "rootroot" /*"app", "app"*/);

    private final String driver, url, uid, psw;
    
    DBInfo(String driver, String url, String uid, String psw) {
        this.driver = driver;
        this.uid = uid;
        this.url = url;
        this.psw = psw;
    }

    /**
     *
     * @return
     */
    public String getDriver() {
        return driver;
    }

    /**
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return
     */
    public String getUid() {
        return uid;
    }

    /**
     *
     * @return
     */
    public String getPsw() {
        return psw;
    }

}
