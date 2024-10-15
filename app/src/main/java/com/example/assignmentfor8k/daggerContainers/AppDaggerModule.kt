package com.example.assignmentfor8k.daggerContainers

import android.app.Application
import com.example.assignmentfor8k.applicationClass.NewsApplication
import com.example.assignmentfor8k.database.AppDataBase
import com.example.assignmentfor8k.database.chipsDataBase.ChipsDao
import com.example.assignmentfor8k.database.newsDataBase.NewsDao
import com.example.assignmentfor8k.repository.ChipRepository
import com.example.assignmentfor8k.repository.NewsRepository
import com.example.assignmentfor8k.retrofit.newsApi.newRetrofit.NewsApiInterface
import com.example.assignmentfor8k.retrofit.newsApi.newRetrofit.NewsRetrofitInstance
import com.example.assignmentfor8k.retrofit.userIp.Retrofit.RetrofitInstanceIpCall
import com.example.assignmentfor8k.retrofit.userIp.api.IpRegionCallInterface
import com.example.assignmentfor8k.util.Constants.getAllTheChips
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)  // to set Modules for how long these dependency needs to be kept alive
object AppDaggerModule {

    @Singleton  // so we have a single instance of this thing
    @Provides
    fun provideChips() = getAllTheChips()

    @Provides
    @Singleton
    fun providesNewsApi(): NewsApiInterface {
        return NewsRetrofitInstance.newsApi
    }

    @Provides
    @Singleton
    fun providesAppDataBase(app: Application): AppDataBase {
        return AppDataBase.invoke(app)!!
    }


    @Provides
    @Singleton
    fun providesNewsDao(appDataBase: AppDataBase): NewsDao {
        return appDataBase.newsDao()
    }

    @Provides
    @Singleton
    fun providesChipsDao(appDataBase: AppDataBase): ChipsDao {
        return appDataBase.chipsDao()
    }

    @Provides
    @Singleton
    fun providesNewsRepository(newsDao: NewsDao, newsApi: NewsApiInterface): NewsRepository {
        return NewsRepository(newsDao, newsApi)
    }

    @Provides
    @Singleton
    fun providesChipsRepository(chipDao: ChipsDao): ChipRepository {
        return ChipRepository(chipDao)
    }

    @Provides
    @Singleton
    fun providesIpInterface(): IpRegionCallInterface {
        return RetrofitInstanceIpCall.ipApi
    }


}