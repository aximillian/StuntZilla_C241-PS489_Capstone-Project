package com.example.stunzilla.ui.auth.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.example.stunzilla.MainActivity
import com.example.stunzilla.R
import com.example.stunzilla.databinding.ActivityLoginBinding
import com.example.stunzilla.ui.auth.register.RegisterActivity
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = Firebase.auth

        setupEditText()
        observeAction()
    }

    private fun observeAction() {
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnGoogle.setOnClickListener {
            signInGoogle()
        }
    }

    private fun setupEditText() {
        binding.apply {
            emailEditText.addTextChangedListener(textWatcher)
            passwordEditText.addTextChangedListener(textWatcher)

            passwordEditText.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (passwordEditText.right - passwordEditText.compoundDrawables[2].bounds.width())) {
                        togglePasswordVisibility(passwordEditText)
                        return@setOnTouchListener true
                    }
                }
                false
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isNotEmpty() || password.isNotEmpty()) {
                binding.btnGoogle.visibility = View.GONE
                binding.buttonContainer.visibility = View.VISIBLE
                binding.tvOr.visibility = View.INVISIBLE
            } else {
                binding.btnGoogle.visibility = View.VISIBLE
                binding.buttonContainer.visibility = View.GONE
                binding.tvOr.visibility = View.VISIBLE
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun togglePasswordVisibility(passwordEditText: AppCompatEditText) {
        val selectionEnd = passwordEditText.selectionEnd
        if (isPasswordVisible) {
            passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(this, R.drawable.ic_lock),
                null,
                ContextCompat.getDrawable(this, R.drawable.ic_visibility),
                null
            )
        } else {
            passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(this, R.drawable.ic_lock),
                null,
                ContextCompat.getDrawable(this, R.drawable.ic_visibility_off),
                null
            )
        }
        isPasswordVisible = !isPasswordVisible
        passwordEditText.clearFocus()
        passwordEditText.requestFocus()
        passwordEditText.setSelection(selectionEnd)
    }

    private fun errorToast(message: String) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
    }

    private fun successToast(message: String) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
    }

    private fun signInGoogle() {
        showLoading(true)

        val credentialManager = CredentialManager.create(this)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result: GetCredentialResponse = credentialManager.getCredential(
                    request = request,
                    context = this@LoginActivity,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                showLoading(false)
                Log.d("Error", e.message.toString())
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        // Handle the successfully returned credential.
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        showLoading(true)
                        Log.e(TAG, "Received an invalid google id token response", e)
                    }
                } else {
                    showLoading(true)
                    Log.e(TAG, "Unexpected type of credential")
                }
            }

            else -> {
                showLoading(true)
                errorToast("Unexpected type of credential")
                Log.e(TAG, "Unexpected type of credential")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    successToast("Login successful.")
                    Log.d(TAG, "signInWithCredential:success")
                    val user: FirebaseUser? = auth.currentUser
                    updateUI(user)
                } else {
                    errorToast("Login failed.")
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
                showLoading(true)
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
}