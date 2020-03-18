package com.example.bluetoothscanner;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 456;
    private static final int REQUEST_ENABLE_LOCATION = 457;

    private BluetoothAdapter bluetoothAdapter = null;

    //recupération de l'id de mon bouton (suite ligne 66)
    private Button btnScan;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On cherche à récupérer l'interface bluetooth du périphérique Android
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Si pas de module (d'interface) bluetooth sur le périphérique ...
        if ( bluetoothAdapter == null ) {
            Toast.makeText(
                    this,
                    "Bluetooth not supported on this deveice",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Si le bluetooth n'est pas activé, on propose de l'activer
        if ( ! bluetoothAdapter.isEnabled() ) {
            // Demande à activer l'interface bluetooth
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // On vérifie les autorisations pour la localisation
        //     Cette permission est requise sur les versions récentes d'Android, car on
        //     peut se localiser par triangulation avec différentes bornes bluetooth en
        //     fonction de la puissance des signaux reçus.
        if ( checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(
                    new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    REQUEST_ENABLE_LOCATION );
        }

        // On enregistre un gestionnaire d'événements sur le bouton
        btnScan = findViewById( R.id.btnScan );
        btnScan.setOnClickListener( btnScanLister );


        textView = findViewById(R.id.textView);
    }


    private View.OnClickListener btnScanLister = new View.OnClickListener() {
        private BluetoothReceiver bluetoothReceiver = null;

        @Override
        public void onClick(View view) {
            // On enregistre le "broadcast receiver" une unique fois
            if ( bluetoothReceiver == null ) {
                bluetoothReceiver = new BluetoothReceiver();
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(bluetoothReceiver, filter);
            }

            // Si un scan bluetooth est en cours, on le coupe
            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }

            textView.setText("heyhey");

            // On lance un nouveau scan bluetooth
            bluetoothAdapter.startDiscovery();


        }
    };

}

