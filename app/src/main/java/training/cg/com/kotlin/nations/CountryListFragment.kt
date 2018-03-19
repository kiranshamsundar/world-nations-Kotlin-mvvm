package training.cg.com.kotlin.nations

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.frag_country_list.*
import training.cg.com.kotlin.MainApplication
import training.cg.com.kotlin.R
import training.cg.com.kotlin.databinding.FragCountryListBinding
import javax.inject.Inject

class CountryListFragment : Fragment() {

    @Inject lateinit var countryListViewModel: CountryListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_country_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity.application as MainApplication).getAppComponent().inject(this)

        countryListViewModel.getUserClicks().asObservable()
                .subscribe { Snackbar.make(coordinator, it.name, Snackbar.LENGTH_SHORT).show() }

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context)

        val viewBinding: FragCountryListBinding = DataBindingUtil.bind(view)
        viewBinding.viewModel = countryListViewModel
    }
}