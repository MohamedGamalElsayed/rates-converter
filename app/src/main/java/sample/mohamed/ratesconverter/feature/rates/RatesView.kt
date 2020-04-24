package sample.mohamed.ratesconverter.feature.rates

import sample.mohamed.ratesconverter.domain.Currency

interface RatesView {

    fun bindRates(rates: List<Currency>)
    fun scrollToFirstItem()
    fun showErrorMessage()
    fun showProgress()
    fun hideProgress()
}