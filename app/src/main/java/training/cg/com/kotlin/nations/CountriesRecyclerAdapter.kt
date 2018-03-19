package training.cg.com.kotlin.nations

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import rx.subjects.PublishSubject
import training.cg.com.kotlin.BR
import training.cg.com.kotlin.R
import training.cg.com.kotlin.model.Country

class CountriesRecyclerAdapter(private val countries: List<Country>, private val userClicks: PublishSubject<Country>) : RecyclerView.Adapter<CountriesRecyclerAdapter.CountryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        val viewDatabinding: ViewDataBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_country, parent, false)
        return CountryItemViewHolder(viewDatabinding)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        holder.viewDataBinding.setVariable(BR.country, CountryItemViewModel(countries[position], userClicks))
    }

    override fun getItemCount(): Int = countries.size

    class CountryItemViewHolder(val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root)
}