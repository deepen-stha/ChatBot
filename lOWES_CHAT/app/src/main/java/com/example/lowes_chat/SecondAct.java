package com.example.lowes_chat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Locale;

public class SecondAct extends AppCompatActivity {

    private WebView webView;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        //getting the details from 1st activity
        final String message = intent.getStringExtra("DETAIL");

        //
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.ENGLISH);
                opening(message);


            }
        });
    }


    public void opening(String message){
        //typecasting the webview
        webView = (WebView) findViewById(R.id.myweb);

        //setting the webview
        WebSettings webSettings = webView.getSettings();
        //to make the JAVASCRIPT code run smoothly on webview
        webSettings.setJavaScriptEnabled(true);


        //setting a listerner which gets performed when we click on the webview
        webView.setOnClickListener(
                new WebView.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        String currenturl = webView.getUrl();
                        webView.loadUrl(currenturl);
                        textToSpeech.speak("hello you are in "+webView.getTitle(),TextToSpeech.QUEUE_FLUSH,null,null);

                    }
                }
        );

        //nested if else loop to check the website starts and ends with proper pattern or not
        if(message.startsWith("https://") && (message.endsWith(".com")||message.endsWith(".org")||message.endsWith(".in"))) {
            //String text2 =" you have searched for "+message;
            //textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);

            webView.loadUrl(message);
            //prevent urls to brows in the browser
            webView.setWebViewClient(new WebViewClient());


        }
        //checking is message starts with www
        else if(message.startsWith("www.")&&(message.endsWith(".com")||message.endsWith(".org")||message.endsWith(".in"))) {
            message = "https://"+message;
            //textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);

            webView.loadUrl(message);
            //prevent urls to brows in the browser
            webView.setWebViewClient(new WebViewClient());


        }
        else if(!message.contains("https://www.") && !(message.endsWith(".com")||message.endsWith(".org")||message.endsWith(".in"))) {
            message = "https://www."+message+".com";
            //textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);

            webView.loadUrl(message);
            //prevent urls to brows in the browser
            webView.setWebViewClient(new WebViewClient());

        }

        else if(!message.contains("https://www.")&&(message.endsWith(".com")||message.endsWith(".org")||message.endsWith(".in"))) {
            message = "https://www."+message;
            //textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);

            webView.loadUrl(message);
            //prevent urls to brows in the browser
            webView.setWebViewClient(new WebViewClient());
            webView.getTitle();

        }


        else if(!message.contains("https://") && message.startsWith("www.")&&(message.endsWith(".com")||message.endsWith(".org")||message.endsWith(".in"))) {
            message = "https://"+message;
            //textToSpeech.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);

            //loading the urlr
            webView.loadUrl(message);
            //prevent urls to brows in the browser
            webView.setWebViewClient(new WebViewClient());

        }

        else {

            //loading the urlr
            webView.loadUrl("https://www.google.com");
            //prevent urls to brows in the browser
            webView.setWebViewClient(new WebViewClient());

        }
    }


    //function gets performend when we backpress on the webview
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }

    }

}
