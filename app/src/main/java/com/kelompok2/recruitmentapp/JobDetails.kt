package com.kelompok2.recruitmentapp

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kelompok2.recruitmentapp.databinding.ActivityJobDetailsBinding
import com.kelompok2.recruitmentapp.model.Job

class JobDetails : AppCompatActivity() {

    private lateinit var binding: ActivityJobDetailsBinding

    companion object{
        const val  EXTRA_JOB = "extra_job"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val job = intent.getParcelableExtra<Job>(EXTRA_JOB) as Job
        val jobId = job.id
        var jobTitle = job.jobTitle
        var jobCompany = job.companyName
        var jobCategory = job.jobCategory
        var jobDescription = job.jobDescription
        var jobResponsibilities = job.jobResponsibilities
        var jobSkill = job.skills
        var jobEdu = job.education
        var jobSalary = job.salary
        var jobLocation = job.location
        var jobType = job.jobType


        val bundle = Bundle()
        bundle.putString(EXTRA_JOB, jobId)

        binding.apply {
            jobtitle.text = jobTitle.toString()
            company.text = jobCompany.toString()
            category.text = jobCategory.toString()
            jobdescription.text = jobDescription.toString()
            jobResp.text = jobResponsibilities.toString()
            skills.text= jobSkill.toString()
            education.text = jobEdu.toString()
            joblocation.text = jobLocation.toString()
            salary.text = jobSalary.toString()
            jobtype.text = jobType.toString()


        }
        binding.ApplyJob.setOnClickListener {
            val intentWithExtraData = Intent(this@JobDetails,JobApplication::class.java)
            intentWithExtraData.putExtra("JobId", jobId.toString())
            Log.d(TAG,"jobid: $jobId")
            startActivity(intentWithExtraData)
        }
    }

}