package com.example.stunzilla.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.stunzilla.R
import com.example.stunzilla.databinding.ActivityLoginBinding
import com.example.stunzilla.ui.auth.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var isPasswordVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupEditText()

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
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
}