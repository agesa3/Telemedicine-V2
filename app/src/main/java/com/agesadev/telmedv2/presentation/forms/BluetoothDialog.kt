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

class BluetoothDialog : DialogFragment() {
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter

    private lateinit var pairedDevices: Set<BluetoothDevice>
    private var availableDevices: MutableSet<BluetoothDevice> = mutableSetOf()

    private lateinit var binding: FragmentBluetoothDialogBinding

    private lateinit var mBluetoothService: BluetoothService

    private lateinit var availableDevicesAdapter: BluetoothDeviceAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = layoutInflater

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        requireContext().registerReceiver(receiver, filter)


        bluetoothManager = requireContext().getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.adapter

        binding = FragmentBluetoothDialogBinding.inflate(inflater)
        mBluetoothService = BluetoothService(mHandler)

        builder.setView(binding.root)
            .setTitle("Bluetooth pairing")

        enableBluetooth()
//        checkPermission()
        listDevices()
        listAvailableDevices()

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

//    private fun checkPermission() {
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.BLUETOOTH_CONNECT
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            listDevices()
//        } else {
//            Toast.makeText(requireContext(), "Bluetooth permission not granted", Toast.LENGTH_LONG).show()
//            dismiss()
//        }
//    }

    @SuppressLint("MissingPermission")
    private fun listDevices() {
        pairedDevices = bluetoothAdapter.bondedDevices

        val adapter = BluetoothDeviceAdapter(pairedDevices, mBluetoothService)

        binding.pairedDevicesList.adapter = adapter
        binding.pairedDevicesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    private fun listAvailableDevices() {
        availableDevicesAdapter = BluetoothDeviceAdapter(availableDevices, mBluetoothService)
        binding.availableDevicesList.adapter = availableDevicesAdapter
        binding.availableDevicesList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    private val mHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            val readBuf: ByteArray = msg.obj as ByteArray
            val readMessage = String(readBuf, 0, msg.arg1)
            Toast.makeText(requireContext(), readMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private val receiver = object: BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val action: String? = p1?.action
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device: BluetoothDevice? = p1.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if(device != null) {
                        availableDevices.add(device)
                        availableDevicesAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        requireContext().unregisterReceiver(receiver)
    }


}