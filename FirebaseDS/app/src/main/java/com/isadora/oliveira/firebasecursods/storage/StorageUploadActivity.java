package com.isadora.oliveira.firebasecursods.storage;

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

                break;


            case R.id.item_camera:

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}




