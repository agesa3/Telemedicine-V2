package com.agesadev.telmedv2.presentation.forms

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.agesadev.telmedv2.data.AppConstants
import com.agesadev.telmedv2.data.services.BluetoothService
import com.agesadev.telmedv2.databinding.FragmentBluetoothDialogBinding
import java.io.IOException
import kotlin.reflect.full.memberFunctions

class BluetoothDialog : DialogFragment(), AdapterView.OnItemClickListener {
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter

    private lateinit var pairedDevices: Set<BluetoothDevice>
    private var availableDevices: MutableSet<BluetoothDevice> = mutableSetOf()

    private lateinit var binding: FragmentBluetoothDialogBinding

    private lateinit var mBluetoothService: BluetoothService

    private lateinit var availableDevicesAdapter: BluetoothDeviceAdapter

    private val TAG = this::class.simpleName

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = layoutInflater


        bluetoothManager = requireContext().getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.adapter

        binding = FragmentBluetoothDialogBinding.inflate(inflater)
        mBluetoothService = BluetoothService(mHandler)

        val filter: IntentFilter = IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        requireContext().registerReceiver(mBroadcastReceiver3, filter)

        builder.setView(binding.root)
            .setTitle("Bluetooth pairing")

        enableBluetooth()
        listDevices()
        discoverDevices()

        return builder.create()
    }

    private fun enableBluetooth() {
        val turnOn = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(turnOn, 0)
    }

    private fun visible() {
        val getvisible = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        startActivityForResult(getvisible, 0)
    }
    
    private fun discoverDevices() {
        Log.d(TAG, "discoverDevices: Looking for unpaired devices...")

        if(bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery()
            Log.d(TAG, "discoverDevices: Canceling discovery")

            checkBTPermissions()

            bluetoothAdapter.startDiscovery()
            val discoverDeviceIntent = IntentFilter(BluetoothDevice.ACTION_FOUND)
            requireContext().registerReceiver(mBroadcastReceiver2, discoverDeviceIntent)
        }

        if (!bluetoothAdapter.isDiscovering()) {
            checkBTPermissions()

            bluetoothAdapter.startDiscovery()
            val discoverDeviceIntent = IntentFilter(BluetoothDevice.ACTION_FOUND)
            requireContext().registerReceiver(mBroadcastReceiver2, discoverDeviceIntent)
        }
    }

    private val mBroadcastReceiver2: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val action = p1?.action
            Log.d(TAG, "onReceive: Action FOUND")

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                val device: BluetoothDevice? = p1?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                if(device != null) {
                    availableDevices.add(device)
                    Log.d(TAG, "onReceive: ${device.name} : ${device.address}")
                    availableDevicesAdapter = BluetoothDeviceAdapter(availableDevices)

                    val availableDeviceNames: MutableList<String> = mutableListOf()
                    availableDevices.forEach { availableDevices.forEach { availableDeviceNames.add(it.name?:"") } }

                    binding.availableDevicesList.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, availableDeviceNames)
                    binding.availableDevicesList.setOnItemClickListener(this@BluetoothDialog)
//                    binding.availableDevicesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

    private val mBroadcastReceiver3: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val action: String? = p1?.action

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                val mDevice: BluetoothDevice? = p1?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

                if(mDevice != null) {
                    // bonded already
                    if(mDevice.bondState == BluetoothDevice.BOND_BONDED) {
                        Log.d(TAG, "BroadcastReceiver: BOND_BONDED")
                    }

                    // creating a bond
                    if(mDevice.bondState == BluetoothDevice.BOND_BONDING) {
                        Log.d(TAG, "BroadcastReceiver: BOND_BONDING")
                    }

                    // breaking a bond
                    if(mDevice.bondState == BluetoothDevice.BOND_NONE) {
                        Log.d(TAG, "BroadcastReceiver: BOND_BONDED")
                    }
                }
            }
        }
    }


    private fun checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            var permissionCheck : Int = requireContext().checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION")
            permissionCheck += requireContext().checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION")
            if (permissionCheck != 0) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1001)
            } else {
                Log.d(TAG, "checkBTPermissions: No need to check permission. SDK version < LOLLIPOP")
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun listDevices() {
        pairedDevices = bluetoothAdapter.bondedDevices

        val deviceNameList: MutableList<String> = mutableListOf()
        pairedDevices.forEach { deviceNameList.add(it.name) }

        val adapter = BluetoothDeviceAdapter(pairedDevices)

        binding.pairedDevicesList.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, deviceNameList)
//        binding.pairedDevicesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }


    private val mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            val readBuf: ByteArray = msg.obj as ByteArray
            val readMessage = String(readBuf, 0, msg.arg1)
            Toast.makeText(requireContext(), readMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(mBroadcastReceiver2)
        requireContext().unregisterReceiver(mBroadcastReceiver3)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        bluetoothAdapter.cancelDiscovery()

        Log.d(TAG, "onItemClick: You clicked a device")
        val deviceName: String = availableDevices.elementAt(p2).name
        val deviceAddress: String = availableDevices.elementAt(p2).address

        Log.d(TAG, "onItemClick: deviceName = ${deviceName}")
        Log.d(TAG, "onItemClick: deviceAddress = ${deviceAddress}")

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Log.d(TAG, "Trying to pair with ${deviceName}")
            availableDevices.elementAt(p2).createBond()
        }
    }

}