package sample.mohamed.ratesconverter.di.module

import dagger.Module
import dagger.Provides
import sample.mohamed.ratesconverter.di.scope.ApplicationScope
import sample.mohamed.ratesconverter.utils.DefaultScheduler

@Module
class DefaultSchedulersModule {

    @Provides
    @ApplicationScope
    internal fun providesDefaultSchedulers(): DefaultScheduler {
        return DefaultScheduler()
    }
}