package com.example.socialmediaintegration.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialmediaintegration.R
import com.example.socialmediaintegration.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}