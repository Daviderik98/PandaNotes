package com.example.panda_quire_application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.panda_quire_application.databinding.FragmentBlankFirstPageBinding

class Blank_FirstPage : Fragment() {
    private var _binding: FragmentBlankFirstPageBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlankFirstPageBinding.inflate(layoutInflater, container,false)
        val view = binding.root

        val btnAbout = binding.btnAbout

        btnAbout.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_blank_FirstPage_to_aboutFragment)
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}

