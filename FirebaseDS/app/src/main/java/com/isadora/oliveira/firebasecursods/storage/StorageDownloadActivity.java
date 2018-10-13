package com.isadora.oliveira.firebasecursods.storage;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.isadora.oliveira.firebasecursods.R;
import com.squareup.picasso.Picasso;


public class StorageDownloadActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private ProgressBar progressBar;
    private Button button_download;
    private Button button_remover;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_download);

        imageView = (ImageView) findViewById(R.id.imageView_StorageDownload);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_StorageDownload);
        button_download = (Button) findViewById(R.id.button_StorageDownload);
        button_remover = (Button) findViewById(R.id.button_StorageDownload_Remover);

        button_download.setOnClickListener(this);
        button_remover.setOnClickListener(this);

        progressBar.setVisibility(View.GONE);

        storage = FirebaseStorage.getInstance();

    }

    @Override

    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_StorageDownload:

                //download imagem pela url
                //download_image_1();

                //download imagem pelo nome
                download_image_2();

                break;

            case R.id.button_StorageDownload_Remover:

                //remover imagem pela url
                //remover_image_1();

                //remover imagem pelo nome
                remover_image_2();

                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_storage_download, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.item_compartilhar:
                break;

            case R.id.item_criar_pdf:
                break;

        }


        return super.onOptionsItemSelected(item);
    }


    //------------------------------Download Image------------------------------//
    //download for image's url
    public void download_image_1(){

        progressBar.setVisibility(View.VISIBLE);

        String url = "https://firebasestorage.googleapis.com/v0/b/fir-cursods-34812.appspot.com/o/imagem%2Favatar.png?alt=media&token=7b5a4abb-325f-46da-81ad-be487b6f90c2";
        /*Picasso.with(getBaseContext()).load(url).into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError() {

                progressBar.setVisibility(View.GONE);

            }
        });*/

        Glide.with(getBaseContext()).
                asBitmap().
        load(url).listener(new RequestListener<Bitmap>(){

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {

                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(imageView);

    }

    //Download for name image
    private void download_image_2(){

        progressBar.setVisibility(View.VISIBLE);

        StorageReference reference = storage.getReference().child("imagem").child("avatar.png");

        reference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if(task.isSuccessful()){
                    String url = task.getResult().toString();
                    Picasso.with(getBaseContext()).load(url).into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                            progressBar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onError() {

                            progressBar.setVisibility(View.GONE);

                        }
                    });
                }

            }
        });
    }


    //------------------------------Remove Image------------------------------//
    public void remover_image_1(){

        String url = "https://firebasestorage.googleapis.com/v0/b/fir-cursods-34812.appspot.com/o/imagem%2Favatar.png?alt=media&token=7b5a4abb-325f-46da-81ad-be487b6f90c2";

        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(url);

        reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    imageView.setImageDrawable(null);

                    Toast.makeText(getBaseContext(), "Sucesso ao remover imagem!", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(getBaseContext(), "Erro ao remover imagem!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //remover image by name
    public void remover_image_2(){

        String nome = "avatar.png";

        StorageReference reference = storage.getReference().child("imagem").child(nome);

        reference.delete().addOnCompleteListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


            }
        } );
    }

}



















