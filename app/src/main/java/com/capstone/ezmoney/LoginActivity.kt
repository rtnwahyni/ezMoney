package com.capstone.ezmoney

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.ezmoney.data.request.LoginRequest
import com.capstone.ezmoney.data.response.LoginResponse
import com.capstone.ezmoney.data.retrofit.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.btnLogin)
        registerTextView = findViewById(R.id.tvRegister)

        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val apiService = RetrofitClient.retrofit.create(com.capstone.ezmoney.data.retrofit.ApiService::class.java)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = "Enter a valid email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                passwordEditText.error = "Password cannot be empty"
                return@setOnClickListener
            }

            val loginRequest = LoginRequest(email, password)
            apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val token = response.body()?.token
                        if (token != null) {
                            sharedPreferences.edit().putString("token", token).apply()
                            Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Login failed: Missing token", Toast.LENGTH_SHORT).show()
                        }
                    } else {

                        try {
                            val errorBody = response.errorBody()?.string()
                            Log.e("LoginError", "Error response: $errorBody")

                            val errorMessage = JSONObject(errorBody).getString("message")
                            Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()

                            if (errorMessage.isEmpty()) {
                                Toast.makeText(this@LoginActivity, "Login failed: Unknown error", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Log.e("LoginError", "Error parsing response: ${e.message}")
                            Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("LoginFailure", "Error: ${t.message}")
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        registerTextView.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
