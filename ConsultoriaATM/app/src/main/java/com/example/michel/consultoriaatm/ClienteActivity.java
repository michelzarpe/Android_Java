package com.example.michel.consultoriaatm;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ClienteActivity extends Activity {
    private TextView idTextCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        idTextCliente = (TextView) findViewById(R.id.idTextCliente);
        Bundle extra = getIntent().getExtras();
        System.out.println("-> "+extra.getString("idInputText"));
        if(extra!=null)
            idTextCliente.setText(extra.getString("idInputText"));
    }
}
