package com.continentalbakingco.balancesheet20.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.continentalbakingco.balancesheet20.databinding.ActivityMainBinding
import com.continentalbakingco.balancesheet20.model.User
import java.util.zip.Inflater


class MainActivity : AppCompatActivity(){
    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

}