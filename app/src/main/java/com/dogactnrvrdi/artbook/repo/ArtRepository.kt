package com.dogactnrvrdi.artbook.repo

import androidx.lifecycle.LiveData
import com.dogactnrvrdi.artbook.api.RetrofitAPI
import com.dogactnrvrdi.artbook.model.Art
import com.dogactnrvrdi.artbook.model.ImageResponse
import com.dogactnrvrdi.artbook.roomdb.ArtDAO
import com.dogactnrvrdi.artbook.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDAO,
    private val retrofitAPI: RetrofitAPI
) : IArtRepository {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {

        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error!", null)
            } else {
                Resource.error("Error!", null)
            }
        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }
}