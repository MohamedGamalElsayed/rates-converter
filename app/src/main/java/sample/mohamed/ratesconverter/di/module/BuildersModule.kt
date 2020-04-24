package sample.mohamed.ratesconverter.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sample.mohamed.ratesconverter.di.scope.ActivityScope
import sample.mohamed.ratesconverter.feature.rates.RatesActivity

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector(modules = [RatesActivityModule::class])
    @ActivityScope
    abstract fun bindsRatesActivity(): RatesActivity
}