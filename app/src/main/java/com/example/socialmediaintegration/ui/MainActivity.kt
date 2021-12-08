package com.example.socialmediaintegration.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.socialmediaintegration.util.FacebookLoginUtil
import com.example.socialmediaintegration.databinding.ActivityMainBinding
import com.facebook.AccessToken
import com.facebook.AccessTokenTracker
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        mBinding.loginButton.setPermissions(EMAIL)

        mBinding.loginButton.registerCallback(
            FacebookLoginUtil.mCallbackManager,
            object : FacebookCallback<LoginResult> {
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


        /**
         * Tracks user token.
         */
        object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(
                oldAccessToken: AccessToken?,
                currentAccessToken: AccessToken?
            ) {
                 if(FacebookLoginUtil.isUserLoggedIn()) {
                     currentAccessToken?.let {
                         FacebookLoginUtil.getUserInfo(it) { user ->
                             Log.i(TAG, "onCurrentAccessTokenChanged: User name : ${user.fullName}, Email : ${user.email}, Image : ${user.image} , Id : ${user.id}")
                         }
                     }
                 } else {
                     Toast.makeText(this@MainActivity,"Log out",Toast.LENGTH_SHORT).show()
                 }
            }

        }
    }

    companion object {
        const val EMAIL = "email"
        private const val TAG = "MainActivity"
    }
}