package com.kelompok2.recruitmentapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kelompok2.recruitmentapp.databinding.FragmentDisplayJobBinding
import com.kelompok2.recruitmentapp.model.Job
import com.kelompok2.recruitmentapp.util.JobAdapter


class DisplayJob : Fragment() {
    private var _binding: FragmentDisplayJobBinding? = null
    private val binding get() = _binding!!
    private val adapter = JobAdapter()
    private lateinit var viewModel: JobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDisplayJobBinding.inflate(inflater,container,false)
        viewModel = ViewModelProviders.of(this).get(JobViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : JobAdapter.OnItemClickCallback{
            override fun onItemClicked(job: Job) {
                showSelectedJob(job)
            }
        })



        binding.apply {
            activity?.let{
                rvJob.layoutManager = LinearLayoutManager(it)
            }
            rvJob.adapter = adapter
            viewModel.job.observe(viewLifecycleOwner,{
                adapter.addJobs(it)
            })

            viewModel.getRealtimeUpdate()
        }
    }

    private fun showSelectedJob(job: Job){
        val i = Job(
            job.id,
            job.jobTitle,
            job.jobType,
            job.companyName,
            job.jobCategory,
            job.jobDescription,
            job.jobResponsibilities,
            job.skills,
            job.education,
            job.location,
            job.salary,

            )
      activity?.let {
          val intentWithExtraData = Intent(it,JobDetails::class.java)
          intentWithExtraData.putExtra(JobDetails.EXTRA_JOB, i)
          startActivity(intentWithExtraData)
      }


    }

}