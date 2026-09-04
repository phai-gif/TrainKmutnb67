package com.kmutnb.trainkmutnb67

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.kmutnb.trainkmutnb67.ui.AppRoot
import com.kmutnb.trainkmutnb67.ui.theme.Trainkmutnb67Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
        }

        setContent {
            Trainkmutnb67Theme {
                AppRoot()
            }
        }
    }
}