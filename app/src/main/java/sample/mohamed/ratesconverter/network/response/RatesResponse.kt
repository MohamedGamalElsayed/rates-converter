package sample.mohamed.ratesconverter.network.response

data class RatesResponse(val baseCurrency: String, val rates: Map<String, Float>)