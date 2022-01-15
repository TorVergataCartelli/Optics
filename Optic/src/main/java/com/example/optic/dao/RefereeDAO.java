package com.example.optic.dao;

import com.example.optic.bean.ReportBean;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Referee;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class RefereeDAO {
    private static String USER;
    private static String PW;
    private static String DB_URL;
    private static String DRIVER_CLASS_NAME;

    private static RefereeDAO instance = null;
    private Connection conn;

    protected RefereeDAO() throws IOException {
        this.conn = null;
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("prop.properties");
            Properties prop = new Properties();
            prop.load(input);
            this.USER = prop.getProperty("USER");
            this.PW = prop.getProperty("PW");
            this.DB_URL = prop.getProperty("DB_URL");
            this.DRIVER_CLASS_NAME = prop.getProperty("DRIVER_CLASS_NAME");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void newReferee(String username,String password) throws Exception {
        Statement stmt = null;
        Referee r = new Referee(username, password);
        try {
            stmt = instance.conn.createStatement();
            String sql = "INSERT INTO referee VALUES(?,?,?)";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql);
            prepStmt.setString(1, r.getUsername());
            prepStmt.setString(2, r.getPassword());
            prepStmt.setNull(3, Types.NULL);
            prepStmt.executeUpdate();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Admin getAdminFromRef(String refUsername) throws SQLException {
        Statement stmt = null;
        Admin a = new Admin();
        try{
            stmt = instance.conn.createStatement();
            String sql = "SELECT A.Username,A.Instagram,A.Facebook,A.Whatsapp,A.NomeC,A.DescrizioneC,A.Via FROM (referee R JOIN admin A ON R.fk_UsernameA1 = A.Username) WHERE R.Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,refUsername);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.first()){ // trovato admin
                rs.first();
                a.setUsername(rs.getString("A.Username"));
                a.setIg(rs.getString("A.Instagram"));
                a.setFb(rs.getString("A.Facebook"));
                a.setWa(rs.getString("A.Whatsapp"));
                a.setNomeC(rs.getString("A.NomeC"));
                a.setDescrizioneC(rs.getString("A.DescrizioneC"));
                a.setVia(rs.getString("A.Via"));
                //chiudo result set
                rs.close();
            }else{
                a = null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return a;
    }

    //chiede l aggiunta di una catch clause
    public static Referee getReferee(String user)throws Exception{
        Statement stmt = null;
        Referee ref = new Referee(user,"");
        try{
            if(instance.conn == null || instance.conn.isClosed()) {
                instance.getConn();
            }
            stmt = instance.conn.createStatement();
            String sql = "SELECT * FROM referee WHERE Username=?";
            PreparedStatement prepStmt = instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if (!rs.first()){ // rs empty
               ref=null;
            }else {
                //il player deve presente in quanto la label da dove prendo il nome non può essere vuota
                rs.first();
                ref.setUsername(rs.getString("Username"));
                ref.setPassword(rs.getString("Password")); //probabilmente non servirà
                ref.setAdminCampo(rs.getString("fk_UsernameA1"));
                //chiudo result set
                rs.close();
            }
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ref;
    }

    public static RefereeDAO getInstance() throws IOException {
        if(RefereeDAO.instance == null){
            RefereeDAO.instance = new RefereeDAO();
        }
        return instance;
    }

    public void saveReport(ReportBean report) throws SQLException {
        String sql="Insert INTO Report values(null,?,?,?)";
        PreparedStatement prepstmt=instance.conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        System.out.println("player "+report.getPlayer()+" referee "+report.getReferee());
        prepstmt.setString(1,report.getMotivazione());
        prepstmt.setString(2,report.getReferee());
        prepstmt.setString(3,report.getPlayer());
        prepstmt.executeUpdate();
    }

    public void getConn(){
        try{
            Class.forName(DRIVER_CLASS_NAME);
            instance.conn = DriverManager.getConnection(DB_URL, USER, PW);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return this.conn;
    }

    public void closeConn(){
        try{
            if (instance.conn != null)
                instance.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
