package com.agesadev.telmedv2.presentation.forms

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.agesadev.telmedv2.databinding.FragmentBluetoothDialogBinding

class BluetoothDialog : DialogFragment() {
    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private lateinit var pairedDevices: Set<BluetoothDevice>

    private lateinit var binding: FragmentBluetoothDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = layoutInflater

        binding = FragmentBluetoothDialogBinding.inflate(inflater)

        builder.setView(binding.root)
            .setTitle("Bluetooth pairing")

        enableBluetooth()
        checkPermission()

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

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            listDevices()
        }
    }

    private fun listDevices() {
        pairedDevices = bluetoothAdapter.bondedDevices

        val adapter = BluetoothDeviceAdapter(pairedDevices)

        binding.pairedDevicesList.adapter = adapter


    }


}