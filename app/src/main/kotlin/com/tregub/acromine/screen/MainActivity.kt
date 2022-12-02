package com.tregub.acromine.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.tregub.acromine.databinding.ActivityMainBinding

/**
 * The root class that represents the owner of the entire user interface.
 *  Done without fragments to make it easier to implement.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}