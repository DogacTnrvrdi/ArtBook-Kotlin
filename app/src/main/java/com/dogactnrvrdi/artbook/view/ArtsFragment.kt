package com.dogactnrvrdi.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dogactnrvrdi.artbook.R
import com.dogactnrvrdi.artbook.databinding.FragmentArtsBinding

class ArtsFragment : Fragment(R.layout.fragment_arts) {

    // Binding
    private var fragmentBinding : FragmentArtsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding
        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        // Navigate to ArtDetails
        binding.fab.setOnClickListener {
            findNavController().navigate(ArtsFragmentDirections.actionArtsFragmentToArtDetails2())
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()

    }
}