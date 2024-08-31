package com.developeremon.ourheros;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.developeremon.ourheros.controller.MyControl;
import com.developeremon.ourheros.helper.ChromeClient;
import com.developeremon.ourheros.helper.HelloWebViewClient;
import com.developeremon.ourheros.helper.MyHelper;
import com.developeremon.ourheros.helper.MyWebDownloader;

import java.io.ByteArrayOutputStream;


public class WebWiew extends Fragment {

    public static String URL = "";
    WebView webview;
    LottieAnimationView progress_loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewWebView = inflater.inflate(R.layout.fragment_web_wiew, container, false);

        webview = viewWebView.findViewById(R.id.webview);
        progress_loading = viewWebView.findViewById(R.id.progress_loading);

        WebViewmethod();

        return viewWebView;

    }

    private void WebViewmethod(){


        webview.getSettings().setUserAgentString(MyControl.USER_AGENT);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setWebChromeClient(new ChromeClient(getActivity()));
        webview.setWebViewClient(new HelloWebViewClient(getActivity()));
        webview.getSettings().setDomStorageEnabled(true);

        //web settings
        WebSettings webSettings = webview.getSettings();
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setAllowContentAccess(true);
        webSettings.setDomStorageEnabled(true);

        webSettings.setSaveFormData(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setEnableSmoothTransition(true);

        webview.getSettings().setBlockNetworkLoads(false);
        webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webview.getSettings().setDomStorageEnabled(true);

        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.clearCache(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.setWebContentsDebuggingEnabled(true);
        }

        // Enable Cookies
        CookieManager.getInstance().setAcceptCookie(true);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webview, true);
        }




        // HelloWebViewClient
        new HelloWebViewClient(new MyHelper() {
            @Override
            public void loading() {

            }

            @Override
            public void finishLoading() {

            }

            @Override
            public void webGoBack() {
                webview.goBack();
            }

            @Override
            public void webLoadUrl(String url) {
                webview.loadUrl(url);
            }

            @Override
            public void errorLoading() {

            }

        });




        // Handle WebLoading
        new ChromeClient(new MyHelper() {
            @Override
            public void loading() {
                progress_loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void finishLoading() {
                progress_loading.setVisibility(View.GONE);
            }

            @Override
            public void webGoBack() {
                webview.goBack();

            }

            @Override
            public void webLoadUrl(String url) {

            }

            @Override
            public void errorLoading() {

            }
        });



        webview.loadUrl(URL);




    }

    //---------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;

            /*-- if file request cancelled; exited camera. we need to send null value to make future attempts workable --*/
            if (resultCode == Activity.RESULT_CANCELED) {
                MyControl.file_path.onReceiveValue(null);
                return;
            }

            /*-- continue if response is positive --*/
            if (resultCode == RESULT_OK) {
                if (null == MyControl.file_path) {
                    return;
                }
                ClipData clipData;
                String stringData;

                try {
                    clipData = intent.getClipData();
                    stringData = intent.getDataString();
                } catch (Exception e) {
                    clipData = null;
                    stringData = null;
                }
                if (clipData == null && stringData == null && MyControl.cam_file_data != null) {
                    results = new Uri[]{Uri.parse(MyControl.cam_file_data)};
                } else {
                    if (clipData != null) {
                        final int numSelectedFiles = clipData.getItemCount();
                        results = new Uri[numSelectedFiles];
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            results[i] = clipData.getItemAt(i).getUri();
                        }
                    } else {
                        try {
                            Bitmap cam_photo = (Bitmap) intent.getExtras().get("data");
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            cam_photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                            stringData = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), cam_photo, null, null);
                        } catch (Exception ignored) {
                        }

                        results = new Uri[]{Uri.parse(stringData)};
                    }
                }
            }

            MyControl.file_path.onReceiveValue(results);
            MyControl.file_path = null;
        } else {
            if (requestCode == MyControl.file_req_code) {
                if (null == MyControl.file_data) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                MyControl.file_data.onReceiveValue(result);
                MyControl.file_data = null;
            }
        }

    } // onActivityResult End Here =============

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }







}