package training.cg.com.kotlin.extension

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

fun FragmentManager.show(containerId: Int, fragment: Fragment, backstack: String? = null) {
    beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(backstack)
            .commit()
}