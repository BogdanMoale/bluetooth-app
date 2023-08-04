package bogdy.myaplication;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Bogdy on 10/24/2016.
 */

public class ControlComunicatieBluetooth extends AppCompatActivity {
    //Creeare widget-uri si variabile
    BluetoothSocket Skt = null;
    private ProgressDialog progres;
    BluetoothAdapter ConexiuneBluetooth = null;
    Button Inainte, Inapoi, Dreapta, Stanga, Oprire,Deconectare,Vorbire,urmarireLinie,evitareObstacole;
    private boolean BluetoothConectat = false;
    private ProgressDialog mesaj;
    private final int Porneste_Bluetooth = 1;
    String adresa = null;
    //SPP UUID.
    static final UUID SerialPortBT = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    byte c=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        //I-a adresa dispozitivului bluetooth
        adresa = newint.getStringExtra(PaginaPrincipala.EXTRA_ADDRESS);
        //Seteaza pagina ControlComunicatiebluetooth
        setContentView(R.layout.activity_control_comunicatie_bluetooth);

        Inainte = (Button) findViewById(R.id.butonInainte);
        Inapoi = (Button) findViewById(R.id.butonInapoi);
        Stanga = (Button) findViewById(R.id.butonStanga);
        Dreapta = (Button) findViewById(R.id.butonDreapta);
        Oprire = (Button) findViewById(R.id.butonOprire);
        Deconectare = (Button) findViewById(R.id.ButonDeconectare);
        Vorbire=(Button) findViewById(R.id.ButonVorbire);
        urmarireLinie=(Button) findViewById(R.id.butonLinie);
        evitareObstacole=(Button) findViewById(R.id.butonObstacole);

        new ConectareBluetooth().execute();

        //creare eveniment la apasarea butoanelor
        Inainte.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DirectieInainte();
            }
        });
        Inapoi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DirectieInapoi();
            }
        });
        Stanga.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DirectieStanga();
            }
        });
        Dreapta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DirectiaDreapta();
            }
        });
        Oprire.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Stop();
            }
        });
        Deconectare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Deconectare();
            }
        });




        Vorbire.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SpeachRecognition();
            }
        });


        urmarireLinie.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UrmaresteLinia();
            }
        });


       evitareObstacole.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EvitaObstacole();
            }
        });

    }
    //Meniu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_lista_dispozitive, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.BluetoothPornit:
                PornesteBluetooth();
                return true;
            case R.id.BluetoothOprit:
                OpresteBluetooth();
                return true;
            case R.id.Setari:
                SetariBluetooth();
                return true;
            case R.id.Info:
                Despre("Versiunea 1.0");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static final int SPEECH_REQUEST_CODE = 0;

    // Create an intent that can start the Speech Recognizer activity
    private void SpeachRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            // Do something with spokenText
                if (Skt!=null) {
                    try {
                        Skt.getOutputStream().write(spokenText.toString().getBytes());
                    } catch (IOException e) {
                        AfisareMesaj("Eroare,incearca din nou");
                    }
                    AfisareMesaj(spokenText);
                }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void AfisareMesaj(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


    private void DirectieInainte()
    {
        if (Skt!=null)
        {
            try
            {
                Skt.getOutputStream().write("a".toString().getBytes());
            }
            catch (IOException e)
            {
                AfisareMesaj("Eroare");
            }
        }
    }


    private void DirectieInapoi()
    {
        if (Skt!=null)
        {
            try
            {
                Skt.getOutputStream().write("b".toString().getBytes());
            }
            catch (IOException e)
            {
                AfisareMesaj("Eroare");
            }
        }
    }


    private void DirectieStanga()
    {
        if (Skt!=null)
        {
            try
            {
                Skt.getOutputStream().write("left".toString().getBytes());
            }
            catch (IOException e)
            {
                AfisareMesaj("Eroare");
            }
        }
    }


    private void DirectiaDreapta()
    {
        if (Skt!=null)
        {
            try
            {
                Skt.getOutputStream().write("right".toString().getBytes());
            }
            catch (IOException e)
            {
                AfisareMesaj("Eroare");
            }
        }
    }


    private void Stop()
    {
        if (Skt!=null)
        {
            try
            {
                Skt.getOutputStream().write("stop".toString().getBytes());
            }
            catch (IOException e)
            {
                AfisareMesaj("Eroare");
            }
        }
    }


    private void UrmaresteLinia()
    {
        if (Skt!=null)
        {
            try
            {
                Skt.getOutputStream().write("c".toString().getBytes());
            }
            catch (IOException e)
            {
                AfisareMesaj("Eroare");
            }
        }
    }


    private void EvitaObstacole()
    {
        if (Skt!=null)
        {
            try
            {
                Skt.getOutputStream().write("obstacle".toString().getBytes());
            }
            catch (IOException e)
            {
                AfisareMesaj("Eroare");
            }
        }
    }

    private void Deconectare()
    {
        //daca socketul este ocupat
        if (Skt!=null)
        {
            try
            {
                Skt.close();
            }
            catch (IOException e)
            { AfisareMesaj("Eroare");}
        }
        finish();

    }

    private class ConectareBluetooth extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConectareReusita = true;

        @Override
        protected void onPreExecute()
        {
            mesaj = ProgressDialog.show(ControlComunicatieBluetooth.this, "Conectare", "");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (Skt == null || BluetoothConectat)
                {
                    //Adresa bluetooth
                    ConexiuneBluetooth = BluetoothAdapter.getDefaultAdapter();
                    //Se conecteaza la adresa obtinuta pentru a verifica daca este valida
                    BluetoothDevice dispositiv = ConexiuneBluetooth.getRemoteDevice(adresa);
                    //conexiune SPP
                    Skt = dispositiv.createInsecureRfcommSocketToServiceRecord(SerialPortBT);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    //Conectare
                    Skt.connect();
                }
            }
            catch (IOException e)
            {
                ConectareReusita = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConectareReusita)
            {
                AfisareMesaj("Eroare");
                finish();
            }
            else
            {
                BluetoothConectat = true;
            }
            mesaj.dismiss();
        }


    }
    //elementele din meniu
    public void PornesteBluetooth(){
        if (!ConexiuneBluetooth.isEnabled()) {
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, Porneste_Bluetooth);
            Toast.makeText(getApplicationContext(),"Bluetooth pornit" , Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(getApplicationContext(),"Bluetooth este deja pornit", Toast.LENGTH_LONG).show();
        }
    }


    public void OpresteBluetooth(){
        ConexiuneBluetooth.disable();
        Toast.makeText(getApplicationContext(),"Bluetooth oprit",
                Toast.LENGTH_LONG).show();
    }

    public void SetariBluetooth(){
        Intent intentBluetooth = new Intent();
        intentBluetooth.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(intentBluetooth);
    }


    private void Despre(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }


}
