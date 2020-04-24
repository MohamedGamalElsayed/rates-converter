package sample.mohamed.ratesconverter.features.rates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import sample.mohamed.ratesconverter.feature.rates.RatesPresenter
import sample.mohamed.ratesconverter.feature.rates.RatesRepo
import sample.mohamed.ratesconverter.feature.rates.RatesView
import sample.mohamed.ratesconverter.utils.DefaultScheduler

@RunWith(MockitoJUnitRunner::class)
class RatesPresenterTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var defaultScheduler: DefaultScheduler
    @Mock
    lateinit var ratesRepo: RatesRepo
    @Mock
    lateinit var ratesView: RatesView

    lateinit var ratesPresenter: RatesPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        ratesPresenter = RatesPresenter(defaultScheduler, ratesRepo)
        ratesPresenter.attachView(ratesView)
        Mockito.`when`(defaultScheduler.ui).thenAnswer { return@thenAnswer Schedulers.trampoline() }
        Mockito.`when`(defaultScheduler.io).thenAnswer { return@thenAnswer Schedulers.trampoline() }
    }

    @Test
    fun test_init() {
        Mockito.`when`(ratesRepo.getRates(ArgumentMatchers.anyString()))
            .thenAnswer { return@thenAnswer null }

        ratesPresenter.init()

        with(Mockito.inOrder(ratesView, ratesRepo)) {
            verify(ratesView).showProgress()
            verify(ratesRepo).getRates(ArgumentMatchers.anyString())
            verify(ratesView).hideProgress()
            verify(ratesView).showErrorMessage()
        }
    }

    @Test
    fun test_onRatesItemClicked() {
        Mockito.`when`(ratesRepo.getRates(ArgumentMatchers.anyString()))
            .thenAnswer { return@thenAnswer null }

        ratesPresenter.onRatesItemClicked("USD")

        with(Mockito.inOrder(ratesView, ratesRepo)) {
            verify(ratesView).showProgress()
            verify(ratesRepo).getRates(ArgumentMatchers.anyString())
            verify(ratesView).hideProgress()
            verify(ratesView).showErrorMessage()
            verify(ratesView).scrollToFirstItem()
        }
    }
}