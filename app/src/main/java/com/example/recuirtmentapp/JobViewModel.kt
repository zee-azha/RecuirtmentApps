package com.example.recuirtmentapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recuirtmentapp.model.Job
import com.example.recuirtmentapp.util.NODE_JOB
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase


class JobViewModel: ViewModel() {

    private val dbJob = FirebaseDatabase.getInstance().getReference(NODE_JOB)


    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

    private val _Job = MutableLiveData<Job>()
    val job: LiveData<Job> get() = _Job

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

    private val childEventListener = object: ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val job = snapshot.getValue(Job::class.java)
            job?.id = snapshot.key
            _Job.value = job!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            TODO("Not yet implemented")
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    }

    fun getRealtimeUpdate(){
        dbJob.addChildEventListener(childEventListener)
    }

    override fun onCleared() {
        super.onCleared()
        dbJob.removeEventListener(childEventListener)
    }

}