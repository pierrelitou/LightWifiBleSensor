package com.example.sensormanagerblifi

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class MainActivity : Activity(), View.OnClickListener {

    private var lightBtn: Button? = null
    private var wifiBtn: Button? = null
    private var bleBtn: Button? = null
    private var text: TextView? = null
    private var lSensorManager: SensorManager? = null
    private var lSensor: Sensor? = null
    var lux: Float? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lightBtn = findViewById(R.id.lightButton)
        wifiBtn = findViewById(R.id.wifiButton)
        bleBtn = findViewById(R.id.bleButton)
        text = findViewById(R.id.textButton)
        lSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lSensor = lSensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        lightBtn!!.setOnClickListener(this)
        wifiBtn!!.setOnClickListener(this)
        bleBtn!!.setOnClickListener(this)

    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.lightButton -> text!!.text = lux.toString()
                R.id.wifiButton -> text!!.text = "Wifi"
                R.id.bleButton -> text!!.text = "BLE"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lSensorManager!!.registerListener(
            lightListener, lSensor,
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    override fun onStop() {
        super.onStop()
        lSensorManager!!.unregisterListener(lightListener)
    }

    private var lightListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, acc: Int) {}
        @SuppressLint("SetTextI18n")
        override fun onSensorChanged(event: SensorEvent) {
            lux = event.values[0]
        }
    }

}

