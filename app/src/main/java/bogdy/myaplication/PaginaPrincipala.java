package bogdy.myaplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class PaginaPrincipala extends AppCompatActivity {



    //Se creeaza clasa bluetooth si i se atribuie valoarea NULL.
    public static String EXTRA_ADDRESS = "adresa_dispozitiv";
    private BluetoothAdapter ComunicatieBluetooth=null;
    private Set<BluetoothDevice> dispozitiveLegate;


    //Se creaza widget-urile cu care se lucreaza in continuare
    Button butonDispozitive;
    ListView listaDispozitive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principala);

        //Se initializeaza widget-urile create mai sus
        butonDispozitive = (Button)findViewById(R.id.dispozitiveImperecheate);
        listaDispozitive=(ListView)findViewById(R.id.dispozitiveLista);

        //Verificam daca mobilul suporta comunicatia bluetooth

        ComunicatieBluetooth=BluetoothAdapter.getDefaultAdapter();
        if(ComunicatieBluetooth == null)
        {
            //Daca mobilul nu suporta bluetooth se afiseaza un mseaj corespunzator
            Toast.makeText(getApplicationContext(), "bluetooth nu este suportat!", Toast.LENGTH_LONG).show();

            //Se termina aplicatia
            finish();
        }

        //Se afiseaza un mesaj pentru a permite aplicatiei bluetooth sa fie pornita
        else if(!ComunicatieBluetooth.isEnabled())
        {   //pe telefonul mobil trebuie sa alegem permite(allow)
            Intent startBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(startBluetooth,1);
        }

        butonDispozitive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                listaDispozitivelorImperecheate();
            }
        });

    }

    private void listaDispozitivelorImperecheate(){
        dispozitiveLegate = ComunicatieBluetooth.getBondedDevices();
        ArrayList lista = new ArrayList();

        if (dispozitiveLegate.size()>0)
        {
            for(BluetoothDevice bth : dispozitiveLegate)
            {
                //Se adauga in lista dispozitivele imperecheate
                lista.add(bth.getName() + "\n" + bth.getAddress());
            }

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Nu am gasit dispozitive imperecheate.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter xAdaptor = new ArrayAdapter(this,android.R.layout.simple_list_item_1, lista);
        listaDispozitive.setAdapter(xAdaptor);
        //metoda care se apeleaza atunci cand o adresa bluetooth este selectata din lista
        listaDispozitive.setOnItemClickListener(ListClickListener); //Method called when the device from the list is clicked

    }


    private AdapterView.OnItemClickListener ListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView av, View v, int argument1, long argument2)
        {

            //Se i-a adresa MAC a mobilului
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Make an intent to start next activity.
            Intent i = new Intent(PaginaPrincipala.this, ControlComunicatieBluetooth.class);

            //Se trece la pagina noua(activity)
            i.putExtra(EXTRA_ADDRESS, address);
            startActivity(i);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meniu_lista_dispozitive, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
