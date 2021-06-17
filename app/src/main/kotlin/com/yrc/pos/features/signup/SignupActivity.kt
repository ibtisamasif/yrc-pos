package com.yrc.pos.features.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.yrc.pos.R
import com.yrc.pos.core.Constants
import com.yrc.pos.core.YrcBaseActivity
import com.yrc.pos.core.enums.DialogTheme
import com.yrc.pos.core.providers.AlertDialogProvider
import kotlinx.android.synthetic.main.activity_signup.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

class SignupActivity : YrcBaseActivity() {

    private val RC_SIGN_IN = 1111
    private var gmailId: String? = Constants.EMPTY_STRING
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    fun onSignUpButtonClicked(signUpButton: View) {
        gmailId = Constants.EMPTY_STRING
        signUpUsingNumber()
    }

    private fun signUpUsingNumber() {
        if (checkValidations()) {
//            var individualSignUpRequest = SignUpRequest()
//            individualSignUpRequest.number = individualSignUp_mobileNumberComponent.getPhoneNumberWithPrefix()
//            individualSignUpRequest.type = UserType.INDIVIDUAL.name
//
//            APiManager.signUpApi(this, this, individualSignUpRequest)
        }
    }

    private fun signUpUsingGmailId() {
//        var individualSignUpRequest = SignUpRequest()
//        individualSignUpRequest.email = gmailId
//        individualSignUpRequest.type = UserType.INDIVIDUAL.name
//
//        APiManager.signUpApi(this, this, individualSignUpRequest)
    }

    private fun checkValidations() : Boolean {
        if (individualSignUp_mobileNumberComponent.getPhoneNumber().length < 10) {
            individualSignUp_mobileNumberComponent.setError(getString(R.string.please_enter_valid_cell_number))
            return false
        }

        return true
    }

    fun onSignUpWithGmailClicked(view: View) {
//        mGoogleSignInClient.signOut() //Sign out any already Google signed in user so that user can select account on every attempt
//        startActivityForResult(mGoogleSignInClient.signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            handleGoogleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            gmailId = account!!.email
            signUpUsingGmailId()
        } catch (e: ApiException) {
            AlertDialogProvider.showFailureDialog(this, DialogTheme.ThemeWhite)
        }
    }
}