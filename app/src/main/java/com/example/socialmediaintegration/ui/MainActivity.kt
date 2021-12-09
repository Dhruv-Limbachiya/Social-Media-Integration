package com.example.socialmediaintegration.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.socialmediaintegration.databinding.ActivityMainBinding
import com.example.socialmediaintegration.util.FacebookLoginUtil
import com.example.socialmediaintegration.util.GoogleSignInUtil
import com.facebook.AccessToken
import com.facebook.AccessTokenTracker
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        setUpFacebookLogin()

        setUpGoogleLogin()
    }



    /**
     * Sets up the facebook login and register callback for login responses.
     */
    private fun setUpFacebookLogin() {
        mBinding.fbLoginButton.setPermissions(EMAIL)

        mBinding.fbLoginButton.registerCallback(FacebookLoginUtil.mCallbackManager,
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
         * Tracks facebook user token.
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

    /**
     * Set up the google login feature
     */
    private fun setUpGoogleLogin() {
        mGoogleSignInClient = GoogleSignInUtil.getGoogleSignInClient(this)

        mBinding.googleSignInButton.setOnClickListener {
            promptGoogleSignInOptions()
        }
    }

    /**
     * Method responsible for prompting user to sign in using google accounts.
     */
    private fun promptGoogleSignInOptions() {
        // get the sign in intent from the google sign in client object.
        val googleSignInIntent = mGoogleSignInClient.signInIntent
        googleSignInLauncher.launch(googleSignInIntent)
    }

    // Google sign launcher
    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            result?.data?.let {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it)
                handleGoogleSignIn(task)
            }
        }
    }

    /**
     * Handle google sign in and update ui by fetching data from the launcher result.
     */
    private fun handleGoogleSignIn(task: Task<GoogleSignInAccount>?) {
        task?.let {
            try {
                // Get the result from the task and assigned it into google sign in account object.
                // The GoogleSignInAccount object contains information about the signed-in user, such as the user's name.
                val googleSignInAccount = task.getResult(ApiException::class.java)
                Log.i(TAG, "handleGoogleSignIn: Name : ${googleSignInAccount.displayName}, Email : ${googleSignInAccount.email} , Image : ${googleSignInAccount.photoUrl} ")
                // TODO : update ui by showing user info or navigate to profile screen.
            }catch (e: ApiException) {
                Log.w(TAG, "handleGoogleSignIn: ${e.statusCode}")
            }
        }
    }

    companion object {
        const val EMAIL = "email"
        private const val TAG = "MainActivity"
    }
}