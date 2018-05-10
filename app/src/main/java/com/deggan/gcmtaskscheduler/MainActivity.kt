package com.deggan.gcmtaskscheduler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.jetbrains.anko.appcompat.v7.switchCompat
import org.jetbrains.anko.relativeLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        relativeLayout {
            //tools:context = com.deggan.gcmtaskscheduler.MainActivity //not support attribute
            switchCompat {
                text = "Background Service"
                setOnCheckedChangeListener { compoundButton, b ->
                    if (compoundButton.isChecked) {
                        Log.d("HASIL", "TRUE")
                    } else {
                        Log.d("HASIL", "FALSE")
                    }
                }
            }
        }
    }
}
