package com.capstone.ezmoney

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.ezmoney.data.request.RegisterRequest
import com.capstone.ezmoney.data.response.RegisterResponse
import com.capstone.ezmoney.data.retrofit.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var loginTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameEditText = findViewById(R.id.username)
        emailEditText = findViewById(R.id.registerEmail)
        passwordEditText = findViewById(R.id.registerPassword)
        registerButton = findViewById(R.id.btnRegister)
        loginTextView = findViewById(R.id.tvAlreadyHaveAccount)

        val apiService = RetrofitClient.retrofit.create(com.capstone.ezmoney.data.retrofit.ApiService::class.java)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty()) {
                usernameEditText.error = "Username cannot be empty"
                return@setOnClickListener
            }
            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = "Enter a valid email"
                return@setOnClickListener
            }
            if (password.length < 8) {
                passwordEditText.error = "Password must be at least 8 characters"
                return@setOnClickListener
            }
            val registerRequest = RegisterRequest(username, email, password)
            apiService.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        try {
                            val errorBody = response.errorBody()?.string()
                            val errorMessage = JSONObject(errorBody).getString("message")
                            Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Log.e("RegisterError", "Error parsing response: ${e.message}")
                            Toast.makeText(this@RegisterActivity, "Registration failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    // Gagal melakukan request
                    Log.e("RegisterFailure", "Error: ${t.message}")
                    Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        loginTextView.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}