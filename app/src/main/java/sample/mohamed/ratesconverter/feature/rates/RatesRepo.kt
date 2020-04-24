package sample.mohamed.ratesconverter.feature.rates

import sample.mohamed.ratesconverter.domain.Rates
import sample.mohamed.ratesconverter.network.request.RatesRequests
import javax.inject.Inject

class RatesRepo @Inject constructor(private val ratesRequests: RatesRequests) {

    fun getRates(baseCurrency: String) = ratesRequests.getRates(baseCurrency).map { Rates.from(it) }
}