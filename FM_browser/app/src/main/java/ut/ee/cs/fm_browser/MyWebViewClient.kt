package ut.ee.cs.fm_browser

import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient

internal class MyWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView,
        url: String
    ): Boolean {
        view.loadUrl(url)
        CookieManager.getInstance().setAcceptCookie(true)
        return true
    }
}