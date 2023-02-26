package com.agesadev.telmedv2.presentation.forms

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.jvm.javaMethod


class BluetoothDeviceAdapter(val deviceNames: Set<BluetoothDevice>): RecyclerView.Adapter<BluetoothDeviceAdapter.ViewHolder>() {
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
        holder.setup(deviceNames.elementAt(position).name)
        holder.listItem.setOnClickListener {
            pairDevice(deviceNames.elementAt(position), holder.itemView.context)
        }
    }

    override fun getItemCount(): Int {
        return deviceNames.size
    }

    private fun pairDevice(device: BluetoothDevice, context: Context) {
        device::class.java.getMethod("setPairingConfirmation", Boolean::class.java).invoke(device, true)
        device::class.java.getMethod("cancelPairingUserInput", Boolean::class.java).invoke(device)
    }
}