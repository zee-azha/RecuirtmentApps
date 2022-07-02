package com.kelompok2.recruitmentapp


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.kelompok2.recruitmentapp.databinding.ActivityJobApplicationBinding
import com.kelompok2.recruitmentapp.model.Application
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_apply_job.*
import java.text.SimpleDateFormat
import java.util.*





class JobApplication : AppCompatActivity() {

   lateinit var binding: ActivityJobApplicationBinding

    private lateinit var viewModel: ApplicationViewModel
    var filepath: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(ApplicationViewModel::class.java)
        binding.postjob.setOnClickListener {
            val name = binding.Name.text.toString().trim()
            val email = binding.edmail.text.toString().trim()
            val phone = binding.phoneNumber.text.toString().trim()


            if (name.isEmpty()) {
                binding.Name.error = "Title required"
                binding.Name.requestFocus()
                return@setOnClickListener

            }
            if (email.isEmpty()) {
                binding.edmail.error = "company required"
                binding.edmail.requestFocus()
                return@setOnClickListener

            }
            if (phone.isEmpty()) {
                binding.phoneNumber.error = "company required"
                binding.phoneNumber.requestFocus()
                return@setOnClickListener

            }
            chooseDoc()



        }

    }
    private fun chooseDoc() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startActivityForResult(intent,100)
        Log.d("success", "sucssed add ${filepath?.path}")




    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK ) {

            if (data?.data != null) {
                filepath = data.data
                UploadFile()

            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun formatDate(dateObject: Date): String? {
        val timeFormat = SimpleDateFormat("yyyyMMddhh:mmssa")
        return timeFormat.format(dateObject)
    }
    private fun UploadFile() {
        if(filepath == null) return
        val dateObject = Date(System.currentTimeMillis())
        val dateFormat = formatDate(dateObject)
        val jobId = intent.getStringExtra("JobId")
        val name = binding.Name.text.toString().trim()
        val email = binding.edmail.text.toString().trim()
        val phone = binding.phoneNumber.text.toString().trim()
        val application= Application()
        val filename = Name.text
        val sref = FirebaseStorage.getInstance().getReference("/Application/"+jobId+"-"+dateFormat+"_"+filename)
        sref.putFile(filepath!!)
            .addOnSuccessListener {

                application.jobId = jobId
                application.username = name
                application.email = email
                application.phoneNumber = phone
                application.cv = it.toString()
                viewModel.addApply(application)



                Toast.makeText(this, "Uploaded Succesfully", Toast.LENGTH_SHORT).show()
                Log.d("sucsess", "succsess${filepath?.path}")
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                Log.d("failed", "failed${filepath?.path}")
            }


    }

}