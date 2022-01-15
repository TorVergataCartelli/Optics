package com.example.optic.dao;

import com.example.optic.entities.Valutazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PrenotazioneDAO {
    private static PlayerDAO daoP;
    private static AdminDAO daoA;
    //private static RefereeDAO daoR;

    public PrenotazioneDAO(PlayerDAO daoP){
        this.daoP = daoP;
    }

    public PrenotazioneDAO(AdminDAO daoA){
        this.daoA = daoA;
    }
    /*
    public ValutazioneDAO(RefreeDAO daoR){
        this.daoR = daoR;
    }
    */

    public boolean isPlayerBooked(String player, int idPlay){
        boolean res = true;
        Statement stmt = null;
        try{
            stmt = this.daoP.getConnection().createStatement();
            String sql = "SELECT * FROM prenotazione WHERE fk_Username =? AND fk_idGiornata=?";
            PreparedStatement prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,player);
            prepStmt.setInt(2,idPlay);
            ResultSet rs = prepStmt.executeQuery();
            if(!(rs.first())){
                res = false;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public void bookPlay(String player, int idPlay){
        Statement stmt = null;
        try{
            stmt = this.daoP.getConnection().createStatement();
            String sql = "INSERT INTO prenotazione VALUES(?,?)";
            PreparedStatement prepStmt = this.daoP.getConnection().prepareStatement(sql);
            prepStmt.setString(1,player);
            prepStmt.setInt(2,idPlay);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
