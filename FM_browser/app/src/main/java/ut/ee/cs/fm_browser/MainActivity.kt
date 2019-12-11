package ut.ee.cs.fm_browser

import android.content.Context
import android.graphics.Color
import android.os.Bundle

import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var webView: WebView? = null
    var editText: EditText? = null
    var progressBar: ProgressBar? = null
    var back: ImageButton? = null
    var forward: ImageButton? = null
    var stop: ImageButton? = null
    var refresh: ImageButton? = null
    var homeButton: ImageButton? = null
    var goButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.web_address_edit_text)
        back = findViewById(R.id.back_arrow)
        forward = findViewById(R.id.forward_arrow)
        stop = findViewById(R.id.stop)
        goButton = findViewById(R.id.go_button)
        refresh = findViewById(R.id.refresh)
        homeButton = findViewById(R.id.home)
        progressBar = findViewById(R.id.progress_bar)
        progressBar!!.max = 100
        progressBar!!.visibility = View.VISIBLE
        webView = findViewById<WebView>(R.id.web_view)
        if (savedInstanceState != null) {
            webView!!.restoreState(savedInstanceState)
        } else {
            webView!!.settings.javaScriptEnabled = true
            webView!!.settings.useWideViewPort = true
            webView!!.settings.loadWithOverviewMode = true
            webView!!.settings.setSupportZoom(true)
            webView!!.settings.setSupportMultipleWindows(true)
            webView!!.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            webView!!.setBackgroundColor(Color.WHITE)
            webView!!.setWebChromeClient(object : WebChromeClient() {
                override fun onProgressChanged(
                    view: WebView,
                    newProgress: Int
                ) {
                    super.onProgressChanged(view, newProgress)
                    progressBar!!.progress = newProgress
                    if (newProgress < 100 && progressBar!!.visibility == ProgressBar.GONE) {
                        progressBar!!.visibility = ProgressBar.VISIBLE
                    }
                    if (newProgress == 100) {
                        progressBar!!.visibility = ProgressBar.GONE
                    } else {
                        progressBar!!.visibility = ProgressBar.VISIBLE
                    }
                }
            })
        }
        webView!!.webViewClient = MyWebViewClient()
        goButton!!.setOnClickListener {
            try {
                when {
                    !NetworkState.connectionAvailable(this@MainActivity) -> {
                        Toast.makeText(
                            this@MainActivity,
                            R.string.check_connection,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        val inputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(editText!!.windowToken, 0)
                        webView!!.loadUrl("https://" + editText!!.text.toString())
                        editText!!.setText("")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        back!!.setOnClickListener {
            when {
                webView!!.canGoBack() -> {
                    webView!!.goBack()
                }
            }
        }
        forward!!.setOnClickListener {
            when {
                webView!!.canGoForward() -> {
                    webView!!.goForward()
                }
            }
        }
        stop!!.setOnClickListener { webView!!.stopLoading() }
        refresh!!.setOnClickListener { webView!!.reload() }
        homeButton!!.setOnClickListener { webView!!.loadUrl("http://google.com") }
    }
}