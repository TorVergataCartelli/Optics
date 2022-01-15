package com.example.optic.dao;

import com.example.optic.bean.ValutazioneBean;
import com.example.optic.entities.Valutazione;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ValutazioneDAO {
    private static PlayerDAO daoP;
    private static AdminDAO daoA;
    //private static RefereeDAO daoR;

    public ValutazioneDAO(PlayerDAO daoP){
        this.daoP = daoP;
        this.daoA = null;
    }

    public ValutazioneDAO(AdminDAO daoA){
        this.daoA = daoA;
        this.daoP = null;
    }
    /*
    public ValutazioneDAO(RefreeDAO daoR){
        this.daoR = daoR;
    }
    */


    public ArrayList<Valutazione> getPlayerReviewList(String user){
        ArrayList<Valutazione> list = new ArrayList<Valutazione>();
        Statement stmt = null;
        String descrizione;
        String recensore;
        String recensito;
        int stelle;
        try{
            stmt = this.daoP.getConnection().createStatement();
            String sql = "SELECT * FROM valutazione WHERE fk_UsernameP2 =?";
            PreparedStatement prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.first()){
                rs.first();
                do{
                    descrizione = rs.getString("Descrizione");
                    recensore = rs.getString("fk_UsernameP1");
                    recensito = rs.getString("fk_UsernameP2");
                    stelle = rs.getInt("Stelle");
                    Valutazione val = new Valutazione(descrizione,recensore,recensito,stelle);
                    list.add(val);
                }while(rs.next());
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
        return list;
    }

    public ArrayList<Valutazione> getAdminReviewList1(String user){
        String sql= "SELECT fk_UsernameA from valutazione join admin on fk_UsernameA=Username WHERE NomeC=?";
        String usernameA = null;
        try {
            PreparedStatement prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.first()){
                rs.first();
                usernameA = rs.getString("fk_UsernameA");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getAdminReviewList(usernameA);
    }

    public ArrayList<Valutazione> getAdminReviewList(String user){
        ArrayList<Valutazione> list = new ArrayList<Valutazione>();
        Statement stmt = null;
        try{
            String sql = "SELECT * FROM valutazione WHERE fk_UsernameA =?";
            PreparedStatement prepStmt=null;
            if (daoA==null){
                stmt = this.daoP.getConnection().createStatement();
                prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            }else {
                stmt = this.daoA.getConnection().createStatement();
                prepStmt = this.daoA.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            }
            prepStmt.setString(1,user);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.first()){
                rs.first();
                do{
                    String descrizione = rs.getString("Descrizione");
                    String recensore = rs.getString("fk_UsernameP1");
                    String recensito = rs.getString("fk_UsernameA");
                    int stelle = rs.getInt("Stelle");
                    Valutazione val = new Valutazione(descrizione,recensore,recensito,stelle);
                    list.add(val);
                }while(rs.next());
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
        return list;
    }

    public void saveReview(ValutazioneBean val,int campo_player){
        Statement stmt;
        PreparedStatement prepStmt;
        PreparedStatement prepStmt2;
        ResultSet rs = null;
        String sql1;
        try {
            if(campo_player==0) {
                stmt = this.daoP.getConnection().createStatement();
                String sql = "SELECT Username from ADMIN where NomeC=?";
                prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                prepStmt.setString(1, val.getRiceve());
                rs = prepStmt.executeQuery();
                rs.first();
                //in teoria impossibile che non trovo admin
                sql1 = "INSERT into valutazione (Descrizione,Stelle,fk_UsernameP1,fk_UsernameA) values (?,?,?,?)";
            }else{
                sql1 = "INSERT into valutazione (Descrizione,Stelle,fk_UsernameP1,fk_UsernameP2) values (?,?,?,?)";
            }
            stmt = this.daoP.getConnection().createStatement();

            prepStmt = this.daoP.getConnection().prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            prepStmt.setString(1,val.getRecensione());
            prepStmt.setInt(2,val.getStelle());
            prepStmt.setString(3,val.getUsernameP1());
            //Campo inteso come username admin
            if(campo_player==0) {
                prepStmt.setString(4, rs.getString("Username"));
            }else{
                prepStmt.setString(4, val.getRiceve());

            }
            prepStmt.executeUpdate();
            if(campo_player == 1){
                String sql3 = "update player set Valutazione = (select avg(stelle) as val from valutazione where fk_UsernameP2 = ?) where Username = ?";
                prepStmt2 = this.daoP.getConnection().prepareStatement(sql3);
                prepStmt2.setString(1, val.getRiceve());
                prepStmt2.setString(2, val.getRiceve());
                prepStmt2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getValutazione(ValutazioneBean val,int campo_player) {
        //restituisce una valutazione dato player e campo
        boolean esiste=false;
        Statement stmt;
        try {
            //get usernameAdmin
            PreparedStatement prepStmt;
            ResultSet rs = null;
            String sql1;
            if(campo_player==0) {
                stmt = this.daoP.getConnection().createStatement();
                String sql = "SELECT Username from ADMIN where NomeC=?";
                prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                prepStmt.setString(1, val.getRiceve());
                rs = prepStmt.executeQuery();
                rs.first();
                sql1 = "SELECT * FROM valutazione WHERE fk_UsernameA=? AND fk_UsernameP1=?";
            }else{
                sql1 = "SELECT * FROM valutazione WHERE fk_UsernameP2=? AND fk_UsernameP1=?";
            }

            stmt = this.daoP.getConnection().createStatement();

            prepStmt = this.daoP.getConnection().prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(2,val.getUsernameP1());
            if(campo_player==0) {
                prepStmt.setString(1, rs.getString("Username"));
            }else{
                prepStmt.setString(1, val.getRiceve());
            }
            ResultSet rs1 = prepStmt.executeQuery();
            if(rs1.first()){
                esiste=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esiste;
    }


    public void deleteValutazione(ValutazioneBean val,int campo_player) {
        //restituisce una valutazione dato player e campo
        boolean esiste = false;
        Statement stmt;
        PreparedStatement prepStmt;
        ResultSet rs = null;
        String sql1;
        try {
            //get usernameAdmin
            if (campo_player==0) {
                stmt = this.daoP.getConnection().createStatement();
                String sql = "SELECT Username from ADMIN where NomeC=?";
                prepStmt = this.daoP.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                prepStmt.setString(1, val.getRiceve());
                rs = prepStmt.executeQuery();
                rs.first();
                sql1 = "DELETE from Valutazione where fk_UsernameP1=? AND fk_UsernameA=?";
            }else{
                sql1 = "DELETE from Valutazione where fk_UsernameP1=? AND fk_UsernameP2=?";
            }
            prepStmt = this.daoP.getConnection().prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (campo_player==0) {
                prepStmt.setString(2, rs.getString("Username"));
            }else{
                prepStmt.setString(2, val.getRiceve());
            }
            prepStmt.setString(1, val.getUsernameP1());
            prepStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
