package com.kelompok2.recruitmentapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kelompok2.recruitmentapp.databinding.RecylerViewJobBinding
import com.kelompok2.recruitmentapp.model.Job


class JobAdapter: RecyclerView.Adapter<JobAdapter.ViewHolder>(){
    private val list= ArrayList<Job>()

    private var onItemClickCallback: OnItemClickCallback? =null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = RecylerViewJobBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)


    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
       return list.size
    }

    fun  addJobs(job: Job){
        if (!list.contains(job)){
            list.add(job)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: RecylerViewJobBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job){
            with(binding){
                tvJobTitle.text = job.jobTitle
                tvCompany.text = job.companyName
                cvJob.setOnClickListener{
                    onItemClickCallback?.onItemClicked(job)
                }
            }
        }

    }
    interface OnItemClickCallback {
        fun onItemClicked(job :Job)

    }
}


