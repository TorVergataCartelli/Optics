package com.example.optic.AppControllers;

import com.example.optic.bean.PlayerBean;
import com.example.optic.bean.UserBean;
import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.dao.RefereeDAO;
import com.example.optic.entities.Admin;
import com.example.optic.entities.Player;
import com.example.optic.entities.Referee;

import java.io.IOException;

public class RegisterController {

    public static boolean isUsernameUsed(UserBean user, int userType) throws Exception {
        boolean res = false;
        switch (userType){
            case 1 ->   {
                PlayerDAO p = PlayerDAO.getInstance();
                Player player = null;
                player = p.getPlayer(user.getUsername());
                if(player != null){
                    res = true;
                }
            }
            case 2 -> {
                AdminDAO a = AdminDAO.getInstance();
                Admin admin = null;
                admin = a.getAdmin(user.getUsername());
                if(admin != null) {
                    res = true;
                }
            }
            case 3 -> {
                RefereeDAO r = RefereeDAO.getInstance();
                Referee referee = null;
                r.getConn();
                referee = r.getReferee(user.getUsername());
                if(referee != null){
                    res = true;
                }
                //res = true; res = RefereeDAO.isReferee(username);
            }
            default ->{

            }
        }
        return res;
    }

    public static void userRegister(UserBean user,int userType) throws Exception {
        String username;
        String password;
        try {
            switch (userType) {
                case 1 -> {
                    PlayerDAO player = PlayerDAO.getInstance();
                    username = user.getUsername();
                    password = user.getPassword();
                    player.newPlayer(username, password);
                }
                case 2 -> {
                    AdminDAO admin = AdminDAO.getInstance();
                    String via = user.getVia();
                    String nomeC = user.getNomeC();
                    String prov = user.getProv();
                    username = user.getUsername();
                    password = user.getPassword();
                    admin.newAdmin(username,password,via,nomeC,prov);
                }
                case 3 -> {
                    RefereeDAO referee = RefereeDAO.getInstance();
                    username = user.getUsername();
                    password = user.getPassword();
                    referee.getConn();
                    referee.newReferee(username, password);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
