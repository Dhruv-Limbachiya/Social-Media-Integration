package com.example.socialmediaintegration.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.socialmediaintegration.databinding.ActivityMainBinding
import com.example.socialmediaintegration.model.User
import com.example.socialmediaintegration.util.FacebookLoginUtil
import com.example.socialmediaintegration.util.GoogleSignInUtil
import com.example.socialmediaintegration.util.LoginType
import com.example.socialmediaintegration.util.TwitterLoginUtil
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
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        setUpFacebookLogin()

        setUpGoogleLogin()

        setUpTwitterLogin()
    }

    /**
     * adds callback to the twitter login button
     */
    private fun setUpTwitterLogin() {
        mBinding.twitterLoginButton.callback =  object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                result?.data?.let {
                    TwitterLoginUtil.getTwitterUserDetails(it) {
                        navigateToProfile(it,LoginType.TWITTER)
                    }
                }
            }

            override fun failure(exception: TwitterException?) {
                Log.e(TAG, "failure: ${exception?.localizedMessage}",exception )
            }
        }

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
                            navigateToProfile(user,LoginType.FACEBOOK)
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
                // navigate to profile screen.
                val user = User(
                    fullName = googleSignInAccount.displayName,
                    email = googleSignInAccount.email,
                    image = googleSignInAccount.photoUrl.toString(),
                    id = googleSignInAccount.id
                )

                navigateToProfile(user,LoginType.GOOGLE)

            }catch (e: ApiException) {
                Log.w(TAG, "handleGoogleSignIn: ${e.statusCode}")
            }
        }
    }


    /**
     * Navigate to profile screen with profile details.
     */
    private fun navigateToProfile(user: User, loginType: LoginType) {
        val intent = Intent(this@MainActivity, ProfileActivity::class.java).apply {
            putExtra(USER_PROFILE_KEY,user)
            putExtra(LOGIN_TYPE_KEY,loginType)
        }
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mBinding.twitterLoginButton.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        const val EMAIL = "email"
        const val TAG = "MainActivity"
        const val USER_PROFILE_KEY = "user_profile_key"
        const val LOGIN_TYPE_KEY = "login_type_key"
    }
}