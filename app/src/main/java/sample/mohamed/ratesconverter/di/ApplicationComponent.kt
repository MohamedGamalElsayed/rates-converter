package sample.mohamed.ratesconverter.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import sample.mohamed.ratesconverter.app.RatesConverterApplication
import sample.mohamed.ratesconverter.di.module.AppRestServicesModule
import sample.mohamed.ratesconverter.di.module.ApplicationModule
import sample.mohamed.ratesconverter.di.module.BuildersModule
import sample.mohamed.ratesconverter.di.module.DefaultSchedulersModule
import sample.mohamed.ratesconverter.di.scope.ApplicationScope

@ApplicationScope
@Component(
    modules = [AndroidSupportInjectionModule::class, ApplicationModule::class,
        AppRestServicesModule::class, BuildersModule::class, DefaultSchedulersModule::class]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        @ApplicationScope
        fun application(application: RatesConverterApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: RatesConverterApplication)
}