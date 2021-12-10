package com.example.socialmediaintegration

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.*

/**
 * Created By Dhruv Limbachiya on 10-12-2021 11:35 AM.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val twitterConfig = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(
                getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret_key)
            ))
            .debug(true)
            .build()

        // Initialize twitter with given configuration
        Twitter.initialize(twitterConfig)
    }
}