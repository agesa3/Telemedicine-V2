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
    val devicesList: Set<BluetoothDevice>
): RecyclerView.Adapter<BluetoothDeviceAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val listItem = itemView.findViewById<TextView>(android.R.id.text1)

        fun setup(deviceName: String) {
            listItem.text = deviceName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setup(devicesList.elementAt(position).name)
    }

    override fun getItemCount(): Int {
        return devicesList.size
    }

}