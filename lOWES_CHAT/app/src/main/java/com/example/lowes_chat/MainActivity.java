package com.example.lowes_chat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    public TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing text to speech
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.UK);

            }
        });

        final Button myButton = (Button) findViewById(R.id.enter);
        myButton.setOnClickListener(

                new Button.OnClickListener() {

                    public void onClick(View v1) {

                        //userdefind temporary function
                        gonow();

                    }
                }
        );
    }

    public void gonow()
    {

        TextView myText = (TextView) findViewById(R.id.website);
        String message = myText.getText().toString();


        if(message.equals("")){
            textToSpeech.speak("please enter something to search", TextToSpeech.QUEUE_FLUSH, null, null);
        }

        else {
            Intent intent = new Intent(this, SecondAct.class);
            //passing details from one activity to another activity
            intent.putExtra("DETAIL", message);

            String text2 = " you have searched for " + message;
            textToSpeech.speak(text2, TextToSpeech.QUEUE_FLUSH, null, null);

            //starting another activity
            startActivity(intent);
        }

    }

    public void getSpeechInput(View view){

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());


        if(intent.resolveActivity((getPackageManager()))!=null) {
            startActivityForResult(intent, 10);
        }
        else {
            Toast.makeText(this,"your device dont suppor this",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView myText = (TextView) findViewById(R.id.website);

        switch (requestCode){
            case 10:
                if(resultCode == RESULT_OK && data !=null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String temp = result.get(0);
                    //textToSpeech.speak(temp, TextToSpeech.QUEUE_FLUSH, null, null);


                    if(temp.contains("hi")|| temp.contains("hello"))
                    {
                        textToSpeech.speak("hi.How can i help you ", TextToSpeech.QUEUE_FLUSH, null, null);

                    }
                    else if(temp .contains("what can you do"))
                    {
                        textToSpeech.speak("i can help you with discovering and ordering products ", TextToSpeech.QUEUE_FLUSH, null, null);

                    }

                    else if(temp.contains("ok Deep")){
                        textToSpeech.speak("thats me how can i help you",TextToSpeech.QUEUE_FLUSH,null,null);
                    }

                    else if(temp.contains("https://") || temp.contains("www.") || temp.contains(".com") || temp.contains(".in") || temp.contains(".org")) {

                        textToSpeech.speak("you have searched for "+temp, TextToSpeech.QUEUE_FLUSH, null, null);
                        Intent intent = new Intent(this, SecondAct.class);
                        //passing details from one activity to another activity
                        intent.putExtra("DETAIL", temp);

                        startActivity(intent);

                    }

                    else if(temp.contains("joke") || temp.contains("tell me joke"))
                    {

                        //logic to tell random jokes

                        Random random = new Random();
                        int num = (int) (random.nextInt() % 4);
                        switch (num) {
                            case 0:
                                textToSpeech.speak("Doctor: I'm sorry but you suffer from a terminal illness and have only 10 to live." +
                                        "Patient: \"What do you mean, 10? 10 what? Months? Weeks?" + "Doctor:Nine. ", TextToSpeech.QUEUE_FLUSH, null, null);
                                break;

                            case 1:
                                textToSpeech.speak("My old aunts would come and tease me at weddings, Well Sarah? Do you think you’ll be next? We’ve settled this quickly once I’ve started doing the same to them at funerals.”", TextToSpeech.QUEUE_FLUSH, null, null);
                                break;

                            case 2:
                                textToSpeech.speak("Job interviewer: “And where would you see yourself in five years’ time Mr. Jeffries?" +
                                        "Mr. Jeffries: Personally I believe my biggest weakness is in listening", TextToSpeech.QUEUE_FLUSH, null, null);
                                break;

                            case 3:
                                textToSpeech.speak("Dentist: “This will hurt a little." +
                                        "Patient:OK.Dentist: “I’ve been having an affair with your wife for a while now.”", TextToSpeech.QUEUE_FLUSH, null, null);
                                break;

                        }

                    }

                    else if(temp.contains("open WhatsApp")){

                        textToSpeech.speak("please wait while opening whatsapp.", TextToSpeech.QUEUE_FLUSH, null, null);

                        Intent i= getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                        startActivity(i);
                    }


                    else
                        {
                        textToSpeech.speak("please make the search in google.com for better discover ", TextToSpeech.QUEUE_FLUSH, null, null);

                            Intent intent = new Intent(this, SecondAct.class);
                            //passing details from main activity to second activity
                            intent.putExtra("DETAIL", temp);

                            startActivity(intent);


                        }
                    myText.setText(result.get(0));
                }
          }
    }
}
