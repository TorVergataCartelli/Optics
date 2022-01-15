package com.example.optic.AppControllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.GiornataBean;
import com.example.optic.bean.UserBean;
import com.example.optic.bean.ValutazioneBean;
import com.example.optic.dao.*;
import com.example.optic.entities.*;

import java.io.IOException;
import java.util.ArrayList;

public class UserPgPageAppController {

    public static AdminBean getCampoInfo(AdminBean campo) throws Exception{
        AdminDAO dao = AdminDAO.getInstance();
        String nomeC = campo.getNomeCampo();;
        Admin x = dao.getCampo(nomeC);
        AdminBean y = new AdminBean();
        Referee ref = null;

        y.setUsername(x.getUsername());
        y.setPassword(x.getPassword());
        y.setDescrizione(x.getDescrizioneC());
        y.setFb(x.getFb());
        y.setIg(x.getIg());
        y.setWa(x.getWa());
        y.setNomeCampo(x.getNomeC());
        ref = dao.getRefereeFromAdmin(x.getUsername());
        if(ref != null){
            y.setReferee(ref.getUsername());
        }
        y.setVia(x.getVia());
        y.setProvincia(x.getProvincia());
        return y;
    }

    public static boolean isPlayerBooked(UserBean player, GiornataBean play){
        boolean res = true;
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            PrenotazioneDAO prenDao = new PrenotazioneDAO(dao);
            res = prenDao.isPlayerBooked(player.getUsername(), play.getIdPlay());
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public static void bookPlay(UserBean player, GiornataBean play){
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
            PrenotazioneDAO prenDao = new PrenotazioneDAO(dao);
            prenDao.bookPlay(player.getUsername(), play.getIdPlay());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Giornata getFirstPlay(UserBean bean){
        Giornata play = null;
        try{
            PlayerDAO dao = PlayerDAO.getInstance();
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
            PlayerDAO dao = PlayerDAO.getInstance();
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
            PlayerDAO dao = PlayerDAO.getInstance();
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
            PlayerDAO dao = PlayerDAO.getInstance();
            GiornataDAO playDao = new GiornataDAO(dao);
            list = playDao.getPlayersList(bean.getIdPlay());
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static void saveReview(ValutazioneBean val) throws IOException {
        PlayerDAO p= PlayerDAO.getInstance();
        ValutazioneDAO dao=new ValutazioneDAO(p);
        if(dao.getValutazione(val,0)){
            dao.deleteValutazione(val,0);
        }
        dao.saveReview(val,0);
    }

    public static ArrayList<Valutazione> reviewList(AdminBean admin){
        ArrayList<Valutazione> list = null;
        try {

            PlayerDAO player= PlayerDAO.getInstance();
            ValutazioneDAO dao= new ValutazioneDAO(player);
            list=dao.getAdminReviewList1(admin.getNomeCampo());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
