package com.dogactnrvrdi.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.dogactnrvrdi.artbook.R
import com.dogactnrvrdi.artbook.databinding.FragmentArtDetailsBinding
import com.dogactnrvrdi.artbook.util.Status
import com.dogactnrvrdi.artbook.viewmodel.ArtDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class ArtDetails @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_art_details) {

    // View Model
    lateinit var viewModel: ArtDetailsViewModel

    // Binding
    private var fragmentBinding: FragmentArtDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View Model
        viewModel = ViewModelProvider(requireActivity()).get(ArtDetailsViewModel::class.java)

        // Binding
        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        // Navigate to ImageApi
        binding.clickMeIV.setOnClickListener {
            findNavController().navigate(ArtDetailsDirections.actionArtDetails2ToImageApiFragment())
        }

        // Subscribe to Observers
        subscribeToObservers()

        // On back pressed callback
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        // Save Button
        binding.detailsSaveBtn.setOnClickListener {
            viewModel.makeArt(
                binding.detailsNameET.text.toString(),
                binding.detailsArtistNameET.text.toString(),
                binding.detailsYearET.text.toString()
            )
        }
    }

    // Subscribe to Observers
    private fun subscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.clickMeIV)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(
                        requireContext(), "Succes", Toast.LENGTH_LONG
                    ).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(), it.message ?: "Error!", Toast.LENGTH_LONG
                    ).show()
                }

                Status.LOADING -> {}
            }
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}