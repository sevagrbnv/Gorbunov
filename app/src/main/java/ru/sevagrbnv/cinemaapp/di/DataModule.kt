package ru.sevagrbnv.cinemaapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sevagrbnv.cinemaapp.data.Mapper
import ru.sevagrbnv.cinemaapp.data.MovieRepositoryImpl
import ru.sevagrbnv.cinemaapp.data.local.AppDatabase
import ru.sevagrbnv.cinemaapp.data.remote.СacheControlInterceptor
import ru.sevagrbnv.cinemaapp.data.local.LocalDataSource
import ru.sevagrbnv.cinemaapp.data.local.MovieDao
import ru.sevagrbnv.cinemaapp.data.remote.MovieService
import ru.sevagrbnv.cinemaapp.data.remote.RemoteDataSource
import ru.sevagrbnv.cinemaapp.domain.MovieRepository


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context) =
        Cache( context.cacheDir, cacheSize)

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cacheControlInterceptor: СacheControlInterceptor,
        cache: Cache) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            //.addInterceptor(cacheControlInterceptor)
            .cache(cache)
            .build()

    @Provides
    fun providesMapper() = Mapper()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providesMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app.db"
        ).build()

    @Provides
    fun provideDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Provides
    fun provideRemoteDataSource(
        movieService: MovieService
    ): RemoteDataSource = RemoteDataSource(movieService)

    @Provides
    fun provideLocalDataSource(
        movieDao: MovieDao
    ): LocalDataSource = LocalDataSource(movieDao)

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        mapper: Mapper
    ): MovieRepository {
        return MovieRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            mapper = mapper
        )
    }

}