package com.example.recuirtmentapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.recuirtmentapp.databinding.ActivityRegisterBinding
import com.example.recuirtmentapp.util.NODE_USER
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class register : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("USERS")
        binding.Login.setOnClickListener{
            val intent = Intent(this@register, com.example.recuirtmentapp.Login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.btnsignup.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val userNama = binding.fullname.text.toString().trim()


            if (email.isEmpty()) {
                binding.email.error = "Email required"
                binding.email.requestFocus()
                return@setOnClickListener

            }
            if (password.isEmpty() || password.length < 6) {
                binding.password.error = "Password required"
                binding.password.requestFocus()
                return@setOnClickListener

            }




            if (userNama.isEmpty()) {
                binding.fullname.error = "Full Name required"
                binding.fullname.requestFocus()
                return@setOnClickListener

            }


            registrasiUser(email, password, userNama)

        }


    }

    private fun registrasiUser(
        email: String,
        password: String,
        userNama: String,

    ) {
        val progressDialog = ProgressDialog(this@register)
        progressDialog.setTitle("Registratation User")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                saveUser(userNama,email,password, progressDialog)
            } else {
                val message = it.exception!!.toString()
                Toast.makeText(this, "Error : $message", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun saveUser(
        userNama: String,
        email: String,
        password: String,
        progressDialog: ProgressDialog
    ) {
        val currentUserId = auth.currentUser!!.uid
        ref = FirebaseDatabase.getInstance().reference.child(NODE_USER)
        val userMap = HashMap<String, Any>()
        userMap["id"] = currentUserId
        userMap["email"] = email
        userMap["userNama"] = userNama
        userMap["password"]= password
        userMap["phoneNumber"] = ""
        userMap["alamat"] = ""

        ref.child(currentUserId).setValue(userMap).addOnCompleteListener {
            if (it.isSuccessful) {
                progressDialog.dismiss()
                Toast.makeText(this, "Register is Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@register, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                val message = it.exception!!.toString()
                Toast.makeText(this, "Error : $message", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }

    }
}