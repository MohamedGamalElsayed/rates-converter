package sample.mohamed.ratesconverter.utils

interface ResponseContract<T> {
    fun onSuccess(data: T)
    fun onError()
    fun onLoading()
}