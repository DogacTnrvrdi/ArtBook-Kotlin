package com.dogactnrvrdi.artbook.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ArtsFragmentFactory @Inject constructor(
    private val glide: RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ArtDetails::class.java.name -> ArtDetails(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}