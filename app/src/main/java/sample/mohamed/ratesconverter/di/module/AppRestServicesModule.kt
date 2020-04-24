package sample.mohamed.ratesconverter.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import sample.mohamed.ratesconverter.BuildConfig
import sample.mohamed.ratesconverter.di.scope.ApplicationScope
import sample.mohamed.ratesconverter.network.Api
import sample.mohamed.ratesconverter.utils.DefaultScheduler

@Module
class AppRestServicesModule {

    @Provides
    @ApplicationScope
    fun provideOkHttpClient() = OkHttpClient.Builder().build()

    @Provides
    @ApplicationScope
    fun provideRetrofitInterface(okHttpClient: OkHttpClient, defaultSchedulers: DefaultScheduler) =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(defaultSchedulers.io))
            .client(okHttpClient)
            .build()

    @Provides
    @ApplicationScope
    fun provideAppServices(retrofit: Retrofit) = retrofit.create(Api::class.java)

}