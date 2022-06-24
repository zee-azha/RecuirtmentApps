package com.example.recuirtmentapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.recuirtmentapp.databinding.ActivityAddJobBinding
import com.example.recuirtmentapp.model.Job

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_job.*

class AddJob : AppCompatActivity() {
    lateinit var binding: ActivityAddJobBinding

    lateinit var viewModel: JobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this).get(JobViewModel::class.java)
        viewModel.result.observe(this,{
            val message = if(it == null){
                getString(R.string.Addjob)
            }else{
                getString(R.string.error,it.message)
            }
            Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()

        })



        binding.postjob.setOnClickListener {
            val title = binding.jobtitle.text.toString().trim()
            val company = binding.company.text.toString().trim()
            val category = binding.category.text.toString().trim()
            val responsibilities = binding.jobResp.text.toString().trim()
            val skill = binding.skills.text.toString().trim()
            val educations = binding.education.text.toString().trim()
            val location = binding.joblocation.text.toString().trim()
            val salaries = binding.salary.text.toString().trim()
            val type = binding.jobtype.text.toString().trim()



            if (title.isEmpty()) {
                binding.jobtitle.error = "Title required"
                binding.jobtitle.requestFocus()
                return@setOnClickListener

            }
            if (company.isEmpty()) {
                binding.company.error = "company required"
                binding.company.requestFocus()
                return@setOnClickListener

            }
            if (responsibilities.isEmpty()) {
                binding.jobResp.error = "responsibilities required"
                binding.jobResp.requestFocus()
                return@setOnClickListener

            }
            if (skill.isEmpty()) {
                binding.skills.error = "skills required"
                binding.skills.requestFocus()
                return@setOnClickListener

            }
            if (educations.isEmpty()) {
                binding.education.error = "education required"
                binding.education.requestFocus()
                return@setOnClickListener

            }
            if (location.isEmpty()) {
                binding.joblocation.error = "location required"
                binding.joblocation.requestFocus()
                return@setOnClickListener

            }
            if (salaries.isEmpty()) {
                binding.salary.error = "salaries required"
                binding.salary.requestFocus()
                return@setOnClickListener

            }
            if (type.isEmpty()) {
                binding.jobtype.error = "type required"
                binding.jobtype.requestFocus()
                return@setOnClickListener

            }
            val job =Job()
            job.jobTitle = title
            job.companyName = company
            job.jobCategory = category
            job.jobResponsibilities = responsibilities
            job.skills = skill
            job.education = educations
            job.salary = salaries
            job.location = location
            job.jobType =type
            viewModel.addJob(job)

        }


    }








}