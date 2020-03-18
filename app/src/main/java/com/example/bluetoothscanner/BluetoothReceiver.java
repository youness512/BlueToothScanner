package com.example.bluetoothscanner;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class BluetoothReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            // Un récupère le périphérique bluetooth détecté durant le scan
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            // Et on affiche ses informations dans un toast et dans la fenêtre LogCat
            String message = device.getName() + "-" + device.getAddress();
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            Log.i("DebugBluetooth", message);

            String Adresse =


        }

    }

}