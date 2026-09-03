package com.kmutnb.trainkmutnb67

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kmutnb.trainkmutnb67.ui.AppRoot
import com.kmutnb.trainkmutnb67.ui.theme.Trainkmutnb67Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Trainkmutnb67Theme {
                AppRoot()
            }
        }
    }
}
