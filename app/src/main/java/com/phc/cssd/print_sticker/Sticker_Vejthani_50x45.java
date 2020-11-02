package com.phc.cssd.print_sticker;

import android.content.Context;
import android.util.Log;

import com.phc.core.string.TextAsBitmap;
import com.phc.core.string.TextTwoLine;
import com.phc.cssd.model.ModelSterileDetail;
import com.phc.cssd.model.ModelWashDetailForPrint;
import com.phc.cssd.usb.TscWifi;

import java.util.Iterator;
import java.util.List;

public class Sticker_Vejthani_50x45 {

    // Var
    String IP = "192.168.1.61";
    final int PortNumber = 9100;

    int pQty = 1;

    private int PSpeed = 6 ;
    private int PDensity = 15;

    // Var Position
    private int h = 46;
    private int w = 50;
    private int x = 0;
    private int y = 0;

    String getID = "";

    boolean Print_Manual = false;
    private Context context;

    List<ModelSterileDetail> Data;

    public Sticker_Vejthani_50x45(Context context, String IP, List<ModelSterileDetail> Data){
        this.IP = IP;
        this.context = context;
        this.Data = Data;
    }

    public String print() {
        TscWifi Tsc = new TscWifi();
        List<ModelSterileDetail> DATA_MODEL = Data;
        Iterator li = DATA_MODEL.iterator();
        Tsc.openport(IP, PortNumber);
        String RETURN_DATA = "";
        while (li.hasNext()) {
            Tsc.setup(w, h, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                String Itemname[] = TextTwoLine.make2line(m.getItemname());
                String Itemname1[] = TextTwoLine.make2line2(m.getItemname());
                //แผนก
                Log.d("JFHFKD",m.getDepName()+"");
                if (m.getDepName().equals("0")){
                    Tsc.sendpicture(10, 10, TextAsBitmap.getTextBitmap("CSSD", 32));
                }else {
                    Tsc.sendpicture(10, 10, TextAsBitmap.getTextBitmap(m.getDepName2(), 32));
                }
                //ชื่อไอเท็ม
                Tsc.sendpicture(10, 55 , TextAsBitmap.getTextBitmap1(Itemname[0], 27));
                Tsc.sendpicture(10, 90 , TextAsBitmap.getTextBitmap1(Itemname[1], 27));
                //เตรียม-ตรวจ
                Tsc.sendpicture(10, 120, TextAsBitmap.getTextBitmap1("เตรียม : "+m.getUsr_prepare(), 24));
                Tsc.sendpicture(220, 120, TextAsBitmap.getTextBitmap1("ตรวจ - "+m.getUsr_approve(), 24));
                //QR_Code
                Tsc.qrcode(10, 165, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                //UsageCode
                Tsc.sendpicture(130, 155, TextAsBitmap.getTextBitmap1("No."+m.getUsageCode(), 28));
                //เครื่อง-รอบ
                Tsc.sendpicture(130, 195, TextAsBitmap.getTextBitmap1("เครื่อง - " + m.getMachineName(), 28));
                Tsc.sendpicture(130, 235, TextAsBitmap.getTextBitmap1("รอบ - " + m.getSterileRoundNumber(), 28));
                //วันหมดอายุ
                String eYear = (Integer.parseInt(m.getExpireDate().substring(6, 10)) + 543) + "";
                String expDate = "EXP : "+m.getExpireDate().substring(0, 2) + " / " + m.getExpireDate().substring(3, 5) + " / " + eYear.substring(2, 4);
                Tsc.sendpicture(10,270, TextAsBitmap.getTextBitmap(expDate, 32));
                //วันแพค
                String eYear1 = (Integer.parseInt(m.getSterileDate().substring(6, 10)) + 543) + "";
                String expDate1 = "ผลิต "+m.getSterileDate().substring(0, 2) + "/" + m.getSterileDate().substring(3, 5) + "/" + eYear1.substring(2, 4);
                Tsc.sendpicture(10,315, TextAsBitmap.getTextBitmap1(expDate1+" ("+ m.getAgeDay() +"วัน"+")", 24));
                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";
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
