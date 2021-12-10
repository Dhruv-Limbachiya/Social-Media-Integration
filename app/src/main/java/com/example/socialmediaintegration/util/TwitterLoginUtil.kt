package com.example.socialmediaintegration.util

import android.util.Log
import android.widget.Toast
import com.example.socialmediaintegration.ui.MainActivity
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession

/**
 * Created By Dhruv Limbachiya on 10-12-2021 11:53 AM.
 */
object TwitterLoginUtil {

    val callback =  object : Callback<TwitterSession>() {
        override fun success(result: Result<TwitterSession>?) {
            // todo : get user details.
        }

        override fun failure(exception: TwitterException?) {
            Log.e(MainActivity.TAG, "failure: ${exception?.localizedMessage}",exception )
        }
    }
}
