package com.agesadev.telmedv2.data.services

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import com.agesadev.telmedv2.data.AppConstants
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

const val MESSAGE_READ: Int = 0
const val MESSAGE_WRITE: Int = 1
const val MESSAGE_TOAST: Int = 2

class BluetoothService(private val handler: Handler) {
    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private inner class ConnectThread(private val mmSocket: BluetoothSocket): Thread() {
        private val mmInStream: InputStream = mmSocket.inputStream
        private val mmOUtStream: OutputStream = mmSocket.outputStream
        private val mmBuffer: ByteArray = ByteArray(1024)

        override fun run() {
            var numBytes: Int

            while(true) {
                numBytes = try {
                    mmInStream.read(mmBuffer)
                } catch (e: IOException) {
                    Log.d(this::class.simpleName, "Input stream was disconnected")
                    break
                }

                val readMsg = handler.obtainMessage(MESSAGE_READ, numBytes, -1, mmBuffer)
                readMsg.sendToTarget()
            }
        }

        fun cancel() {
            try {
                mmSocket.close()
            } catch (e: IOException) {
                Log.e(this::class.simpleName, "Could not close the connect socket", e)
            }
        }

    }

    @SuppressLint("MissingPermission")
    public inner class ConnectToBluetoothThread(device: BluetoothDevice): Thread() {
        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE)  {
            device.createRfcommSocketToServiceRecord(AppConstants.BLUETOOTH_UUID)
        }

        override fun run() {
            bluetoothAdapter?.cancelDiscovery()
            mmSocket?.let { socket ->
                socket.connect()
                ConnectThread(socket).start()
            }
        }

        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                Log.e(this::class.simpleName, "cancel: Could not close client socket", e)
            }
        }
    }
}