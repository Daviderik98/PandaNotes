package com.example.panda_quire_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.panda_quire_application.databinding.FragmentRegisterPageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RegisterPage : Fragment() {
    private var _binding: FragmentRegisterPageBinding?=null
    private val binding get() = _binding!!
    private lateinit var db : DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterPageBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        val registerView = binding.root

        db = FirebaseDatabase.
        getInstance("https://pandaappdavid-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")

        val usernameEt = binding.editTextUserName
        val passwordEt = binding.editTextPassword
        val btnSubmit = binding.btnOnScreenTwo

        btnSubmit.setOnClickListener {

            val username = usernameEt.text.toString()
            val password = passwordEt.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()){
                db.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            Toast.makeText(activity,"username exist", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val newUserId = db.push().key
                            val newUser = newUserId?.let { User(username, password, userId = it) }
                            db.child(newUserId!!).setValue(newUser).addOnSuccessListener {
                                Toast.makeText(activity,"user registered",Toast.LENGTH_LONG).show()
                            }.addOnFailureListener {
                                Toast.makeText(activity,"failed to register",Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(activity,"something went wrong! try again",Toast.LENGTH_LONG).show()
                    }
                })



            }else{
                Toast.makeText(activity,"fill in the fields",Toast.LENGTH_LONG).show()
            }



        }







        return registerView
    }


}