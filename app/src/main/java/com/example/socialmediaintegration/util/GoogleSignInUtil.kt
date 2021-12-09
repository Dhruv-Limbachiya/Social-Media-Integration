package com.example.socialmediaintegration.util

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

/**
 * Created By Dhruv Limbachiya on 09-12-2021 11:02 AM.
 */
object GoogleSignInUtil {

    init {

    }

    /**
     * Configure the sign in options for the google sign in client.
     */
    private fun configGoogleSignInOptions(): GoogleSignInOptions {
        // Configure sign-in to request the user's ID, email address, and basic profile.
        // ID and basic profile are included in DEFAULT_SIGN_IN option.
        return GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()
    }

    /**
     * Creates & gets google sign in client object.
     */
    fun getGoogleSignInClient(context: Context) =
        GoogleSignIn.getClient(context, configGoogleSignInOptions())

    /**
     * Check user is already logged in or not.
     */
    fun isGoogleUserAlreadyLoggedIn(context: Context): Boolean {
        // Check for existing Google Sign In account, if the user is already signed in the GoogleSignInAccount will be non-null.
        val lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(context)

        if (lastSignedInAccount != null) {
            return true
        }
        return false
    }
}