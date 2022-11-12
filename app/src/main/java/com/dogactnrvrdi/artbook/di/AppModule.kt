package com.dogactnrvrdi.artbook.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dogactnrvrdi.artbook.R
import com.dogactnrvrdi.artbook.api.RetrofitAPI
import com.dogactnrvrdi.artbook.repo.ArtRepository
import com.dogactnrvrdi.artbook.repo.IArtRepository
import com.dogactnrvrdi.artbook.roomdb.ArtDAO
import com.dogactnrvrdi.artbook.roomdb.ArtDatabase
import com.dogactnrvrdi.artbook.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context, ArtDatabase::class.java, "ArtBookDB"
        ).build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: ArtDAO, api: RetrofitAPI) =
        ArtRepository(dao, api) as IArtRepository

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) =
        Glide.with(context).setDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
            )
}