package sample.mohamed.ratesconverter.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import sample.mohamed.ratesconverter.network.response.RatesResponse

interface Api {

    @GET("latest")
    fun getRates(@Query("base") baseCurrency: String): Single<RatesResponse>
}