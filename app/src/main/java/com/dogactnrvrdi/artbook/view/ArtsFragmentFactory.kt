package com.dogactnrvrdi.artbook.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.dogactnrvrdi.artbook.adapter.ArtRecyclerAdapter
import com.dogactnrvrdi.artbook.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtsFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val glide: RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ArtsFragment::class.java.name -> ArtsFragment(artRecyclerAdapter)
            ArtDetails::class.java.name -> ArtDetails(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}