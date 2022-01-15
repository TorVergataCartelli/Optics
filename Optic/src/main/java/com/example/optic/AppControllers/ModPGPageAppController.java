package com.example.optic.AppControllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.*;
import com.example.optic.entities.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class ModPGPageAppController {

    public static Admin getAdmin(AdminBean a){
        Admin admin = null;
        try {
            AdminDAO dao = AdminDAO.getInstance();
            admin = dao.getAdmin(a.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }

    public static void setAdminSocial(AdminBean a){
        String admin = a.getUsername();
        String fb = a.getFb();
        String ig = a.getIg();
        String wa = a.getWa();
        try{
            AdminDAO dao = AdminDAO.getInstance();
            dao.setAdminSocial(admin,ig,fb,wa);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setDescription(AdminBean a){
        try{
            AdminDAO dao = AdminDAO.getInstance();
            String admin = a.getUsername();
            String desc = a.getDescrizione();
            dao.setDescription(admin,desc);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //cerco arbitro tramite admin username
    public static Referee getRefereeFromAdmin(UserBean u){
        Referee ref = null;
        try {
            AdminDAO dao = AdminDAO.getInstance();
            ref = dao.getRefereeFromAdmin(u.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ref;
    }

    public static Giornata getFirstPlay(UserBean bean){
        Giornata play = null;
        try{
            AdminDAO dao = AdminDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getFirstPlay(bean.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return play;
    }

    public static Giornata getNextPlay(GiornataBean bean){
        Giornata play = null;
        try{
            AdminDAO dao = AdminDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getNextPlay(bean.getAdmin(),bean.getData());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  play;
    }

    public static Giornata getLastPlay(GiornataBean bean){
        Giornata play = null;
        try{
            AdminDAO dao = AdminDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getLastPlay(bean.getAdmin(),bean.getData());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  play;
    }

    //cerco arbitro tramite il suo nome
    public static Referee getReferee(UserBean u){
        Referee ref = null;
        try {
            AdminDAO dao = AdminDAO.getInstance();
            ref = dao.getReferee(u.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ref;
    }

    public static ArrayList<Valutazione> getReviewList(AdminBean a) throws IOException {
        ArrayList<Valutazione> list = new ArrayList<Valutazione>();
        //utilizzo la dao dell'admin dove è creata la connessisone
        try {
            AdminDAO daoA = AdminDAO.getInstance();
            ValutazioneDAO dao = new ValutazioneDAO(daoA);
            list = dao.getAdminReviewList(a.getUsername());
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Player> getPlayersList(GiornataBean bean) throws IOException{
        ArrayList<Player> list = new ArrayList<Player>();
        try{
            AdminDAO dao = AdminDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            list = playDao.getPlayersList(bean.getIdPlay());
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Event> getEventList() throws IOException{
        ArrayList<Event> list = new ArrayList<Event>();
        try{
            AdminDAO dao = AdminDAO.getInstance();
            EventDAO eventDao = new EventDAO(dao);
            list = eventDao.getAdminEventList();
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static boolean isDateValid(GiornataBean bean){
        boolean res = true;
        try{
            AdminDAO dao = AdminDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            String admin = bean.getAdmin();
            Calendar cal = bean.getData();
            res = playDao.isDateValid(admin,cal);
        }catch (IOException e){
            e.printStackTrace();
        }
        return res;
    }


    public static void insertPlay(GiornataBean bean){
        try{
            AdminDAO dao = AdminDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            String event = bean.getEvento();
            String admin = bean.getAdmin();
            Calendar cal = bean.getData();
            playDao.insertPlay(admin,event,cal);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //setto all'admin (bean) il corrispettivo arbitro (bean2)
    public static void setReferee(UserBean admin, UserBean referee) {
        try{
            AdminDAO dao = AdminDAO.getInstance();
            dao.setReferee(admin.getUsername(),referee.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void freeReferee(UserBean u) {
        try{
            AdminDAO dao = AdminDAO.getInstance();
            dao.freeReferee(u.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void closeConn() throws IOException {
        try {
            AdminDAO dao = AdminDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            System.out.println("Errore chiusura connessione con il database");
            e.printStackTrace();
        }
    }
}
