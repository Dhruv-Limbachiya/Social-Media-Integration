package com.example.socialmediaintegration.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.socialmediaintegration.databinding.ActivityProfileBinding
 import com.example.socialmediaintegration.model.User

class ProfileActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        displayProfileDetails()
    }

    /**
     * Fetches user object from the bundle and set details in views.
     */
    private fun displayProfileDetails() {
        val profileBundle = intent

        val user = profileBundle?.getParcelableExtra<User>(MainActivity.USER_PROFILE_KEY)

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