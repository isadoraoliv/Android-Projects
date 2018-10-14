package com.isadora.oliveira.firebasecursods.storage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
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
import com.isadora.oliveira.firebasecursods.R;
import com.isadora.oliveira.firebasecursods.util.DialogAlerta;
import com.isadora.oliveira.firebasecursods.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


public class StorageDownloadActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private ProgressBar progressBar;
    private Button button_Download;
    private Button button_Remover;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_download);

        imageView = (ImageView)findViewById(R.id.imageView_StorageDownload);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_StorageDownload);

        button_Download = (Button)findViewById(R.id.button_StorageDownload);

        button_Remover = (Button) findViewById(R.id.button_StorageDownload_Remover);


        button_Download.setOnClickListener(this);
        button_Remover.setOnClickListener(this);

        progressBar.setVisibility(View.GONE);


        storage = FirebaseStorage.getInstance();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.button_StorageDownload:

                button_Download();

                break;

            case R.id.button_StorageDownload_Remover:

                button_Remover();

                break;
        }
    }

    private void button_Download(){

        if (Util.statusInternet(getBaseContext())){

            // download_imagem_1();
            download_imagem_2();
        }else{


            DialogAlerta alerta = new DialogAlerta("Erro de Conexão","Verifique se sua conexão Wiffi ou 3G está funcionando");
            alerta.show(getSupportFragmentManager(),"1");

        }
    }


    private void button_Remover(){


        if (Util.statusInternet(getBaseContext())){

            /// remover_imagem_1();
            remover_imagem_2();
        }else{

            DialogAlerta alerta = new DialogAlerta("Erro de Conexão","Verifique se sua conexão Wifi ou 3G está funcionando");
            alerta.show(getSupportFragmentManager(),"1");

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_storage_download,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){


            case R.id.item_compartilhar:

                item_Compartilhar();
                return true;


            case R.id.item_criar_pdf:

                item_GerarPDF();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void item_Compartilhar(){

        if( imageView.getDrawable() != null){

            compartilhar();

        }else{

            DialogAlerta alerta = new DialogAlerta("Erro de Compartilhamento", "Não existe nenhuma imagem para compartilhar");
            alerta.show(getSupportFragmentManager(),"1");
        }
    }


    private void item_GerarPDF(){

        if( imageView.getDrawable() != null){

            try {
                gerarPDF();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else{

            DialogAlerta alerta = new DialogAlerta("Erro ao Gerar PDF", "Não existe nenhuma imagem para gerar o PDF");
            alerta.show(getSupportFragmentManager(),"1");

        }
    }

    private void download_imagem_1(){

        progressBar.setVisibility(View.VISIBLE);

        String url = "https://firebasestorage.googleapis.com/v0/b/fir-cursods-34812.appspot.com/o/imagem%2Favatar.png?alt=media&token=36a1a962-b463-42f3-9853-de9b58368977";

          /*Picasso.
                with(getBaseContext()).
                load(url).
                into(imageView, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);

            }
        });

       */

        Glide.
                with(getBaseContext()).
                asBitmap().
                load(url).
                listener(new RequestListener<Bitmap>() {
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


    private void download_imagem_2(){

        progressBar.setVisibility(View.VISIBLE);

        StorageReference reference = storage.getReference().child("imagem").child("avatar.png");

        reference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {

            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()){

                    String url = task.getResult().toString();

                    Picasso.with(getBaseContext()).load(url).into(imageView, new Callback() {
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


    //remove image
    private void remover_imagem_1(){

        String url = "https://firebasestorage.googleapis.com/v0/b/fir-cursods-34812.appspot.com/o/imagem%2Favatar.png?alt=media&token=36a1a962-b463-42f3-9853-de9b58368977";

        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(url);

        reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    imageView.setImageDrawable(null);

                    Toast.makeText(getBaseContext(),"Sucesso ao Remover Imagem",Toast.LENGTH_LONG).show();
                }else{


                    Toast.makeText(getBaseContext(),"Erro ao Remover Imagem",Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    private void remover_imagem_2(){

        String nome = "avatar.png";

        StorageReference reference = storage.getReference().child("imagem").child(nome);

        reference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    imageView.setImageDrawable(null);

                    Toast.makeText(getBaseContext(),"Sucesso ao Remover Imagem",Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(getBaseContext(),"Erro ao Remover Imagem",Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    //share image
    private void compartilhar(){

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("image/jpeg");

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG,100, bytes);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"FirebaseDS",null);

        Uri uri = Uri.parse(path);

        intent.putExtra(Intent.EXTRA_STREAM,uri);

        startActivity(Intent.createChooser(intent,"Compartilhar imagem"));
    }

    //create pdf file
    private void gerarPDF() throws DocumentException, FileNotFoundException {

        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        String nome_Arquivo = diretorio.getPath()+"/"+"FirebaseCurso"+System.currentTimeMillis()+".pdf";

        File pdf = new File(nome_Arquivo);

        OutputStream outputStream =  new FileOutputStream(pdf);

        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document,outputStream);

        writer.setBoxSize("firebase", new Rectangle(36,54,559,788));

        document.open();

        Font font = new Font(Font.FontFamily.HELVETICA,20,Font.BOLD);

        Paragraph paragraph = new Paragraph("FirebaseDS",font);

        paragraph.setAlignment(Element.ALIGN_CENTER);

        ListItem item = new ListItem();

        item.add(paragraph);

        document.add(item);

        try {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

            Bitmap bitmap = drawable.getBitmap();

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG,100, bytes);


            Image image = Image.getInstance(bytes.toByteArray());

            image.scaleAbsolute(100f,100f);

            image.setAlignment(Element.ALIGN_CENTER);

            document.add(image);


        } catch (IOException e) {
            e.printStackTrace();
        }

        document.close();

        visualizarPDF(pdf);

    }

    //view pdf
    private void visualizarPDF(File pdf){

        PackageManager packageManager = getPackageManager();

        Intent itent = new Intent(Intent.ACTION_VIEW);

        itent.setType("application/pdf");

        List<ResolveInfo> lista = packageManager.queryIntentActivities(itent,PackageManager.MATCH_DEFAULT_ONLY);


        if(lista.size() > 0 ){

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Uri uri = FileProvider.getUriForFile(getBaseContext(),"com.isadora.oliveira.firebasecursods",pdf);

            intent.setDataAndType(uri,"application/pdf");

            startActivity(intent);

        }else{

            DialogAlerta dialogAlerta = new DialogAlerta("Erro ao Abrir PDF","Não foi detectado nenhum leitor de PDF no seu Dispositivo.");
            dialogAlerta.show(getSupportFragmentManager(),"3");

        }

    }
}



















