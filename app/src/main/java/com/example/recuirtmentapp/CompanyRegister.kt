package com.example.recuirtmentapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.recuirtmentapp.databinding.ActivityCompanyRegisterBinding
import com.example.recuirtmentapp.databinding.ActivityRegisterBinding
import com.example.recuirtmentapp.util.NODE_Comp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_company_login.*

class CompanyRegister : AppCompatActivity() {

    lateinit var binding: ActivityCompanyRegisterBinding
    private lateinit var auth: FirebaseAuth
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("COMPANY")
        binding.Login.setOnClickListener{
            val intent = Intent(this@CompanyRegister, CompanyLogin::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.btnsignup.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val phoneNumber = binding.phoneNumber.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val compNama = binding.company.text.toString().trim()


            if (email.isEmpty()) {
                binding.email.error = "Email required"
                binding.email.requestFocus()
                return@setOnClickListener

            }
            if (phoneNumber.isEmpty()) {
                binding.email.error = "Phonenumber required"
                binding.email.requestFocus()
                return@setOnClickListener

            }
            if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
                binding.email.error = "Phonenumber Wrong"
                binding.email.requestFocus()
                return@setOnClickListener

            }
            if (password.isEmpty() || password.length < 6) {
                binding.password.error = "Password required"
                binding.password.requestFocus()
                return@setOnClickListener

            }




            if (compNama.isEmpty()) {
                binding.company.error = "Company Name required"
                binding.company.requestFocus()
                return@setOnClickListener

            }


            registrasiUser(email, password, compNama,phoneNumber)

        }


    }

    private fun registrasiUser(
        email: String,
        password: String,
        userNama: String,
        phoneNumber: String,

        ) {
        val progressDialog = ProgressDialog(this@CompanyRegister)
        progressDialog.setTitle("Registratation Company")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                saveUser(userNama,email,password,phoneNumber, progressDialog)
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
        phoneNumber: String,
        progressDialog: ProgressDialog
    ) {
        val currentUserId = auth.currentUser!!.uid
        ref = FirebaseDatabase.getInstance().reference.child(NODE_Comp)
        val userMap = HashMap<String, Any>()
        userMap["id"] = currentUserId
        userMap["email"] = email
        userMap["companyName"] = userNama
        userMap["password"]= password
        userMap["phoneNumber"] = phoneNumber
        userMap["alamat"] = ""

        ref.child(currentUserId).setValue(userMap).addOnCompleteListener {
            if (it.isSuccessful) {
                progressDialog.dismiss()
                Toast.makeText(this, "Register is Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@CompanyRegister, AddJob::class.java)
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