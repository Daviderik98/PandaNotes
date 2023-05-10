package com.example.panda_quire_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.panda_quire_application.databinding.FragmentLoggInPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoggInPage : Fragment() {
    private var _binding:FragmentLoggInPageBinding?=null
    private val binding get() = _binding!!
    private lateinit var db : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentLoggInPageBinding.inflate(layoutInflater,container,false)
        val view = binding.root

        db = FirebaseDatabase.
        getInstance("https://pandaappdavid-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Users")

        var username = binding.etLoggUsername
        var password = binding.etLoggPassword
        var btnSubmit =binding.btnSubmit

        btnSubmit.setOnClickListener {
            val inputUsername = username.text.toString()
            val inputPassword = password.text.toString()

            db.orderByChild("username").equalTo(inputUsername).addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){

                        for (userSnapshot in snapshot.children){

                            val user = userSnapshot.getValue(User::class.java)

                            if (user != null && user.password == inputPassword){
                                val currentUser = userSnapshot.key?.let {
                                    it1->
                                    User(
                                        username = user.username,
                                        password=user.password,
                                        userId = it1
                                    )
                                    //todo : viewmodel for fething the current user
                                }
                            }
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(activity,"something went wrong,try again!", Toast.LENGTH_LONG).show()
                }
            })
        }

        return view

    }


}