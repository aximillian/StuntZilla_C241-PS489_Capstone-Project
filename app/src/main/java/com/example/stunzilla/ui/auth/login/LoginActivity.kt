package com.example.stunzilla.ui.auth.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stunzilla.R
import com.example.stunzilla.databinding.ActivityLoginBinding
import com.example.stunzilla.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.emailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isNotEmpty() || password.isNotEmpty()) {
                binding.googleButton.visibility = View.GONE
                binding.buttonContainer.visibility = View.VISIBLE
                binding.tvOr.text = ""
            } else {
                binding.googleButton.visibility = View.VISIBLE
                binding.buttonContainer.visibility = View.GONE
                binding.tvOr.text = "atau"
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }
}