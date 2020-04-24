package sample.mohamed.ratesconverter.domain

import sample.mohamed.ratesconverter.utils.asEmoji
import java.util.Currency

data class Currency(val code: String, var rate: Float = 0f) {

    val flag: CharSequence by lazy { code.asEmoji() }
    val name = Currency.getInstance(code).displayName
}