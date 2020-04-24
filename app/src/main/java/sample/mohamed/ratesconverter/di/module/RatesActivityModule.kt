package sample.mohamed.ratesconverter.di.module

import dagger.Module
import dagger.Provides
import sample.mohamed.ratesconverter.di.scope.ActivityScope
import sample.mohamed.ratesconverter.feature.rates.RatesPresenter
import sample.mohamed.ratesconverter.feature.rates.RatesRepo
import sample.mohamed.ratesconverter.network.Api
import sample.mohamed.ratesconverter.network.request.RatesRequests
import sample.mohamed.ratesconverter.utils.DefaultScheduler

@Module
class RatesActivityModule {

    @Provides
    @ActivityScope
    internal fun providesRatesRequests(api: Api) = RatesRequests(api)

    @Provides
    @ActivityScope
    internal fun providesRatesRepo(ratesRequests: RatesRequests) = RatesRepo(ratesRequests)

    @Provides
    @ActivityScope
    internal fun providesRatesPresenter(defaultScheduler: DefaultScheduler, ratesRepo: RatesRepo) = RatesPresenter(defaultScheduler, ratesRepo)
}