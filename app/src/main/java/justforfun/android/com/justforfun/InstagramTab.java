package justforfun.android.com.justforfun;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class InstagramTab extends Fragment {

    WebView webView;
    private ProgressBar mPbar = null;
    SwipeRefreshLayout mySwipeRefreshLayout;
    private int count = 0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    webView.goBack();
                }
                break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View V = inflater.inflate(R.layout.instagram_tab, container, false);

        webView = (WebView) V.findViewById(R.id.is);
        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        mPbar = (ProgressBar) V.findViewById(R.id.instaSpinner);
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.canGoBack();
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl("https://www.instagram.com");

        //  CookieManager.getInstance().setAcceptThirdPartyCookies(webView,true);

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view,
                                          int newProgress) {
                System.out.println(" progress " + newProgress);

                //mPbar.setVisibility(View.VISIBLE);
                mPbar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {
                    mPbar.setVisibility(View.GONE);
                    mySwipeRefreshLayout.setRefreshing(false);
                }

            }
        });
        webView.setWebViewClient(new WebViewClient() {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mPbar.setVisibility(View.VISIBLE);
                //mPbar.setProgress(0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mPbar.setVisibility(View.GONE);
                mySwipeRefreshLayout.setRefreshing(false);

            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //Your code to do
                //  Toast.makeText(getActivity(), "Your Internet Connection May not be active Or " + error , Toast.LENGTH_LONG).show();

                count++;
                if (count < 5) {
                    webView.loadUrl("https://www.instagram.com");
                }
            }


        });


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("https://www.instagram.com");
            }
        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == MotionEvent.ACTION_UP && webView.canGoBack()) {
                    System.out.println("Press Back key");
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }
        });

        mySwipeRefreshLayout = (SwipeRefreshLayout) V.findViewById(R.id.swiperefresh);

        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                                                      @Override
                                                      public void onRefresh() {
                                                          webView.reload();
                                                      }
                                                  }
        );


        return V;
    }

    /*
    public boolean canGoBack(){
        return webView.canGoBack();
    }

    public void goBack(){
        webView.goBack();
    }
*/
}
