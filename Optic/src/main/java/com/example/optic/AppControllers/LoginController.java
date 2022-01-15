package com.example.optic.AppControllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.RefereeBean;
import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.RefereeDAO;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Player;
import com.example.optic.entities.Referee;

import java.io.IOException;

public class LoginController {

    public static boolean playerLogin(PlayerBean p) throws Exception {
        boolean res = false;
        //controllo username e password
        try {
            PlayerDAO dao = PlayerDAO.getInstance();
            Player user = dao.getPlayer(p.getUsername());
            //controllo se il player esiste
            if(user != null){
                //esiste
                if(user.getPassword().equals(p.getPassword())){//controllo se la password è uguale
                    //password uguale
                    res = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean adminLogin(AdminBean a) throws Exception {
        boolean res = false;
        //controllo username e password
        try {
            AdminDAO dao = AdminDAO.getInstance();
            Admin user = dao.getAdmin(a.getUsername());
            //controllo se il player esiste
            if(user != null){
                //esiste
                if(user.getPassword().equals(a.getPassword())){//controllo se la password è uguale
                    //password uguale
                    res = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean refereeLogin(RefereeBean r)throws Exception{
        boolean res=false;
        try{
            RefereeDAO dao = RefereeDAO.getInstance();
            Referee ref = dao.getReferee(r.getUsername());
            if(ref!=null){
                if(ref.getPassword().equals(r.getPassword())){//controllo se la password è uguale
                    //password uguale
                    res = true;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void closeConn(int user) throws IOException {
        try {
            switch (user) {
                case 1 -> {
                    PlayerDAO player = PlayerDAO.getInstance();
                    player.closeConn();
                }
                case 2 -> {
                    AdminDAO admin = AdminDAO.getInstance();
                    admin.closeConn();
                }
                case 3 -> {
                    RefereeDAO referee = RefereeDAO.getInstance();
                    referee.closeConn();
                }
            }
        }catch (Exception e){
            System.out.println("Errore chiusura connessione con il database");
            e.printStackTrace();
        }
    }
}
