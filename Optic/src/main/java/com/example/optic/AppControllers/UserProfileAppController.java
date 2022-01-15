package com.example.optic.AppControllers;

import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.ReportBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.*;
import com.example.optic.entities.Event;
import com.example.optic.entities.Player;
import com.example.optic.entities.Valutazione;
import com.example.optic.entities.Giornata;

import java.io.IOException;
import java.util.ArrayList;

public class UserProfileAppController {

    public static Player getPlayer(PlayerBean p){
        Player player = null;
        try {
            PlayerDAO dao = PlayerDAO.getInstance();
            player = dao.getPlayer(p.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }

    public static ArrayList<Valutazione> getReviewList(PlayerBean p) throws IOException {
        ArrayList<Valutazione> list = new ArrayList<Valutazione>();
        //utilizzo la dao del player dove è creata la connessisone
        try {
            PlayerDAO daoP = PlayerDAO.getInstance();
            ValutazioneDAO dao = new ValutazioneDAO(daoP);
            list = dao.getPlayerReviewList(p.getUsername());
        }catch (IOException e){
           e.printStackTrace();
        }
        return list;
    }

    public static void setInfo(PlayerBean p){
        try {
            PlayerDAO player = PlayerDAO.getInstance();
            player.setPlayerInfo(p.getUsername(),p.getDescrizione(),p.getFb(),p.getIg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveReview(ValutazioneBean val){
        PlayerDAO playerDAO= null;
        try {
            playerDAO = PlayerDAO.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ValutazioneDAO dao = new ValutazioneDAO(playerDAO);
        if(dao.getValutazione(val,1)){
            dao.deleteValutazione(val,1);
        }
        dao.saveReview(val,1);
    }
    public static ArrayList<Giornata> getRecentPlayList(UserBean user){
        ArrayList<Giornata> list = new ArrayList<Giornata>();
        try{
            PlayerDAO daoP = PlayerDAO.getInstance();
            GiornataDAO dao = new GiornataDAO(daoP);
            list = dao.getRecentPlayList(user.getUsername());
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Event> getEventList() throws IOException{
        ArrayList<Event> list = new ArrayList<Event>();
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            EventDAO eventDao = new EventDAO(dao);
            list = eventDao.getPlayerEventList();
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<ReportBean> getReportList(String user) {
        ArrayList<ReportBean> list = new ArrayList<ReportBean>();
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            list = dao.getPlayerReportList(user);
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }
}
