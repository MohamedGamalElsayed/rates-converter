package sample.mohamed.ratesconverter.feature.rates

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import sample.mohamed.ratesconverter.domain.Rates
import sample.mohamed.ratesconverter.utils.DefaultScheduler
import sample.mohamed.ratesconverter.utils.ResponseContract
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RatesPresenter @Inject constructor(
    val defaultScheduler: DefaultScheduler,
    val ratesRepo: RatesRepo
) : ResponseContract<Rates> {

    private val compositeDisposable = CompositeDisposable()
    private var rates: Rates? = null
    private var baseCurrencyCode = "EUR"
    private var isNewCurrencySelected = false
    private var isInEditMode = false

    private lateinit var ratesView: RatesView

    fun attachView(ratesView: RatesView) {
        this.ratesView = ratesView
    }

    fun init() {
        loadRates(baseCurrencyCode)
    }

    fun loadRates(baseCurrency: String) {
        compositeDisposable.add(
            Observable.interval(1, TimeUnit.SECONDS, defaultScheduler.io)
                .flatMap { ratesRepo.getRates(baseCurrency).toObservable() }
                .subscribeOn(defaultScheduler.io)
                .observeOn(defaultScheduler.ui)
                .doOnSubscribe { onLoading() }
                .subscribe(
                    { response ->
                        response?.let {
                            onSuccess(response)
                        } ?: run {
                            onError()
                        }
                    }, {
                        onError()
                    })
        )
    }

    fun onRatesItemClicked(currencyCode: String) {
        compositeDisposable.clear()
        isNewCurrencySelected = true
        isInEditMode = false
        baseCurrencyCode = currencyCode
        loadRates(currencyCode)
        ratesView.scrollToFirstItem()
    }

    fun onRatesItemValueFocusChanged(hasFocus: Boolean, currencyCode: String) {
        if (hasFocus) {
            isInEditMode = true
            compositeDisposable.clear()
        } else {
            isNewCurrencySelected = false
            baseCurrencyCode = currencyCode
            loadRates(currencyCode)
        }
    }

    fun onRatesBaseCurrencyValueChanged(rate: String) {
        val rateChangeAmount = rate.toFloat() / rates!!.rateList[0].rate
        rates!!.rateList.forEach {
            it.rate *= rateChangeAmount
        }
        ratesView.bindRates(rates!!.rateList)
    }

    fun onRestart() {
        if (!isInEditMode) loadRates(baseCurrencyCode)
    }

    fun onPause() = compositeDisposable.clear()

    override fun onSuccess(data: Rates) {
        ratesView.hideProgress()
        if (!isNewCurrencySelected) {
            rates?.let { rates ->
                data.rateList.forEach { it.rate = it.rate * rates.rateList[0].rate }
            }
        }
        rates = data
        ratesView.bindRates(rates!!.rateList)
    }

    override fun onError() {
        ratesView.hideProgress()
        ratesView.showErrorMessage()
    }

    override fun onLoading() {
        ratesView.showProgress()
    }

    fun deAttachView() {
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}