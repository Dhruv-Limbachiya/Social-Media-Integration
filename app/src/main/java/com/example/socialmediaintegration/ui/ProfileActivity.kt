package com.example.socialmediaintegration.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.socialmediaintegration.databinding.ActivityProfileBinding
 import com.example.socialmediaintegration.model.User
import com.example.socialmediaintegration.util.GoogleSignInUtil
import com.example.socialmediaintegration.util.LoginType
import com.example.socialmediaintegration.util.LoginType.GOOGLE
import com.example.socialmediaintegration.util.LoginType.TWITTER
import com.example.socialmediaintegration.util.LoginType.FACEBOOK
import com.facebook.login.LoginManager
import com.google.android.material.snackbar.Snackbar

class ProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityProfileBinding
    private var loginType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        displayProfileDetails()

        mBinding.btnLogout.setOnClickListener {
            loginType?.let {
                when(loginType) {
                    GOOGLE.name -> {
                        GoogleSignInUtil.signOutFromGoogle(this) {
                            if(it) {
                                finish()
                            } else {
                                Snackbar.make(mBinding.root,"Unable to sign out",Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }

                    FACEBOOK.name -> {
                        LoginManager.getInstance().logOut()
                        finish()
                    }

                    TWITTER.name -> {

                    }
                }
            }
        }
    }

    /**
     * Fetches user object from the bundle and set details in views.
     */
    private fun displayProfileDetails() {
        val profileBundle = intent

        val user = profileBundle?.getParcelableExtra<User>(MainActivity.USER_PROFILE_KEY)
        loginType = profileBundle?.getStringExtra(MainActivity.LOGIN_TYPE_KEY)

        user?.let {
            it.id?.let { id ->
                mBinding.etId.setText(id)
            }

            it.fullName?.let { name ->
                mBinding.etName.setText(name)
            }

            it.email?.let { email ->
                mBinding.etEmail.setText(email)
            }

            it.image?.let { photoUrl ->
                Glide.with(this)
                    .load(photoUrl)
                    .circleCrop()
                    .into(mBinding.imageviewProfile)
            }
        }
    }
}