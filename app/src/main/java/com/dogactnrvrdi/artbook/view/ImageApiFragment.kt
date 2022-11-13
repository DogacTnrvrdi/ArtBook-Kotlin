package com.dogactnrvrdi.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.dogactnrvrdi.artbook.R
import com.dogactnrvrdi.artbook.adapter.ImageRecyclerAdapter
import com.dogactnrvrdi.artbook.databinding.FragmentImageApiBinding
import com.dogactnrvrdi.artbook.util.Status
import com.dogactnrvrdi.artbook.viewmodel.ImageApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_api) {

    // View Model
    lateinit var viewModel: ImageApiViewModel

    // Binding
    private var fragmentBinding: FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View Model
        viewModel = ViewModelProvider(requireActivity()).get(ImageApiViewModel::class.java)

        // Binding
        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        binding.imageRecyclerView.adapter = imageRecyclerAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        // Job
        var job: Job? = null

        // On Item Clicked
        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.selectedImage(it)
        }

        // Text Changed Listener
        binding.searchET.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        // Subscribe to Observers
        subscribeToObservers()
    }

    // Subscribe to Observes
    private fun subscribeToObservers() {
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }
                    imageRecyclerAdapter.images = urls ?: listOf()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                }

                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(), it.message ?: "Error!", Toast.LENGTH_LONG
                    ).show()
                    fragmentBinding?.progressBar?.visibility = View.GONE
                }

                Status.LOADING -> {
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}