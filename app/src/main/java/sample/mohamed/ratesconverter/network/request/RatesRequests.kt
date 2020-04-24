package sample.mohamed.ratesconverter.network.request

import sample.mohamed.ratesconverter.network.Api
import javax.inject.Inject

class RatesRequests @Inject constructor(private val api: Api) {

    fun getRates(baseCurrency: String) = api.getRates(baseCurrency)
}