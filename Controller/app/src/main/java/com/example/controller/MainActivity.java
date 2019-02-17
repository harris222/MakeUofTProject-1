package com.example.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import java.net.*;
import java.io.*;
public class MainActivity extends AppCompatActivity {
    private String pi_addr = "100.65.59.103";
    private int port = 12345;
    private Socket ss = null;
    private DataOutputStream out = null;
    private int LED_HIGH = 1;
    private int LED_LOW = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setText("OFF");
        button.setX(435);
        button.setY(435);

        new Thread(new ClientThread()).start();
        final WriteThread WrtThread = new WriteThread();
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(button.getText() == "OFF"){
                    WrtThread.setLedState("HIGH");
                    new Thread(WrtThread).start();
                    button.setText("ON");
                    new Thread(new WriteThread()).start();
                }else{
                    WrtThread.setLedState("LOW");
                    new Thread(WrtThread).start();
                    button.setText("OFF");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        try {
            out.close();
            ss.close();
        }
        catch(IOException i) {
            System.out.println(i);
        }
        super.onDestroy();
    }
    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                ss = new Socket(pi_addr, port);
                out = new DataOutputStream(ss.getOutputStream());
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
    class WriteThread implements Runnable {
        private String LED_STATE = "HIGH";
        public void setLedState(String state){
            LED_STATE = state;
        }
        @Override
        public void run() {

            try {
                out.writeUTF(LED_STATE);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
}

