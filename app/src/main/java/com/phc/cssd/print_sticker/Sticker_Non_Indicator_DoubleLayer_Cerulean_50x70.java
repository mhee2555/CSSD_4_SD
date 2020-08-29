package com.phc.cssd.print_sticker;

import android.content.Context;

import com.phc.core.string.TextAsBitmap;
import com.phc.cssd.model.ModelWashDetailForPrint;
import com.phc.cssd.usb.TscWifi;

import java.util.Iterator;
import java.util.List;

public class Sticker_Non_Indicator_DoubleLayer_Cerulean_50x70 {

    // Var
    String IP = "192.168.1.61";
    final int PortNumber = 9100;

    private int PSpeed = 6 ;
    private int PDensity = 15;

    // Var Position
    private int h = 73;
    private int w = 54;
    private int x = 0;
    private int y = 0;

    boolean Print_Manual = false;
    private Context context;

    List<ModelWashDetailForPrint> Data;

    public Sticker_Non_Indicator_DoubleLayer_Cerulean_50x70(Context context, String IP, List<ModelWashDetailForPrint> Data){
        this.IP = IP;
        this.context = context;
        this.Data = Data;
    }

    public String print() {

        TscWifi Tsc = new TscWifi();

        List<ModelWashDetailForPrint> DATA_MODEL = Data;
        Iterator li = DATA_MODEL.iterator();

        Tsc.openport(IP, PortNumber);

        String RETURN_DATA = "";

        while (li.hasNext()) {

            Tsc.setup(w, h, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();

            try {
                ModelWashDetailForPrint m = (ModelWashDetailForPrint) li.next();

                //-----------------------------------------------------------
                // Part 1

                // ItemName
                Tsc.sendpicture(x(4), y(2), TextAsBitmap.getTextBitmap1(m.getItemname(), 32));

                // Usage
                Tsc.sendpicture(x(4), y(8.5), TextAsBitmap.getTextBitmap(m.getUsageCode(), 26));

                // Packer
                Tsc.sendpicture( x(4) , y(14), TextAsBitmap.getTextBitmap(m.getPacker(), 26));

                // Usage Count
                Tsc.sendpicture( x(4) , y(19), TextAsBitmap.getTextBitmap1("Usage Count : " + m.getUsageCount(), 26));

                // ID
                //Tsc.sendpicture( x(4) , y(24), TextAsBitmap.getTextBitmap1("SD : " + m.getID(), 26));

                // MFG
                Tsc.sendpicture(x(21), y(36.5), TextAsBitmap.getTextBitmap(m.getMFG() + " " + m.getAge(), 25));

                // EXP
                Tsc.sendpicture(x(21), y(40.5) , TextAsBitmap.getTextBitmap1( m.getEXP(), 32));

                //QR_Code
                Tsc.qrcode(x(4.2), y(33), "H", "6", "A", "0", "M2", "S1", m.getUsageCode());

                //-----------------------------------------------------------------------------
                // Part 2

                //ItemName
                Tsc.sendpicture(x(4), y(51), TextAsBitmap.getTextBitmap1(m.getItemname(), 26));

                //ItemCode
                Tsc.sendpicture(x(21), y(55), TextAsBitmap.getTextBitmap(m.getUsageCode(), 26));

                //Pack Date
                Tsc.sendpicture(x(21), y(61), TextAsBitmap.getTextBitmap(m.getMFG() + " " + m.getAge(), 25));

                //EXP Date
                Tsc.sendpicture(x(21), y(65), TextAsBitmap.getTextBitmap1( m.getEXP(), 32));

                //Qr code
                Tsc.qrcode(x(4.6), y(55.6), "H", "5", "A", "0", "M2", "S1", m.getUsageCode());

                Tsc.sendcommand("PRINT 1,1\r\n");

                RETURN_DATA += m.getID() + ",";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Tsc.closeport();

        return RETURN_DATA;
    }

    private int x(double mm){
        double data = (mm*7.986);
        return (int)data + x;
    }

    private int y(double mm){
        double data = (mm*7.986);
        return (int)data + y;
    }

}
