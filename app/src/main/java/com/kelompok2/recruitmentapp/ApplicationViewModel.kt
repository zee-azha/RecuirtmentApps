package com.kelompok2.recruitmentapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kelompok2.recruitmentapp.model.Application

import com.kelompok2.recruitmentapp.util.NODE_Apply
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class ApplicationViewModel: ViewModel() {
    private val dbApplication = FirebaseDatabase.getInstance().getReference(NODE_Apply)


    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?> get() = _result

    private val _application = MutableLiveData<Application>()
    val job: LiveData<Application> get() = _application

    fun addApply(application: Application){
        application.applicationid = dbApplication.push().key
        dbApplication.child(application.applicationid!!).setValue(application).addOnCompleteListener{
            if (it.isSuccessful){
                _result.value  = null
            }else{
                _result.value = it.exception
            }
        }


    }

    private val childEventListener = object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val application = snapshot.getValue(Application::class.java)
                application?.applicationid = snapshot.key
            _application.value = application!!
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
        dbApplication.addChildEventListener(childEventListener)
    }

    override fun onCleared() {
        super.onCleared()
        dbApplication.removeEventListener(childEventListener)
    }
}