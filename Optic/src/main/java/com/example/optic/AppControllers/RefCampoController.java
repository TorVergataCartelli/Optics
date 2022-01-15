package com.example.optic.AppControllers;

import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.ReportBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.*;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Event;
import com.example.optic.entities.Giornata;
import com.example.optic.entities.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RefCampoController {

    public static void saveReport(ReportBean report) throws IOException {
        RefereeDAO dao=RefereeDAO.getInstance();
        dao.getConn();
        try {
            dao.saveReport(report);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Admin getAdminFromRef(UserBean user) throws IOException {
        Admin a = null;
        try {
            RefereeDAO dao = RefereeDAO.getInstance();
            dao.getConn();
            a = dao.getAdminFromRef(user.getUsername());
        }catch (IOException | SQLException e){
            e.printStackTrace();
        }
        return a;
    }

    public static ArrayList<Event> getEventList() throws IOException{
        ArrayList<Event> list = new ArrayList<Event>();
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
            EventDAO eventDao = new EventDAO(dao);
            list = eventDao.getRefereeEventList();
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static Giornata getFirstPlay(UserBean bean){
        Giornata play = null;
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
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
            RefereeDAO dao = RefereeDAO.getInstance();
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
            RefereeDAO dao = RefereeDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            play = playDao.getLastPlay(bean.getAdmin(),bean.getData());
        }catch (Exception e){
            e.printStackTrace();
        }
        return  play;
    }

    public static ArrayList<Player> getPlayersList(GiornataBean bean) throws IOException{
        ArrayList<Player> list = new ArrayList<Player>();
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            list = playDao.getPlayersList(bean.getIdPlay());
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static void closeConn() throws IOException {
        try {
            RefereeDAO dao = RefereeDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            System.out.println("Errore chiusura connessione con il database");
            e.printStackTrace();
        }
    }
}
