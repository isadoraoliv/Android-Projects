package com.oliveira.isadora.dog_uau;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oliveira.isadora.dog_uau.modelo.Pessoa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText etNome, etLogradouro, etNumero, etComplemento, etCEP, etCidade, etEstado, etTelefone, etCPF, etRG, etEmail;
    ListView lvDados;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Pessoa> listPessoa = new ArrayList<Pessoa>();
    private ArrayAdapter<Pessoa> arrayAdapterPessoa;

    Pessoa pessoaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = (EditText) findViewById(R.id.et_nome);
        etLogradouro = (EditText) findViewById(R.id.et_logradouro);
        etNumero = (EditText) findViewById(R.id.et_numero);
        etComplemento = (EditText) findViewById(R.id.et_complemento);
        etCEP = (EditText) findViewById(R.id.et_cep);
        etCidade = (EditText) findViewById(R.id.et_cidade);
        etEstado = (EditText) findViewById(R.id.et_estado);
        etTelefone = (EditText) findViewById(R.id.et_telefone);
        etCPF = (EditText) findViewById(R.id.et_cpf);
        etRG = (EditText) findViewById(R.id.et_rg);
        etEmail = (EditText) findViewById(R.id.et_email);
        lvDados = (ListView) findViewById(R.id.lv_dados);

        inicializarFirebase();
        eventoDatabase();

        lvDados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pessoaSelecionada = (Pessoa) parent.getItemAtPosition(position);
                etNome.setText(pessoaSelecionada.getNome());

            }
        });

    }

    private void eventoDatabase() {
        databaseReference.child("Pessoa").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPessoa.clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Pessoa p = objSnapshot.getValue(Pessoa.class);
                    listPessoa.add(p);
                }
                arrayAdapterPessoa = new ArrayAdapter<Pessoa>(MainActivity.this, android.R.layout.simple_list_item_1, listPessoa);
                lvDados.setAdapter(arrayAdapterPessoa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_novo){
            Pessoa p = new Pessoa();
            p.setId(UUID.randomUUID().toString());
            p.setNome(etNome.getText().toString());
            p.setLogradouro(etLogradouro.getText().toString());
            p.setNumero(etNumero.getText().toString());
            p.setComplemento(etComplemento.getText().toString());
            p.setCep(etCEP.getText().toString());
            p.setCidade(etCidade.getText().toString());
            p.setEstado(etEstado.getText().toString());
            p.setCpf(etCPF.getText().toString());
            p.setRg(etRG.getText().toString());
            p.setEmail(etEmail.getText().toString());

            databaseReference.child("Pessoa").child(p.getId()).setValue(p);
            Toast.makeText(getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
            limparCampos();
        }
        else if (id == R.id.menu_update){
            Pessoa p = new Pessoa();
            p.setId(pessoaSelecionada.getId());
            p.setNome(etNome.getText().toString().trim());
            p.setLogradouro(etLogradouro.getText().toString().trim());
            p.setNumero(etNumero.getText().toString().trim());
            p.setComplemento(etComplemento.getText().toString().trim());
            p.setCep(etCEP.getText().toString().trim());
            p.setCidade(etCidade.getText().toString().trim());
            p.setEstado(etEstado.getText().toString().trim());
            p.setCpf(etCPF.getText().toString().trim());
            p.setRg(etRG.getText().toString().trim());
            p.setEmail(etEmail.getText().toString().trim());
            databaseReference.child("Pessoa").child(p.getId()).setValue(p);
            limparCampos();
        }
        else if (id == R.id.menu_delete){
            Pessoa p = new Pessoa();
            p.setId(pessoaSelecionada.getId());
            databaseReference.child("Pessoa").child(p.getId()).removeValue();
            limparCampos();
        }

        return true;
    }

    private void limparCampos() {
        etNome.setText("");
        etLogradouro.setText("");
        etNumero.setText("");
        etComplemento.setText("");
        etCEP.setText("");
        etCidade.setText("");
        etEstado.setText("");
        etTelefone.setText("");
        etCPF.setText("");
        etRG.setText("");
        etEmail.setText("");

    }
}