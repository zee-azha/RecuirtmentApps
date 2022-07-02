package com.kelompok2.recruitmentapp

import android.app.Activity.RESULT_OK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.kelompok2.recruitmentapp.databinding.FragmentApplyJobBinding

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.kelompok2.recruitmentapp.model.Application


import kotlinx.android.synthetic.main.fragment_apply_job.*

import com.google.firebase.storage.FirebaseStorage



import java.util.*






class ApplyJob : Fragment() {

    private var _binding: FragmentApplyJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ApplicationViewModel
    var filepath: Uri? = null
    lateinit var intent: Intent





    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = FragmentApplyJobBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(ApplicationViewModel::class.java)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {








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
        Log.d("success","sucssed add ${filepath?.path}",)

        UploadFile()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK ) {

            if (data?.data != null) {
                filepath = data.data


            } else {
                Toast.makeText(context, "No file chosen", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun UploadFile() {
        if(filepath == null) return
        val bundle = arguments
        val jobId = bundle!!.getString("JobId")
            val filename = Name.text
            val sref = FirebaseStorage.getInstance().getReference("/Application/$jobId+_+$filename")
            sref.putFile(filepath!!)
                .addOnSuccessListener {
                    val name = binding.Name.text.toString().trim()
                    val email = binding.edmail.text.toString().trim()
                    val phone = binding.phoneNumber.text.toString().trim()


                    val application= Application()
                    application.jobId = jobId
                    application.username = name
                    application.email = email
                    application.phoneNumber = phone
                    application.cv = it.toString()
                    viewModel.addApply(application)



                    Toast.makeText(activity, "Uploaded Succesfully", Toast.LENGTH_SHORT).show()
                    Log.d("sucsess","succsess${filepath?.path}",)
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
                    Log.d("failed","failed${filepath?.path}",)
                }


        }



}