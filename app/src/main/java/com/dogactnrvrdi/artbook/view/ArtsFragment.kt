package com.dogactnrvrdi.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogactnrvrdi.artbook.R
import com.dogactnrvrdi.artbook.adapter.ArtRecyclerAdapter
import com.dogactnrvrdi.artbook.databinding.FragmentArtsBinding
import com.dogactnrvrdi.artbook.viewmodel.ArtsViewModel
import javax.inject.Inject

class ArtsFragment @Inject constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter
) : Fragment(R.layout.fragment_arts) {

    // View Model
    lateinit var viewModel: ArtsViewModel

    // Binding
    private var fragmentBinding: FragmentArtsBinding? = null

    // Swipe Callback
    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtsViewModel::class.java)

        // Binding
        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        binding.artsRV.adapter = artRecyclerAdapter
        binding.artsRV.layoutManager = LinearLayoutManager(requireContext())

        // Swipe Callback
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.artsRV)

        // Subscribe to Observers
        subscribeToObservers()

        // Navigate to ArtDetails
        binding.fab.setOnClickListener {
            findNavController().navigate(ArtsFragmentDirections.actionArtsFragmentToArtDetails2())
        }
    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.arts = it
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()

    }
}