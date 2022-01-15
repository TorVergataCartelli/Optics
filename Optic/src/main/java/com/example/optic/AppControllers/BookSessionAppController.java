package com.example.optic.AppControllers;

import com.example.optic.bean.AdminBean;
import com.example.optic.dao.AdminDAO;
import com.example.optic.dao.PlayerDAO;
import com.example.optic.entities.Admin;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BookSessionAppController {

    public static ArrayList<AdminBean> getCampi() throws Exception {
        AdminDAO dao = AdminDAO.getInstance();
        ArrayList<AdminBean> lista = new ArrayList<AdminBean>();
        try {
            lista=dao.getCampoList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return lista;
    }


    public static void closeConn() throws IOException {
        try {
            PlayerDAO dao = PlayerDAO.getInstance();
            dao.closeConn();
        }catch (Exception e){
            System.out.println("Errore chiusura connessione con il database");
            e.printStackTrace();
        }
    }
}
