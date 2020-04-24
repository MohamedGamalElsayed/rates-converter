package sample.mohamed.ratesconverter.domain

import sample.mohamed.ratesconverter.network.response.RatesResponse

data class Rates(val baseCurrency: Currency, val rateList: List<Currency>) {

    companion object {

        fun from(ratesResponse: RatesResponse): Rates {
            val rateList: MutableList<Currency> = ratesResponse.rates.map {
                Currency(code = it.key, rate = it.value)
            }.toMutableList()
            rateList.add(0, Currency(code = ratesResponse.baseCurrency, rate = 1f))

            return Rates(
                baseCurrency = Currency(ratesResponse.baseCurrency),
                rateList = rateList
            )
        }
    }
}