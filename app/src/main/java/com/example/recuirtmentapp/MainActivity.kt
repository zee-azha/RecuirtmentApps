package com.example.recuirtmentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recuirtmentapp.R
import com.example.recuirtmentapp.databinding.ActivityAddJobBinding
import com.example.recuirtmentapp.databinding.ActivityMainBinding
import com.example.recuirtmentapp.model.Job
import com.example.recuirtmentapp.util.JobAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: JobAdapter
    private lateinit var viewModel: JobViewModel
    private lateinit var list: ArrayList<Job>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =ViewModelProviders.of(this).get(JobViewModel::class.java)

        adapter = JobAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : JobAdapter.OnItemClickCallback{
            override fun onItemClicked(job: Job) {
                showSelectedJob(job)
            }
        })



        binding.apply {
            rvJob.layoutManager = LinearLayoutManager(this@MainActivity)
            rvJob.adapter = adapter
            viewModel.job.observe(this@MainActivity,{
                adapter.addJobs(it)
            })

            viewModel.getRealtimeUpdate()
        }



    }

//    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean {
//            return  true
//        }
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//          var position = viewHolder.adapterPosition
//          var currentJob = adapter.jobs[position]
//
//
//            when(direction){
//                ItemTouchHelper.RIGHT
//            }
//        }
//
//    }

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
        val intentWithExtraData = Intent(this@MainActivity, JobDetails::class.java)
        intentWithExtraData.putExtra(JobDetails.EXTRA_JOB, i)
        startActivity(intentWithExtraData)

    }




}