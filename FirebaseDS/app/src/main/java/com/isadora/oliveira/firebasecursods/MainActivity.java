package com.isadora.oliveira.firebasecursods;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.Manifest;
import android.widget.Toast;

import com.isadora.oliveira.firebasecursods.storage.StorageDownloadActivity;
import com.isadora.oliveira.firebasecursods.util.Permissao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardViewDownload;
    private CardView cardViewUpload;
    private CardView cardViewDados;
    private CardView cardViewAlterarExcluir;
    private CardView cardViewEmpresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardViewDownload = (CardView) findViewById(R.id.cardView_Storage_Download);
        cardViewUpload = (CardView) findViewById(R.id.cardView_Storage_Upload);
        cardViewDados = (CardView) findViewById(R.id.cardView_Database_Dados);
        cardViewAlterarExcluir = (CardView) findViewById(R.id.cardView_Gravar_Alterar_Excluir);
        cardViewEmpresas = (CardView) findViewById(R.id.cardView_Empresas);

        cardViewDownload.setOnClickListener(this);
        cardViewUpload.setOnClickListener(this);
        cardViewDados.setOnClickListener(this);
        cardViewAlterarExcluir.setOnClickListener(this);
        cardViewEmpresas.setOnClickListener(this);

        permissao();

    }

    //user clicks
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cardView_Storage_Download:
                Intent intent = new Intent(getBaseContext(), StorageDownloadActivity.class);
                startActivity(intent);
                break;

            case R.id.cardView_Storage_Upload:

                break;

            case R.id.cardView_Database_Dados:

                break;

            case R.id.cardView_Gravar_Alterar_Excluir:

                break;

            case R.id.cardView_Empresas:

                break;
        }

    }

    //permission
    private void permissao(){

        String permissoes [] = new String[]{

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };

        Permissao.permissao(this, 0, permissoes);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        for(int result: grantResults){

            if(result == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Aceite as permissoes para o aplicativo funcionar " +
                        "correntamente", Toast.LENGTH_SHORT).show();
                finish();
                break;
            }

        }


    }
}
