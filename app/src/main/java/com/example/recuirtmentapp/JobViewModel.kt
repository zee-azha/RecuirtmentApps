package com.example.recuirtmentapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recuirtmentapp.model.Job
import com.example.recuirtmentapp.util.NODE_JOB
import com.google.firebase.database.FirebaseDatabase


class JobViewModel: ViewModel() {

    private val dbJob = FirebaseDatabase.getInstance().getReference(NODE_JOB)

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

    fun addJob(job: Job){
        job.id = dbJob.push().key
        dbJob.child(job.id!!).setValue(job).addOnCompleteListener{
            if (it.isSuccessful){
                _result.value  = null
            }else{
                _result.value = it.exception
            }
        }


    }
}