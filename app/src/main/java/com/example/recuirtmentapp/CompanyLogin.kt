package com.example.recuirtmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.recuirtmentapp.databinding.ActivityCompanyLoginBinding
import com.example.recuirtmentapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class CompanyLogin : AppCompatActivity() {
    lateinit var binding : ActivityCompanyLoginBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnlogin.setOnClickListener{
            val email: String = binding.email.text.toString().trim()
            val password: String = binding.password.text.toString().trim()

            if (email.isEmpty()){
                binding.email.error = "Email Tidak Boleh Kosong"
                binding.email.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.email.error = "Email Tidak Valid"
                binding.email.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.password.error = "Email Tidak Boleh Kosong"
                binding.password.requestFocus()
                return@setOnClickListener
            }

            loginUser(email,password)
        }

        binding.signup.setOnClickListener {
            val intent = Intent(this@CompanyLogin, CompanyRegister::class.java)
            startActivity(intent)
        }
    }
    private fun loginUser(email: String, password: String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Intent(this@CompanyLogin, AddJob::class.java).also { intent->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }
                else{
                    Toast.makeText(this,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            val intent = Intent(this@CompanyLogin, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}