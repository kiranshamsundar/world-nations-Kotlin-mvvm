package training.cg.com.kotlin.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import training.cg.com.kotlin.MainApplication
import training.cg.com.kotlin.R
import training.cg.com.kotlin.nations.CountryListFragment
import training.cg.com.kotlin.databinding.LayoutHomeBinding
import training.cg.com.kotlin.extension.show
import training.cg.com.kotlin.util.ConnectionObserver
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject lateinit var homeViewModel: HomeViewModel
    @Inject lateinit var connectionObserver: ConnectionObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as MainApplication).getAppComponent().inject(this)

        val snackBar = Snackbar.make(findViewById(android.R.id.content), "Disconnected", Snackbar.LENGTH_INDEFINITE)
        connectionObserver.networkChanges().subscribe { if (it) snackBar.dismiss() else snackBar.show() }

        val binding = DataBindingUtil.setContentView<LayoutHomeBinding>(this, R.layout.layout_home)
        binding.main = homeViewModel


        homeViewModel.getCountryListFragmentEvents()
                .subscribe { supportFragmentManager.show(R.id.container, CountryListFragment()) }
    }
}
