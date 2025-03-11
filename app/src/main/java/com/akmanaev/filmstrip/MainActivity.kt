package com.akmanaev.filmstrip

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.akmanaev.filmstrip.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_container)

        navView.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(this) {
            if (!findNavController(R.id.nav_host_fragment_container).popBackStack()) {
                finish()
            }
        }
    }

}


