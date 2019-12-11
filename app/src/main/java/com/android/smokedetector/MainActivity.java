package com.android.smokedetector;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public final static String MODULE_MAC = "98:D3:34:90:6F:A1";

    private Button mDisconnectButton, mConnectButton;
    private TextView mEmpryConnectionTextview;
    private ListView mDataListview;

    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDisconnectButton = findViewById(R.id.button_disconnected);
        mConnectButton = findViewById(R.id.button_connected);
        mEmpryConnectionTextview = findViewById(R.id.textView_disconnect_info);
        mDataListview = findViewById(R.id.listView_detector);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        if (myBluetooth == null) {
            toast("Bluetooth Device Not Available");
        } else {
            if (myBluetooth.isEnabled()) {

            } else {
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon, 1);
            }
        }
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress()); //Get the device's name and the address
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        mDataListview.setAdapter(adapter);
//        mDataListview.setOnItemClickListener(myListClickListener); //Method called when the device from the list is clicked

    }

//    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
//    {
//        public void onItemClick (AdapterView<?> av, View v, int arg2, long arg3)
//        {
//            // Get the device MAC address, the last 17 chars in the View
//            String info = ((TextView) v).getText().toString();
//            String address = info.substring(info.length() - 17);
//
//            // Make an intent to start next activity.
////            Intent i = new Intent(this, ledControl.class);
//
//            //Change the activity.
////            i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
//            startActivity(i);
//        }
//    }
}
