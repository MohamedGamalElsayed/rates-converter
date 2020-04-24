package sample.mohamed.ratesconverter.feature.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.emoji.text.EmojiCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_rates.*
import kotlinx.android.synthetic.main.item_rate.view.*
import sample.mohamed.ratesconverter.R
import sample.mohamed.ratesconverter.domain.Currency
import sample.mohamed.ratesconverter.utils.afterTextChanged
import sample.mohamed.ratesconverter.utils.hide
import sample.mohamed.ratesconverter.utils.setMaxLength
import sample.mohamed.ratesconverter.utils.show
import javax.inject.Inject

class RatesActivity : AppCompatActivity(), RatesView {

    @Inject
    lateinit var presenter: RatesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)
        AndroidInjection.inject(this)
        initRatesRecyclerView()
        initPresenter()
    }

    private fun initRatesRecyclerView() = ratesRecyclerView.apply {
        layoutManager = LinearLayoutManager(this@RatesActivity, RecyclerView.VERTICAL, false)
        adapter = RatesAdapter()
    }

    private fun initPresenter() {
        presenter.attachView(this)
        presenter.init()
    }

    override fun bindRates(rates: List<Currency>) =
        (ratesRecyclerView.adapter as RatesAdapter).setItems(rates)

    override fun scrollToFirstItem() =
        ratesRecyclerView.smoothScrollToPosition(0)

    override fun showErrorMessage() =
        Toast.makeText(this, R.string.error_default_message, Toast.LENGTH_SHORT).show()

    override fun showProgress() = loadingProgress.show()

    override fun hideProgress() = loadingProgress.hide()

    override fun onDestroy() {
        super.onDestroy()
        presenter.deAttachView()
    }

    private inner class RatesAdapter : RecyclerView.Adapter<RatesAdapter.RateViewHolder>() {

        private val list = mutableListOf<Currency>()
        private var firstItemIsFocused = false

        private inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val ratesItemContainer = itemView.ratesItemContainer
            val code = itemView.codeTextView
            val flag = itemView.flagEmoji
            val name = itemView.nameTextView
            val rate = itemView.rateEditText
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RateViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_rate, parent, false)
            )

        /**
         * Rate EditText is Focusable only for first item
         * Rate EditText accepts only Int value for first item
         */
        override fun onBindViewHolder(viewHolder: RateViewHolder, position: Int) =
            with(viewHolder) {
                list[position].let {
                    code.text = it.code
                    flag.text = EmojiCompat.get().process(it.flag)
                    name.text = it.name
                    rate.setText(
                        if (position != 0) it.rate.toString() else it.rate.toInt().toString()
                    )
                    rate.setMaxLength(if (position == 0) 4 else 8)
                }

                rate.isFocusable = (position == 0)
                if (position == 0 && firstItemIsFocused) rate.requestFocus() else window.decorView.clearFocus()
                if (position == 0) rate.isFocusableInTouchMode = true

                ratesItemContainer.setOnClickListener { view ->
                    this@RatesAdapter.firstItemIsFocused = false
                    presenter.onRatesItemClicked(list[position].code)
                }

                rate.setOnFocusChangeListener { _, hasFocus ->
                    if (position == 0 && hasFocus) {
                        firstItemIsFocused = true
                    }
                    presenter.onRatesItemValueFocusChanged(firstItemIsFocused, list[position].code)
                }

                rate.afterTextChanged {
                    if (position == 0 && firstItemIsFocused) {
                        if (it.isBlank() || it.toFloat() == 0f) rate.setText("1")
                        if (rate.hasFocus()) presenter.onRatesBaseCurrencyValueChanged(rate.text.toString())
                    }
                }
            }

        override fun getItemCount() = list.size

        fun setItems(items: List<Currency>) {
            list.clear()
            list.addAll(items)
            notifyDataSetChanged()
        }
    }
}
