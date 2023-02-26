package com.agesadev.telmedv2.presentation.forms

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.agesadev.telmedv2.data.services.BluetoothService
import kotlin.reflect.jvm.javaMethod


class BluetoothDeviceAdapter(
    val devicesList: Set<BluetoothDevice>,
    private val mBluetoothService: BluetoothService
): RecyclerView.Adapter<BluetoothDeviceAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val listItem = itemView.findViewById<TextView>(android.R.id.text1)

        fun setup(deviceName: String) {
            listItem.text = deviceName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothDeviceAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return BluetoothDeviceAdapter.ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: BluetoothDeviceAdapter.ViewHolder, position: Int) {
        holder.setup(devicesList.elementAt(position).name)
        holder.listItem.setOnClickListener {
            mBluetoothService.ConnectToBluetoothThread(devicesList.elementAt(position))
        }
    }

    override fun getItemCount(): Int {
        return devicesList.size
    }

    private fun pairDevice(device: BluetoothDevice, context: Context) {
        device::class.java.getMethod("setPairingConfirmation", Boolean::class.java).invoke(device, true)
        device::class.java.getMethod("cancelPairingUserInput", Boolean::class.java).invoke(device)
    }


}