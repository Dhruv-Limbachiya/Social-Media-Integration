package com.example.socialmediaintegration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.socialmediaintegration.databinding.ActivityMainBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    // Handle facebook login responses.
    private lateinit var mCallbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mCallbackManager = CallbackManager.Factory.create()

        mBinding.loginButton.setPermissions(EMAIL)

        mBinding.loginButton.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                Log.i(TAG, "onCancel: Register Callback")
            }

            override fun onError(error: FacebookException) {
                Log.i(TAG, "onError: Register Callback")
            }

            override fun onSuccess(result: LoginResult) {
                Log.i(TAG, "onSuccess: Register Callback")
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data) // pass the login result to Login Manager via CallbackManager.
        super.onActivityResult(requestCode, resultCode, data)
    }


    companion object {
        const val EMAIL = "email"
        private const val TAG = "MainActivity"
    }
}