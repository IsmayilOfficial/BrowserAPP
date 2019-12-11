package ut.ee.cs.fm_browser

import android.content.Context
import android.net.ConnectivityManager

object NetworkState {
    fun connectionAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
    }
}