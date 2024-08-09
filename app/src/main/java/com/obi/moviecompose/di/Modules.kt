package com.obi.moviecompose.di

import android.app.Application
import androidx.room.Room
import com.obi.moviecompose.data.Consts.BASE_URL
import com.obi.moviecompose.data.local.MoviesDao
import com.obi.moviecompose.data.local.MoviesDatabase
import com.obi.moviecompose.data.network.MovieApi
import com.obi.moviecompose.domain.MoviesRepository
import com.obi.moviecompose.domain.usecases.GetAiringTodayTvShowsUseCase
import com.obi.moviecompose.domain.usecases.GetFavoriteMoviesUseCase
import com.obi.moviecompose.domain.usecases.GetMovieDetailsUseCase
import com.obi.moviecompose.domain.usecases.GetTopRatedMoviesUseCase
import com.obi.moviecompose.domain.usecases.GetTrendingMoviesUseCase
import com.obi.moviecompose.domain.usecases.RemoveFavoriteMovieUseCase
import com.obi.moviecompose.domain.usecases.SaveFavoriteMovieUseCase
import com.obi.moviecompose.domain.usecases.SearchMovieUseCase
import com.obi.moviecompose.presentation.favorites.FavoritesViewModel
import com.obi.moviecompose.presentation.details.MovieDetailsViewModel
import com.obi.moviecompose.presentation.home.HomeViewModel
import com.obi.moviecompose.presentation.search.SearchViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(Interceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .build()
            chain.proceed(request)
        })

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .build()
            .create(MovieApi::class.java)
    }
    single { Dispatchers.IO }

    fun provideDatabase(application: Application) = Room.databaseBuilder(
        application,
        MoviesDatabase::class.java,
        "movies_database"
    ).build()

    single { provideDatabase(get()) }
    single<MoviesDao> {
        get<MoviesDatabase>().moviesDao()
    }
}

val dataModule = module {
    factoryOf(::MoviesRepository)
}
val domainModule = module {
    factoryOf(::GetTopRatedMoviesUseCase)
    factoryOf(::GetTrendingMoviesUseCase)
    factoryOf(::GetAiringTodayTvShowsUseCase)
    factoryOf(::SearchMovieUseCase)
    factoryOf(::GetMovieDetailsUseCase)
    factoryOf(::SaveFavoriteMovieUseCase)
    factoryOf(::RemoveFavoriteMovieUseCase)
    factoryOf(::GetFavoriteMoviesUseCase)
}

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::MovieDetailsViewModel)
    viewModelOf(::FavoritesViewModel)
}
