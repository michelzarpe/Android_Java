package com.cursoandroid.firebaseapp.firebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cursoandroid.firebaseapp.firebaseapp.model.Produto;
import com.cursoandroid.firebaseapp.firebaseapp.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    /*faz referencia a raiz do meu projeto na web*/
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioFirebase = databaseReference.child("usuario");
   /* private DatabaseReference produtoFirebase = databaseReference.child("produto").child("003");*//*pega apenas os dados do filho 003*/
     private DatabaseReference produtoFirebase = databaseReference.child("produto");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      /*
       usuarioFirebase.child("001").child("nome").setValue("Michel");
        usuarioFirebase.child("001").child("sobrenome").setValue("Zarpelon");
        usuarioFirebase.child("001").child("telefone").setValue("45123458");
*/
/**/
        Usuario usuario = new Usuario();
        usuario.setNome("Rogeria");
        usuario.setSobrenome("Ramos");
        usuario.setSexo("Femenino");
        usuario.setIdade(35);

        usuarioFirebase.child("003").setValue(usuario);


/*
        Produto agua = new Produto("Agua Mineral","incolor", "Ourofino");
       // produtoFirebase.child("003").setValue(agua);



*/

        //      produtoFirebase.addValueEventListener(new ValueEventListener() {
        /*sempre que ouver alguma movimentacao no objeto*/
        //       @Override
        //       public void onDataChange(DataSnapshot dataSnapshot) {
        //        Log.i("FIREBASE PRODUTO",dataSnapshot.getValue().toString());
        //      }

        /*caso de algum erro no retorno do bancos*/
        //     @Override
        //      public void onCancelled(DatabaseError databaseError) {

        //     }
        //   });

        usuarioFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FIREBASE",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
