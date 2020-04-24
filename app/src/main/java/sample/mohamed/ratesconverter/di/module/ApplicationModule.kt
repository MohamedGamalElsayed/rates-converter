package sample.mohamed.ratesconverter.di.module

import dagger.Module
import dagger.Provides
import sample.mohamed.ratesconverter.app.RatesConverterApplication
import sample.mohamed.ratesconverter.di.scope.ApplicationScope

@Module
class ApplicationModule {

    @Provides
    @ApplicationScope
    internal fun providesApplicationContext(ratesConverterApplication: RatesConverterApplication) = ratesConverterApplication.applicationContext
}