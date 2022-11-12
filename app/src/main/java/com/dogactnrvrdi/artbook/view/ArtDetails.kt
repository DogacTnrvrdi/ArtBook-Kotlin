package com.dogactnrvrdi.artbook.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.dogactnrvrdi.artbook.R
import com.dogactnrvrdi.artbook.databinding.FragmentArtDetailsBinding
import javax.inject.Inject

class ArtDetails @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_art_details) {

    // Binding
    private var fragmentBinding : FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding
        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        // Navigate to ImageApi
        binding.clickMeIV.setOnClickListener {
            findNavController().navigate(ArtDetailsDirections.actionArtDetails2ToImageApiFragment())
        }

        // On back pressed callback
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}