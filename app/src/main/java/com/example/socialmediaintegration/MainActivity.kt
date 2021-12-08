package com.example.socialmediaintegration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialmediaintegration.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)


    }
}