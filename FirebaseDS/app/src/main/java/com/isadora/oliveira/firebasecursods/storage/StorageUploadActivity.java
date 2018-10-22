package com.isadora.oliveira.firebasecursods.storage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.isadora.oliveira.firebasecursods.R;

public class StorageUploadActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private Button button_enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_storage_upload);

        imageView = (ImageView) findViewById(R.id.imageView_StorageUpload);
        button_enviar = (Button) findViewById(R.id.button_StorageUpload_Enviar);

        button_enviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_StorageUpload_Enviar:
                Toast.makeText(getBaseContext(), "Button Upload", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    //create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_storage_upload,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_galeria:
                obterImagem_galeria();

                break;

            case R.id.item_camera:
                //obterImagem_camera();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //recuperando imagem da galeria
    public void obterImagem_galeria(){

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //forResult é para comunicar com um aplicativo que não tem nada com o seu aplicativo
        startActivityForResult(Intent.createChooser(intent, "Escolha uma imagem"), 0);

    }

    //metodo responsavel por informar o resultado que a galeria nos mandou
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if(requestCode == 0){

                if(data != null){

                    Bundle extras = data.getExtras();
                    Bitmap bitmap = (Bitmap) extras.get("data");

                    imageView.setImageBitmap(bitmap);

                }
            }

        }
    }
}







