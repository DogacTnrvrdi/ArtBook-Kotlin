package com.dogactnrvrdi.artbook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactnrvrdi.artbook.model.Art
import com.dogactnrvrdi.artbook.model.ImageResponse
import com.dogactnrvrdi.artbook.repo.IArtRepository
import com.dogactnrvrdi.artbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageApiViewModel @Inject constructor(
    private val repository: IArtRepository
) : ViewModel() {

    // Get Image
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList: LiveData<Resource<ImageResponse>>
        get() = images

    // Get Image Url
    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
        get() = selectedImage

    // Selected Image Url
    fun selectedImage(url: String) {
        selectedImage.postValue(url)
    }

    fun searchForImage(searchString: String) {

        if (searchString.isEmpty()) {
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.searchImage(searchString)
            images.value = response
        }
    }
}