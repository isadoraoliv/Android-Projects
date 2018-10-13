package com.isadora.oliveira.firebasecursods.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class Util {

    //conferir conexao com internet
    public static boolean statusInternet(Context context) {

        ConnectivityManager conexao = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo informacao = conexao.getActiveNetworkInfo();

        if (informacao != null && informacao.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean verificarCampos(Context context, String text_1, String text_2) {

        if (!text_1.isEmpty() && !text_2.isEmpty()) {

            if (statusInternet(context)) {
                return true;

            } else {
                Toast.makeText(context, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
                return false;
            }

        }else {
            Toast.makeText(context, "Prencher os campos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}



