package com.example.socialmediaintegration.util

import android.util.Log
import com.example.socialmediaintegration.model.User
import com.example.socialmediaintegration.ui.MainActivity
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.Callback

/**
 * Created By Dhruv Limbachiya on 10-12-2021 11:53 AM.
 */
object TwitterLoginUtil {

    /**
     * Get twitter user details.
     */
    fun getTwitterUserDetails(session: TwitterSession,userDetails: (User) -> Unit) {
        TwitterCore.getInstance().getApiClient(session).accountService
            .verifyCredentials(false,false,false)
            .enqueue(object : Callback<com.twitter.sdk.android.core.models.User>() {
                override fun success(result: Result<com.twitter.sdk.android.core.models.User>?) {
                    val name = result?.data?.name
                    val userName = result?.data?.screenName
                    val profileImageUrl = result?.data?.profileImageUrl?.replace("_normal", "")
                    val user = User(name , "" ,profileImageUrl,userName)
                    userDetails(User("","","",""))
                }

                override fun failure(exception: TwitterException?) {
                    Log.e(MainActivity.TAG, "failure: ${exception?.localizedMessage}",exception )
                }
            })
    }
}
