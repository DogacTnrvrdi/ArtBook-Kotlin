package com.dogactnrvrdi.artbook.repo

import androidx.lifecycle.LiveData
import com.dogactnrvrdi.artbook.model.Art
import com.dogactnrvrdi.artbook.model.ImageResponse
import com.dogactnrvrdi.artbook.util.Resource

interface IArtRepository {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>
}