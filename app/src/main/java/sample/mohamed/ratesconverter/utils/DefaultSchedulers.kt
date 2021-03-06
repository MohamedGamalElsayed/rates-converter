package sample.mohamed.ratesconverter.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerContract {
    val io: Scheduler
    val ui: Scheduler
}

class DefaultScheduler : SchedulerContract {
    override val io: Scheduler = Schedulers.io()
    override val ui: Scheduler = AndroidSchedulers.mainThread()
}