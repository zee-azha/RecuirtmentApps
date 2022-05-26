//package com.example.recuirtmentapp.fragment
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.example.recuirtmentapp.R
//import com.example.recuirtmentapp.model.Us
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import kotlinx.android.synthetic.main.fragment_profile.*
//
//
//class ProfileFragment : Fragment() {
//
//    lateinit var auth: FirebaseAuth
//    private lateinit var firebaseUser: FirebaseUser
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//        auth = FirebaseAuth.getInstance()
//        firebaseUser = FirebaseAuth.getInstance().currentUser!!
//
//        userInfo()
//
//        return view
//    }
//
//    private fun userInfo(){
//        val userRef = FirebaseDatabase.getInstance().getReference().child("USERS").child(firebaseUser.uid)
//        userRef.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    val user =snapshot.getValue<Users>(Users::class.java)
//                    tv_user.text = user!!.userNama
//                    tv_isi_email.text = firebaseUser.email
//                    tv_isi_phone.text = user!!.phoneNumber
//                    tv_isi_alamat.text = user!!.alamat
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//    }
//
//
//}