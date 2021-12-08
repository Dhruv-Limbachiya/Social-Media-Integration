package com.example.socialmediaintegration.util

import android.os.Bundle
import com.example.socialmediaintegration.model.User
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.GraphRequest
import org.json.JSONException
import java.lang.NullPointerException


/**
 * Created By Dhruv Limbachiya on 08-12-2021 01:35 PM.
 */
object FacebookLoginUtil {

    // Handle facebook login responses.
    val mCallbackManager = CallbackManager.Factory.create()

    /**
     * Checks user is logged in or not.
     */
    fun isUserLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }

    /**
     * Requests user details to Facebook platform using GraphAPI.
     */
    fun getUserInfo(accessToken: AccessToken, userDetails : (User) -> Unit) {
        // Creates a new Request configured to retrieve a user's own profile.
        val graphRequest = GraphRequest.newMeRequest(
            accessToken
        ) { obj, _ ->

            obj?.let {
                try {
                    val name = obj.getString("name")
                    val email = obj.getString("email")
                    val id = obj.getString("id")
                    val image = "http://graph.facebook.com/$id/picture?type=normal"

                    val user = User(
                        name,
                        email,
                        image,
                        id
                    )
                    userDetails(user)

                }catch (jsonException: JSONException) {
                    jsonException.printStackTrace()
                }
                catch (nullPointerException: NullPointerException) {
                    nullPointerException.printStackTrace()
                }
            }
        }

        val bundle = Bundle().apply {
            putString("fields", "id, name, email, gender")
        }

        graphRequest.parameters = bundle
        graphRequest.executeAsync()
    }
}