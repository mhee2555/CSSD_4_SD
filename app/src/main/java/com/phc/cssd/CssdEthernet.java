package com.phc.cssd;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import com.example.tscdll.TscWifiActivity;
//import com.example.tscwifidll.TscWifiActivity;


public class CssdEthernet extends Activity {

    TscWifiActivity TscEthernetDll = new TscWifiActivity();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_ethernet);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            /*
            StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                    .permitAll()
                    .detectNetwork()
                    .build());
            StrictMode.setThreadPolicy(old);
            */

        }

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    for(int i=0;i<2;i++) {
                        TscEthernetDll.openport("192.168.1.60", 9100);
                        TscEthernetDll.downloadpcx("UL.PCX");
                        TscEthernetDll.downloadbmp("Triangle.bmp");
                        TscEthernetDll.downloadttf("ARIAL.TTF");

                        //TscEthernetDll.setup(70, 110, 4, 4, 0, 0, 0);

                        TscEthernetDll.setup(51, 70, 4, 4, 0, 1, 2);

                        TscEthernetDll.clearbuffer();
                        TscEthernetDll.sendcommand("SET TEAR ON\n");
                        TscEthernetDll.sendcommand("SET COUNTER @1 1\n");
                        TscEthernetDll.sendcommand("@1 = \"0001\"\n");
                        TscEthernetDll.sendcommand("TEXT 100,300,\"3\",0,1,1,@1\n");
                        TscEthernetDll.sendcommand("PUTPCX 100,300,\"UL.PCX\"\n");
                        TscEthernetDll.sendcommand("PUTBMP 100,520,\"Triangle.bmp\"\n");
                        TscEthernetDll.sendcommand("TEXT 100,760,\"ARIAL.TTF\",0,15,15,\"THIS IS ARIAL FONT\"\n");
                        TscEthernetDll.barcode(100, 100, "128", 100, 1, 0, 3, 3, "123456789");
                        TscEthernetDll.printerfont(100, 250, "3", 0, 1, 1, "987654321");
                        TscEthernetDll.printerfont(100, 350, "3", 0, 1, 1, "xxxxx");

                        //String status = TscEthernetDll.printerstatus();
                        //text1.setText(status);
                        TscEthernetDll.printlabel(2, 1);
                        //TscEthernetDll.sendfile("zpl.txt");
                        TscEthernetDll.closeport(5000);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();



        //finish();
    }
}