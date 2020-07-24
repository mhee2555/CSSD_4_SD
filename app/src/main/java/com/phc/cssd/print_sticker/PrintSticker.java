package com.phc.cssd.print_sticker;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.phc.core.connect.HTTPConnect;
import com.phc.core.string.TextAsBitmap;
import com.phc.core.string.TextTwoLine;
import com.phc.cssd.data.PreviewSticker;
import com.phc.cssd.model.ModelSterileDetail;
import com.phc.cssd.url.getUrl;
import com.phc.cssd.usb.TscWifi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PrintSticker {
    getUrl iFt = new getUrl();

    HTTPConnect ruc = new HTTPConnect();

    String DATA="";

    int pQty = 1;

    private String B_ID = null;
    boolean Print = true;

    int Heigth = 0;
    int Width = 0;

    public String getData() { return DATA; }

    public void setData(String DATA) {
        this.DATA = DATA;
    }
    // =============================================================================================
    // -- Get Printer
    // =============================================================================================
    int xItemname1 = 0;
    int yItemname1 = 0;
    int xUsagecode1 = 0;
    int yUasgecode1 = 0;
    int xPrice = 0;
    int yPrice = 0;
    int xDept1 = 0;
    int yDept1 = 0;
    int xMachine = 0;
    int yMahcine = 0;
    int xRound = 0;
    int yRound = 0;
    int xPackdate = 0;
    int yPackdate = 0;
    int xExp = 0;
    int yExp = 0;
    int xEmp1 = 0;
    int yEmp1 = 0;
    int xEmp2 = 0;
    int yEmp2 = 0;
    int xItemname2 = 0;
    int yItemname2 = 0;
    int xDept2 = 0;
    int yDept2 = 0;
    int xUsagecode2 = 0;
    int yUsagecode2 = 0;
    int xQrcode1 = 0;
    int yQrcode1 = 0;
    int xQrcode2 = 0;
    int yQrcode2 = 0;
    int xPackdate2 = 0;
    int yPackdate2 = 0;
    int xExp2 = 0;
    int yExp2 = 0;
    int xEmp3 = 0;
    int yEmp3 = 0;
    private Context context;
    PreviewSticker p = new PreviewSticker("");

    public void PrintSticker(Activity aActivity, int Sel, List<ModelSterileDetail> sterile, ArrayList<String> PRINTER_IP,String B_ID,boolean Print) throws Exception{
        this.B_ID = B_ID;
        this.Print = Print;

        if (!B_ID.equals("1")){
            xPrintB_1(aActivity, Sel, sterile, PRINTER_IP, B_ID);
        }else {
            xPrintB_0(aActivity, Sel, sterile, PRINTER_IP, B_ID);
        }
    }

    public void PrintSticker(Activity aActivity, int Sel, List<ModelSterileDetail> sterile, ArrayList<String> PRINTER_IP, int pQty,String B_ID) throws Exception{
        this.pQty = pQty;
        this.B_ID = B_ID;
        xPrintB_0(aActivity,Sel,sterile,PRINTER_IP,B_ID);
    }

    private void xPrintB_0(Activity aActivity, int Sel, List<ModelSterileDetail> sterile, ArrayList<String> PRINTER_IP,String B_ID){
        String xData = "";
        if(sterile == null || sterile.size() == 0){
            Toast.makeText(aActivity, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (Sel) {
            //==================================================================================================================================
            // Indicator blue 5x2.5cm
            //==================================================================================================================================
            case 1:
                if (Print) {
                    Print = false;
                    xData = StickerCase01_SIRIRAJ(Sel, PRINTER_IP, sterile);
                }
                break;
            //==================================================================================================================================
            // Indicator blue 5x5cm
            //==================================================================================================================================
            case 2:
                if (Print) {
                    Print = false;
                    xData = StickerCase02_SIRIRAJ(Sel, PRINTER_IP, sterile);
                }
                break;
            //==================================================================================================================================
            // Sticker Non Indicator Grey 5x8cm
            //==================================================================================================================================
            case 3:
                if (Print) {
                    Print = false;
                    xData = StickerCase03_SIRIRAJ(Sel, PRINTER_IP, sterile);
                }
                break;
            //==================================================================================================================================
            // Sticker Non Indicator Grey 5x8cm
            //==================================================================================================================================
            case 4:
                if (Print) {
                    Print = false;
                    xData = StickerCase04_SIRIRAJ(Sel, PRINTER_IP, sterile);
                }
                break;
        }
        setData(xData);
    }
    private void xPrintB_1(Activity aActivity, int Sel, List<ModelSterileDetail> sterile, ArrayList<String> PRINTER_IP,String B_ID){
        String xData = "";
        if(sterile == null || sterile.size() == 0){
            Toast.makeText(aActivity, "ไม่พบข้อมูล", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (Sel) {
            //==================================================================================================================================
            // Indicator blue 5x2.5cm
            //==================================================================================================================================
            case 1:
                if (Print) {
                    Print = false;
                    xData = StickerCase01(Sel, PRINTER_IP, sterile);
                }
                break;
            //==================================================================================================================================
            // Indicator blue 5x5cm
            //==================================================================================================================================
            case 2:
                if (Print) {
                    Print = false;
                    xData = StickerCase12(Sel, PRINTER_IP, sterile);
                }
                break;
            //==================================================================================================================================
            // Sticker Non Indicator Grey 5x8
            //==================================================================================================================================
            case 3:
                if (Print) {
                    Print = false;
                    xData = StickerCase11_2(Sel, PRINTER_IP, sterile);
                }
                break;
            case 4:
                if (Print) {
                    Print = false;
                    //xData = StickerCase13(Sel, PRINTER_IP, sterile);
                }
                break;
        }
        setData(xData);
    }
    //==============================================================================================
    // -- Function Case Sticker
    //==============================================================================================
    private String StickerCase01_SIRIRAJ(int Sel, ArrayList<String> PRINTER_IP ,List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        String Age1 = "";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);
        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));
        Tsc.openport(PrinterIP, 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                //sticker สีฟ้า
                ModelSterileDetail m = (ModelSterileDetail) li.next();

                Tsc.setup(50, 26, PSpeed, PDensity, 0, 2, 1);
                Tsc.sendcommand("DIRECTION 1,0\r\n");
                Tsc.clearbuffer();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                Tsc.sendpicture(10, 3, TextAsBitmap.getTextBitmap1(Itemname[0], 26));
                //QR_Code
                Tsc.qrcode(280, 50, "H", "5", "A", "0", "M2", "S1", m.getUsageCode());

                Tsc.sendpicture(10, 44, TextAsBitmap.getTextBitmap1(m.getUsageCode(), 20));

                //เครื่อง
                if (PMachine || PRound) {
                    Tsc.sendpicture(190, 44, TextAsBitmap.getTextBitmap1(m.getMachineName()+"/" + m.getSterileRoundNumber(), 20));
                }
                //วันแพค

                String yy1 = (Integer.parseInt(m.getSterileDate().substring(6, 10)) + 543) + "";
                String mfc1 = m.getSterileDate().substring(0, 2) + "/" + m.getSterileDate().substring(3, 5) + "/" + yy1.substring(2, 4);
                Tsc.sendpicture(10, 76, TextAsBitmap.getTextBitmap1("ผลิต " + mfc1 + " ( " + m.getAgeDay() + " วัน " + ")", 20));


                //วันหมดอายุ
                String yy2 = (Integer.parseInt( m.getExpireDate().substring(6,10) )+543)+"";
                String mfc2 =" หมดอายุ " + m.getExpireDate().substring(0,2) +"/"+m.getExpireDate().substring(3,5)+"/"+ yy2.substring(2,4);
                Tsc.sendpicture(7, 103, TextAsBitmap.getTextBitmap1(mfc2 , 30));

                Tsc.sendpicture(10, 136, TextAsBitmap.getTextBitmap1(m.getUsr_prepare()+" เตรียม / "
                        +m.getUsr_approve()+" ตรวจ ",18));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        Print = true;
        Tsc.closeport();
        return getID;
    }

    private String StickerCase02_SIRIRAJ(int Sel, ArrayList<String> PRINTER_IP ,List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);
        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));
        Tsc.openport(PrinterIP, 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                String Itemname[] = TextTwoLine.make2line(m.getItemname());
                Tsc.sendpicture(25, 30, TextAsBitmap.getTextBitmap1(Itemname[0], 27));
                Tsc.sendpicture(25, 80, TextAsBitmap.getTextBitmap(m.getUsageCode(), 26));
                Tsc.sendpicture(25, 125, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+" - เตรียม ", 26));
                Tsc.sendpicture(25, 170, TextAsBitmap.getTextBitmap(m.getUsr_approve()+" - ตรวจ ", 26));
                Tsc.qrcode(25, 275, "H", "5", "A", "0", "M2", "S1", m.getUsageCode());
                String yy1 = (Integer.parseInt( m.getSterileDate().substring(6,10) )+543)+"";
                String mfc1 = m.getSterileDate().substring(0,2) +"/"+m.getSterileDate().substring(3,5)+"/"+ yy1.substring(2,4);
                Tsc.sendpicture(160, 300, TextAsBitmap.getTextBitmap("MFG "+mfc1+" ("+ m.getAgeDay() +"วัน"+")", 24));
                String yy2 = (Integer.parseInt( m.getExpireDate().substring(6,10) )+543)+"";
                String mfc2 = m.getExpireDate().substring(0,2) +"/"+m.getExpireDate().substring(3,5)+"/"+ yy2.substring(2,4);
                Tsc.sendpicture(160, 330, TextAsBitmap.getTextBitmap1( "EXP "+ mfc2 , 32));
                Tsc.sendpicture(160, 265, TextAsBitmap.getTextBitmap("เครื่อง : "+m.getMachineName() + "  รอบ :" + m.getSterileRoundNumber(), 24));
                //----------------------------------------------------------------
                // Footer
                //----------------------------------------------------------------
                Tsc.sendpicture(35, 430, TextAsBitmap.getTextBitmap1(Itemname[0], 26));
                String Dep = m.getDepName2();
                Tsc.sendpicture(160, 470, TextAsBitmap.getTextBitmap(m.getUsageCode(),20));
                String DepName[] = TextTwoLine.make2line1(Dep);
                Tsc.sendpicture(160, 500, TextAsBitmap.getTextBitmap("MFG "+mfc1+" ("+ m.getAgeDay() +"วัน"+")", 23));
                Tsc.qrcode(35, 470, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                Tsc.sendpicture(160, 527, TextAsBitmap.getTextBitmap1( "EXP "+ mfc2 , 32));
                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Tsc.closeport();
        return getID;
    }

    private String StickerCase03_SIRIRAJ(int Sel, ArrayList<String> PRINTER_IP ,List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);
        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));
        Tsc.openport(PrinterIP, 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                String Itemname[] = TextTwoLine.make2line(m.getItemname());
                Tsc.sendpicture(25, 30, TextAsBitmap.getTextBitmap1(Itemname[0], 27));
                Tsc.sendpicture(25, 80, TextAsBitmap.getTextBitmap(m.getUsageCode(), 26));
                Tsc.sendpicture(25, 125, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+" - เตรียม ", 26));
                Tsc.sendpicture(25, 170, TextAsBitmap.getTextBitmap(m.getUsr_approve()+" - ตรวจ ", 26));
                Tsc.qrcode(25, 275, "H", "5", "A", "0", "M2", "S1", m.getUsageCode());
                String yy1 = (Integer.parseInt( m.getSterileDate().substring(6,10) )+543)+"";
                String mfc1 = m.getSterileDate().substring(0,2) +"/"+m.getSterileDate().substring(3,5)+"/"+ yy1.substring(2,4);
                Tsc.sendpicture(160, 300, TextAsBitmap.getTextBitmap("MFG "+mfc1+" ("+ m.getAgeDay() +"วัน"+")", 24));
                String yy2 = (Integer.parseInt( m.getExpireDate().substring(6,10) )+543)+"";
                String mfc2 = m.getExpireDate().substring(0,2) +"/"+m.getExpireDate().substring(3,5)+"/"+ yy2.substring(2,4);
                Tsc.sendpicture(160, 330, TextAsBitmap.getTextBitmap1( "EXP "+ mfc2 , 32));
                Tsc.sendpicture(160, 265, TextAsBitmap.getTextBitmap("เครื่อง : "+m.getMachineName() + "  รอบ :" + m.getSterileRoundNumber(), 24));
                //----------------------------------------------------------------
                // Footer
                //----------------------------------------------------------------
                Tsc.sendpicture(35, 430, TextAsBitmap.getTextBitmap1(Itemname[0], 26));
                String Dep = m.getDepName2();
                Tsc.sendpicture(160, 470, TextAsBitmap.getTextBitmap(m.getUsageCode(),20));
                String DepName[] = TextTwoLine.make2line1(Dep);
                Tsc.sendpicture(160, 500, TextAsBitmap.getTextBitmap("MFG "+mfc1+" ("+ m.getAgeDay() +"วัน"+")", 23));
                Tsc.qrcode(35, 470, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                Tsc.sendpicture(160, 527, TextAsBitmap.getTextBitmap1( "EXP "+ mfc2 , 32));
                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Tsc.closeport();
        return getID;
    }

    private String StickerCase04_SIRIRAJ(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        String Age1 = "";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);
        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));
        Log.d("BANKRT77",Sel+"");
        Log.d("BANKRT77",PRINTER_IP+"");
        Log.d("BANKRT77",B_ID+"");
        //Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);
        Tsc.openport(PrinterIP, 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                String Itemname[] = TextTwoLine.make2line(m.getItemname());
                Tsc.sendpicture(20, 10, TextAsBitmap.getTextBitmap(Itemname[0], 30));
                Tsc.sendpicture(20, 55, TextAsBitmap.getTextBitmap(m.getUsageCode(), 26));
                Tsc.sendpicture(20, 95, TextAsBitmap.getTextBitmap("เครื่อง :" + m.getMachineName()+"    รอบ :"+ m.getSterileRoundNumber(), 26));
                Tsc.sendpicture(20, 135, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"- เตรียม", 26));
                Tsc.sendpicture(20, 175, TextAsBitmap.getTextBitmap(m.getUsr_approve()+"- ตรวจ", 26));
                Tsc.qrcode(20, 240, "H", "5", "A", "0", "M2", "S1", m.getUsageCode());
                String yy1 = (Integer.parseInt( m.getSterileDate().substring(6,10) )+543)+"";
                String mfc1 = m.getSterileDate().substring(0,2) +"/"+m.getSterileDate().substring(3,5)+"/"+ yy1.substring(2,4);
                Tsc.sendpicture(160, 240, TextAsBitmap.getTextBitmap("MFG : " + mfc1 + " ( " + m.getAgeDay() + " วัน " + ")", 20));
                String yy2 = (Integer.parseInt( m.getExpireDate().substring(6,10) )+543)+"";
                String mfc2 = m.getExpireDate().substring(0,2) +"/"+m.getExpireDate().substring(3,5)+"/"+ yy2.substring(2,4);
                Tsc.sendpicture(170, 270, TextAsBitmap.getTextBitmap("EXP : " + mfc2, 32));
                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Tsc.closeport();
        return getID;
    }

    private String StickerCase01(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        String Age1 = "";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);
        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));
        Tsc.openport(PrinterIP, 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {
                //sticker สีฟ้า
                ModelSterileDetail m = (ModelSterileDetail) li.next();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                Tsc.sendpicture(10, 2, TextAsBitmap.getTextBitmap(Itemname[0], 28));
                //QR_Code
                Tsc.qrcode(280, 37, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());

                Tsc.sendpicture(10, 54, TextAsBitmap.getTextBitmap(m.getUsageCode(), 18));
                //เครื่อง
                if (PMachine || PRound) {
                    Tsc.sendpicture(180, 56, TextAsBitmap.getTextBitmap(m.getMachineName()+"/" + m.getSterileRoundNumber(), 18));
                }
                //วันแพค
                String yy1 = (Integer.parseInt(m.getSterileDate().substring(6, 10)) + 543) + "";
                String mfc1 = m.getSterileDate().substring(0, 2) + "/" + m.getSterileDate().substring(3, 5) + "/" + yy1.substring(2, 4);
                Tsc.sendpicture(10, 75, TextAsBitmap.getTextBitmap("ผลิต " + mfc1 + " ( " + m.getAgeDay() + " วัน " + ")", 20));

                //วันหมดอายุ
                String yy2 = (Integer.parseInt( m.getExpireDate().substring(6,10) )+543)+"";
                String mfc2 =" หมดอายุ " + m.getExpireDate().substring(0,2) +"/"+m.getExpireDate().substring(3,5)+"/"+ yy2.substring(2,4);
                Tsc.sendpicture(5, 103, TextAsBitmap.getTextBitmap(mfc2 , 30));

                Tsc.sendpicture(10, 136, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+" -เตรียม / "
                        +m.getUsr_approve()+" -ตรวจ",18));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        Print = true;
        Tsc.closeport();
        return getID;
    }

    private String StickerCase02(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(50, 46, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                Tsc.sendpicture(15, 5, TextAsBitmap.getTextBitmap(Itemname[0], 26));

                //แผนก
                if (PDept) {
                    Tsc.sendpicture(125, 58, TextAsBitmap.getTextBitmap("[ "+m.getDepName2()+" ]", 20));
                }
                //QR_Code
                Tsc.qrcode(10, 63, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                //เครื่อง
                if (PMachine || PRound) {
                    Tsc.sendpicture(125, 82, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName()+" / "+"รอบ:" + m.getSterileRoundNumber(), 18));
                }
                //วันแพค
                Tsc.sendpicture(125, 105, TextAsBitmap.getTextBitmap("MFG "+m.getSterileDate()+" (" + m.getAgeDay()+" วัน "+ ")", 20));
                //วันหมดอายุ
                Tsc.sendpicture(195, 134, TextAsBitmap.getTextBitmap(m.getExpireDate() , 23));
                Tsc.sendpicture(15, 165, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม/"
                        +m.getUsr_approve()+"ตรวจ/"
                        +m.getUsr_sterile()+"ฆ่าเชื้อ", 18));

                Tsc.sendpicture(25, 205, TextAsBitmap.getTextBitmap(Itemname[0], 26));

                //แผนก
                if (PDept) {
                    Tsc.sendpicture(30, 235, TextAsBitmap.getTextBitmap("[ "+m.getDepName2()+" ]", 18));
                }
                //QR_Code
                Tsc.qrcode(290, 233, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                //เครื่อง
                if (PMachine || PRound) {
                    Tsc.sendpicture(20, 257, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName()+" / "+"รอบ:" + m.getSterileRoundNumber(), 18));
                }
                //วันแพค
                Tsc.sendpicture(20, 278, TextAsBitmap.getTextBitmap("MFG "+m.getSterileDate()+" (" + m.getAgeDay()+" วัน "+ ")", 18));
                //วันหมดอายุ
                Tsc.sendpicture(100, 305, TextAsBitmap.getTextBitmap(m.getExpireDate() , 23));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Tsc.closeport();
        return getID;
    }

    private String StickerCase03(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        String Age1 = "";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);
        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));
        Tsc.openport(PrinterIP, 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {
                //sticker สีฟ้า
                ModelSterileDetail m = (ModelSterileDetail) li.next();

                Tsc.setup(50, 26, PSpeed, PDensity, 0, 2, 1);
                Tsc.sendcommand("DIRECTION 1,0\r\n");
                Tsc.clearbuffer();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                Tsc.sendpicture(10, 2, TextAsBitmap.getTextBitmap1(Itemname[0], 28));

                Tsc.qrcode(280, 37, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());

                Tsc.sendpicture(10, 44, TextAsBitmap.getTextBitmap1(m.getUsageCode(), 20));

                if (PMachine || PRound) {
                    Tsc.sendpicture(180, 44, TextAsBitmap.getTextBitmap1(m.getMachineName()+"/" + m.getSterileRoundNumber(), 20));
                }

                String yy1 = (Integer.parseInt(m.getSterileDate().substring(6, 10)) + 543) + "";
                String mfc1 = m.getSterileDate().substring(0, 2) + "/" + m.getSterileDate().substring(3, 5) + "/" + yy1.substring(2, 4);
                Tsc.sendpicture(10, 76, TextAsBitmap.getTextBitmap1("ผลิต " + mfc1 + " ( " + m.getAgeDay() + " วัน " + ")", 20));

                //วันหมดอายุ
                String yy2 = (Integer.parseInt( m.getExpireDate().substring(6,10) )+543)+"";
                String mfc2 =" หมดอายุ " + m.getExpireDate().substring(0,2) +"/"+m.getExpireDate().substring(3,5)+"/"+ yy2.substring(2,4);
                Tsc.sendpicture(5, 103, TextAsBitmap.getTextBitmap1(mfc2 , 30));

                Tsc.sendpicture(10, 136, TextAsBitmap.getTextBitmap1(m.getUsr_prepare()+" เตรียม / "
                        +m.getUsr_approve()+" ตรวจ ",18));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Tsc.closeport();
        return getID;
    }

    private String StickerCase04(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(60, 78, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                Tsc.sendpicture(15, 5, TextAsBitmap.getTextBitmap(Itemname[0], 26));

                //แผนก
                if (PDept) {
                    Tsc.sendpicture(125, 58, TextAsBitmap.getTextBitmap("[ "+m.getDepName2()+" ]", 20));
                }
                //QR_Code
                Tsc.qrcode(10, 63, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                //เครื่อง
                if (PMachine || PRound) {
                    Tsc.sendpicture(125, 82, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName()+" / "+"รอบ:" + m.getSterileRoundNumber(), 18));
                }
                //วันแพค
                Tsc.sendpicture(125, 105, TextAsBitmap.getTextBitmap("MFG "+m.getSterileDate()+" (" + m.getAgeDay()+" วัน "+ ")"  , 20));
                //วันหมดอายุ
                Tsc.sendpicture(195, 134, TextAsBitmap.getTextBitmap(m.getExpireDate(), 23));
                Tsc.sendpicture(15, 165, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม/"
                        +m.getUsr_approve()+"ตรวจ/"
                        +m.getUsr_sterile()+"ฆ่าเชื้อ", 18));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        Tsc.closeport();
        return getID;
    }

    private String StickerCase05(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(58, 58, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                Tsc.sendpicture(15, 5, TextAsBitmap.getTextBitmap(Itemname[0], 26));

                if (PDept) {
                    Tsc.sendpicture(125, 58, TextAsBitmap.getTextBitmap("[ "+m.getDepName2()+" ]", 20));
                }

                Tsc.qrcode(10, 63, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());

                if (PMachine || PRound) {
                    Tsc.sendpicture(125, 82, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName()+" / "+"รอบ:" + m.getSterileRoundNumber(), 18));
                }

                Tsc.sendpicture(125, 105, TextAsBitmap.getTextBitmap("MFG "+m.getSterileDate(), 20));

                Tsc.sendpicture(195, 134, TextAsBitmap.getTextBitmap(m.getExpireDate() + " (" + m.getAgeDay()+")", 23));
                Tsc.sendpicture(15, 165, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม/"
                        +m.getUsr_approve()+"ตรวจ/"
                        +m.getUsr_sterile()+"ฆ่าเชื้อ", 18));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Tsc.closeport();
        return getID;
    }

    private String StickerCase06(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(53, 28, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                Tsc.sendpicture(15, 5, TextAsBitmap.getTextBitmap(Itemname[0], 26));

                if (PDept) {
                    Tsc.sendpicture(125, 58, TextAsBitmap.getTextBitmap("[ "+m.getDepName2()+" ]", 20));
                }

                Tsc.qrcode(10, 63, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());

                if (PMachine || PRound) {
                    Tsc.sendpicture(125, 82, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName()+" / "+"รอบ:" + m.getSterileRoundNumber(), 18));
                }

                Tsc.sendpicture(125, 105, TextAsBitmap.getTextBitmap("MFG "+m.getSterileDate(), 20));

                Tsc.sendpicture(195, 134, TextAsBitmap.getTextBitmap(m.getExpireDate() + " (" + m.getAgeDay()+")", 23));
                Tsc.sendpicture(15, 165, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม/"
                        +m.getUsr_approve()+"ตรวจ/"
                        +m.getUsr_sterile()+"ฆ่าเชื้อ", 18));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        Tsc.closeport();
        return getID;
    }

    private String StickerCase07(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(58, 53, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                Tsc.sendpicture(15, 5, TextAsBitmap.getTextBitmap(Itemname[0], 26));

                if (PDept) {
                    Tsc.sendpicture(125, 58, TextAsBitmap.getTextBitmap("[ "+m.getDepName2()+" ]", 20));
                }

                Tsc.qrcode(10, 63, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());

                if (PMachine || PRound) {
                    Tsc.sendpicture(125, 82, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName()+" / "+"รอบ:" + m.getSterileRoundNumber(), 18));
                }

                Tsc.sendpicture(125, 105, TextAsBitmap.getTextBitmap("MFG "+m.getSterileDate(), 20));

                Tsc.sendpicture(195, 134, TextAsBitmap.getTextBitmap(m.getExpireDate() + " (" + m.getAgeDay()+")", 23));
                Tsc.sendpicture(15, 165, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม/"
                        +m.getUsr_approve()+"ตรวจ/"
                        +m.getUsr_sterile()+"ฆ่าเชื้อ", 18));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Tsc.closeport();
        return getID;
    }

    private String StickerCase08(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(54, 48, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                Tsc.sendpicture(15, 5, TextAsBitmap.getTextBitmap(Itemname[0], 26));

                if (PDept) {
                    Tsc.sendpicture(125, 58, TextAsBitmap.getTextBitmap("[ "+m.getDepName2()+" ]", 20));
                }

                Tsc.qrcode(10, 63, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());

                if (PMachine || PRound) {
                    Tsc.sendpicture(125, 82, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName()+" / "+"รอบ:" + m.getSterileRoundNumber(), 18));
                }

                Tsc.sendpicture(125, 105, TextAsBitmap.getTextBitmap("MFG "+m.getSterileDate(), 20));

                Tsc.sendpicture(195, 134, TextAsBitmap.getTextBitmap(m.getExpireDate() + " (" + m.getAgeDay()+")", 23));
                Tsc.sendpicture(15, 165, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม/"
                        +m.getUsr_approve()+"ตรวจ/"
                        +m.getUsr_sterile()+"ฆ่าเชื้อ", 18));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Tsc.closeport();
        return getID;
    }

    private String StickerCase09(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(53, 83, PSpeed, PDensity, 0, 3, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

//                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                Tsc.sendpicture(15, 5, TextAsBitmap.getTextBitmap(Itemname[0], 26));
//                        }else{
//                            Tsc.sendpicture(20, 2, TextAsBitmap.getTextBitmap(Itemname[0], 20));
//                            Tsc.sendpicture(20, 22, TextAsBitmap.getTextBitmap(Itemname[1], 20));
//                        }

                //แผนก
                if (PDept) {
                    Tsc.sendpicture(125, 58, TextAsBitmap.getTextBitmap("[ "+m.getDepName2()+" ]", 20));
                }
                //QR_Code
                Tsc.qrcode(10, 63, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                //เครื่อง
                if (PMachine || PRound) {
                    Tsc.sendpicture(125, 82, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName()+" / "+"รอบ:" + m.getSterileRoundNumber(), 18));
                }
                //วันแพค
                Tsc.sendpicture(125, 105, TextAsBitmap.getTextBitmap("MFG "+m.getSterileDate(), 20));
                //วันหมดอายุ
                Tsc.sendpicture(195, 134, TextAsBitmap.getTextBitmap(m.getExpireDate() + " (" + m.getAgeDay()+")", 23));
                Tsc.sendpicture(15, 165, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม/"
                        +m.getUsr_approve()+"ตรวจ/"
                        +m.getUsr_sterile()+"ฆ่าเชื้อ", 18));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Tsc.closeport();
        return getID;
    }

    private String StickerCase10(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {

            Tsc.setup(53, 83, PSpeed, PDensity, 0, 3, 0);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

//                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                Tsc.sendpicture(15, 5, TextAsBitmap.getTextBitmap(Itemname[0], 26));
//                        }else{
//                            Tsc.sendpicture(20, 2, TextAsBitmap.getTextBitmap(Itemname[0], 20));
//                            Tsc.sendpicture(20, 22, TextAsBitmap.getTextBitmap(Itemname[1], 20));
//                        }

                //แผนก
                if (PDept) {
                    Tsc.sendpicture(125, 58, TextAsBitmap.getTextBitmap("[ "+m.getDepName2()+" ]", 20));
                }
                //QR_Code
                Tsc.qrcode(10, 63, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                //เครื่อง
                if (PMachine || PRound) {
                    Tsc.sendpicture(125, 82, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName()+" / "+"รอบ:" + m.getSterileRoundNumber(), 18));
                }
                //วันแพค
                Tsc.sendpicture(125, 105, TextAsBitmap.getTextBitmap("MFG "+m.getSterileDate(), 20));
                //วันหมดอายุ
                Tsc.sendpicture(195, 134, TextAsBitmap.getTextBitmap(m.getExpireDate() + " (" + m.getAgeDay()+")", 23));
                Tsc.sendpicture(15, 165, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+"เตรียม/"
                        +m.getUsr_approve()+"ตรวจ/"
                        +m.getUsr_sterile()+"ฆ่าเชื้อ", 18));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Tsc.closeport();
        return getID;
    }

    private String StickerCase11(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        String Age1 = "";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        System.out.println("Sel"+ Sel);
        System.out.println("Printer"+PRINTER_IP.size());

        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);

        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));

        System.out.println("PrinterIP = " + PrinterIP);
        System.out.println("Heigth = " + Heigth);
        System.out.println("Width = " + Width);

        //Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);

        Tsc.openport(PrinterIP, 9100);

        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 0,0\r\n");
            try {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                int Page = 0;
                int row = 0;
                int itemCnt = 24;
                int last_row_count = 0;
                String[] arrOfStr = null;
                //================
                //    Count Page
                //================
                try {
                    arrOfStr = m.getItemSetData().split("%");
                    Page = (arrOfStr.length / itemCnt);
                    last_row_count = (arrOfStr.length % itemCnt);
                }catch (Exception e){
                    Page = 0;
                    last_row_count = 0;
                }
                if( last_row_count > 0 ) Page++;

                for(int z=1;z<(Page+1);z++) {
                    Tsc.clearbuffer();
                    int row_y = 0;

                    Tsc.sendpicture(45, 40, TextAsBitmap.getTextBitmap(Itemname[0], 26));

                    //วันแพค
                    String yy1 = (Integer.parseInt( m.getSterileDate().substring(6,10) )+543)+"";
                    String mfc1 = m.getSterileDate().substring(0,2) +"/"+m.getSterileDate().substring(3,5)+"/"+ yy1.substring(2,4);
                    Tsc.sendpicture(40, 415, TextAsBitmap.getTextBitmap("ผลิต : " + mfc1 + " ( " + m.getAgeDay() + " วัน " + ")", 26));
                    //วันหมดอายุ
                    String yy2 = (Integer.parseInt( m.getExpireDate().substring(6,10) )+543)+"";
                    String mfc2 = m.getExpireDate().substring(0,2) +"/"+m.getExpireDate().substring(3,5)+"/"+ yy2.substring(2,4);
                    Tsc.sendpicture(40, 445, TextAsBitmap.getTextBitmap( "หมดอายุ ", 32));
                    Tsc.sendpicture(200, 445, TextAsBitmap.getTextBitmap(mfc2 , 40));

                    //QR_Code
                    Tsc.qrcode(40, 500, "H", "3.5", "A", "0", "M2", "S1", m.getUsageCode());
                    //Item_Code
                    Tsc.sendpicture(135, 490, TextAsBitmap.getTextBitmap("No."+m.getUsageCode()+"", 20));
                    //เครื่อง
                    Tsc.sendpicture(300, 490, TextAsBitmap.getTextBitmap("ตู้ :" + m.getMachineName()+"/" + m.getSterileRoundNumber(), 20));
                    Tsc.sendpicture(135, 515, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+" -เตรียม", 20));
                    Tsc.sendpicture(135, 540, TextAsBitmap.getTextBitmap(m.getUsr_approve()+" -ตรวจ", 18));
//                    Tsc.sendpicture(45, 490, TextAsBitmap.getTextBitmap(m.getUsr_sterile()+"-ฆ่าเชื้อ", 18));


                    // Print Item Detail
                    int row_cnt = (z*itemCnt);
                    if(z==Page) row_cnt = ((Page-1)*itemCnt)+last_row_count;

//                    Log.d("OOOO",z +" == "+Page);
//                    Log.d("OOOO",row_cnt+"");
//                    Log.d("OOOO",row+"");

                    for (int l = row; l < row_cnt; l+=2) {
                        //Log.d("OOOO",l+"/"+z+" :: "+arrOfStr[l]+"");

                        try {
                            if(arrOfStr[l].length() > 0) {
                                String[] vb1 = arrOfStr[l].split("#");
                                //Log.d("OOOO",vb1[0]+" :: "+vb1[0].length());
                                Tsc.sendpicture(40, 80 + (row_y * 27), TextAsBitmap.getTextBitmap(vb1[0].substring(0, vb1[0].length()<12?vb1[0].length():12) + "...", 18));
                                String[] pp = vb1[1].split(" ");
                                Tsc.sendpicture(185, 80 + (row_y * 27), TextAsBitmap.getTextBitmap(pp[0], 18));
                            }
                        }catch (Exception e){}
                        //Log.d("OOOO",(l+1)+"/"+z+" :: "+arrOfStr[(l+1)]+"");
                        try {
                            if(arrOfStr[(l+1)].length() > 0) {
                                String[] vb2 = arrOfStr[(l+1)].split("#");
                                //Log.d("OOOO",vb2[0]+" :: "+vb2[0].length());
                                Tsc.sendpicture(215, 80 + (row_y * 27), TextAsBitmap.getTextBitmap(vb2[0].substring(0, vb2[0].length()<12?vb2[0].length():12) + "...", 18));
                                String[] pp2 = vb2[1].split(" ");
                                Tsc.sendpicture(365, 80 + (row_y * 27), TextAsBitmap.getTextBitmap(pp2[0], 18));
                            }
                        }catch (Exception e){}

                        row_y++;
                    }


                    if(pQty > Integer.parseInt( m.getQty() ))
                        Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                    else
                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                    row = (z*itemCnt);
                }

                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Tsc.closeport();
        return getID;
    }

    private String StickerCase11_2(int Sel, ArrayList<String> PRINTER_IP,List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);
        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));
        Tsc.openport(PrinterIP, 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 3, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            try {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                int Page = 0;
                int row = 0;
                int itemCnt = 24;
                int last_row_count = 0;
                String[] arrOfStr = null;
                //================
                //    Count Page
                //================
                try {
                    arrOfStr = m.getItemSetData().split("%");
                    Page = (arrOfStr.length / itemCnt);
                    last_row_count = (arrOfStr.length % itemCnt);
                }catch (Exception e){
                    Page = 0;
                    last_row_count = 0;
                }
                if( last_row_count > 0 ) Page++;

                for(int z=1;z<(Page+1);z++) {
                    Tsc.clearbuffer();
                    int row_y = 0;

                    Tsc.sendpicture(30, 30, TextAsBitmap.getTextBitmap(Itemname[0], 30));

                    Tsc.sendpicture(30, 70, TextAsBitmap.getTextBitmap("No."+m.getUsageCode()+"", 26));

                    Tsc.sendpicture(30, 453, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+" - เตรียม / " +m.getUsr_approve() + " - ตรวจ", 19));
                    //Tsc.sendpicture(225, 435, TextAsBitmap.getTextBitmap(m.getUsr_approve()+" -ตรวจ ", 19));

                    Tsc.sendpicture(30, 480, TextAsBitmap.getTextBitmap("ตู้ : " + m.getMachineName()+"/" + m.getSterileRoundNumber(), 20));

                    //QR_Code
                    Tsc.qrcode(30, 510, "H", "5", "A", "0", "M2", "S1", m.getUsageCode());

                    //วันแพค
                    String yy1 = (Integer.parseInt( m.getSterileDate().substring(6,10) )+543)+"";
                    String mfc1 = m.getSterileDate().substring(0,2) +"/"+m.getSterileDate().substring(3,5)+"/"+ yy1.substring(2,4);
                    Tsc.sendpicture(170, 515, TextAsBitmap.getTextBitmap("ผลิต "+mfc1+" ("+ m.getAgeDay() +"วัน"+")", 24));

                    //วันหมดอายุ
                    String yy2 = (Integer.parseInt( m.getExpireDate().substring(6,10) )+543)+"";
                    String mfc2 = m.getExpireDate().substring(0,2) +"/"+m.getExpireDate().substring(3,5)+"/"+ yy2.substring(2,4);
                    Tsc.sendpicture(170, 550, TextAsBitmap.getTextBitmap( "หมดอายุ ", 28));
                    Tsc.sendpicture(280, 550, TextAsBitmap.getTextBitmap(mfc2 , 28));

                    // Print Item Detail
                    int row_cnt = (z*itemCnt);
                    if(z==Page) row_cnt = ((Page-1)*itemCnt)+last_row_count;

                    if (last_row_count <= 12) {
                        for (int l = row; l < row_cnt; l++) {
                            String[] vb = arrOfStr[l].split("#");
                            if (!arrOfStr[l].equals("-")) {
                                String[] vb1 = arrOfStr[l].split("#");
                                Tsc.sendpicture(30, 105 + (row_y * 29), TextAsBitmap.getTextBitmap(vb1[0], 22));
                                String[] pp = vb1[1].split(" ");
                                Tsc.sendpicture(385, 105 + (row_y * 29), TextAsBitmap.getTextBitmap(pp[0], 22));
                                row_y++;
                            }else {
                                Tsc.sendpicture( + 30,  + 105 + (row_y * 29), TextAsBitmap.getTextBitmap("", 22));
                                row_y++;
                            }
                        }
                    }else if (last_row_count >= 13){
                        for (int l = row; l < row_cnt; l++) {
                            if (l < 12) {
                                String[] vb1 = arrOfStr[l].split("#");
                                Tsc.sendpicture(30, 105 + (row_y * 29), TextAsBitmap.getTextBitmap(vb1[0].substring(0, vb1[0].length() < 12 ? vb1[0].length() : 12) + "...", 18));
                                String[] pp = vb1[1].split(" ");
                                Tsc.sendpicture(220, 105 + (row_y * 29), TextAsBitmap.getTextBitmap(pp[0], 20));

                            } else {
                                String[] vb2 = arrOfStr[(l)].split("#");
                                Tsc.sendpicture(245, -240 + (row_y * 29), TextAsBitmap.getTextBitmap(vb2[0].substring(0, vb2[0].length() < 12 ? vb2[0].length() : 12) + "...", 18));
                                String[] pp2 = vb2[1].split(" ");
                                Tsc.sendpicture(385, -240 + (row_y * 29), TextAsBitmap.getTextBitmap(pp2[0], 20));
                            }
                            row_y++;
                        }
                    }else if (last_row_count >= 25){
                        for (int l = row; l < row_cnt; l++) {
                            if (l < 12) {
                                String[] vb1 = arrOfStr[l].split("#");
                                Tsc.sendpicture(30, 105 + (row_y * 29), TextAsBitmap.getTextBitmap(vb1[0].substring(0, vb1[0].length() < 12 ? vb1[0].length() : 12) + "...", 18));
                                String[] pp = vb1[1].split(" ");
                                Tsc.sendpicture(220, 105 + (row_y * 29), TextAsBitmap.getTextBitmap(pp[0], 20));

                            } else {
                                String[] vb2 = arrOfStr[(l)].split("#");
                                Tsc.sendpicture(245, -240 + (row_y * 29), TextAsBitmap.getTextBitmap(vb2[0].substring(0, vb2[0].length() < 12 ? vb2[0].length() : 12) + "...", 18));
                                String[] pp2 = vb2[1].split(" ");
                                Tsc.sendpicture(385, -240 + (row_y * 29), TextAsBitmap.getTextBitmap(pp2[0], 20));
                            }
                            row_y++;
                        }
                    }

                    if(pQty > Integer.parseInt( m.getQty() ))
                        Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                    else
                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                    row = (z*itemCnt);
                }

                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Tsc.closeport();
        return getID ;
    }

    private String StickerCase11_3(int Sel, ArrayList<String> PRINTER_IP,List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();

        System.out.println("Sel"+ Sel);
        System.out.println("Printer"+PRINTER_IP.size());

        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);

        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));

        System.out.println("PrinterIP = " + PrinterIP);
        System.out.println("Heigth = " + Heigth);
        System.out.println("Width = " + Width);

        Tsc.openport(PrinterIP, 9100);

        Iterator li = DATA_MODEL.iterator();

        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 3, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            try {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                String[] arrOfStr = m.getItemSetData().split("%");

                //=============================================================
                int xCpoy = 0;
                if (pQty > Integer.parseInt(m.getQty()))
                    xCpoy = pQty ;
                else
                    xCpoy = Integer.parseInt( m.getQty() );

                for(int xQ = 0; xQ < xCpoy ; xQ++) {

                    //================
                    //    Count Page
                    int Page = (arrOfStr.length / 24);
                    int last_row_count = (arrOfStr.length % 24);
                    if (last_row_count > 0) Page++;
                    int row = 0;
                    //================
                    for (int p = 1; p < (Page + 1); p++) {
                        Tsc.clearbuffer();
                        int row_y = 0;

                        Tsc.sendpicture(95, 60, TextAsBitmap.getTextBitmap(Itemname[0], 30));

                        Tsc.sendpicture(95, 95, TextAsBitmap.getTextBitmap("No." + m.getUsageCode() + "", 26));

                        Tsc.sendpicture(95, 445, TextAsBitmap.getTextBitmap(m.getUsr_prepare() + " - เตรียม / " + m.getUsr_approve() + " - ตรวจ", 19));
                        //Tsc.sendpicture(225, 435, TextAsBitmap.getTextBitmap(m.getUsr_approve()+" -ตรวจ ", 19));

                        Tsc.sendpicture(95, 475, TextAsBitmap.getTextBitmap("ตู้ : " + m.getMachineName() + "/" + m.getSterileRoundNumber(), 20));

                        //QR_Code
                        Tsc.qrcode(30, 510, "H", "5", "A", "0", "M2", "S1", m.getUsageCode());

                        //วันแพค
                        String yy1 = (Integer.parseInt(m.getSterileDate().substring(6, 10)) + 543) + "";
                        String mfc1 = m.getSterileDate().substring(0, 2) + "/" + m.getSterileDate().substring(3, 5) + "/" + yy1.substring(2, 4);
                        Tsc.sendpicture(170, 510, TextAsBitmap.getTextBitmap("ผลิต " + mfc1 + " (" + m.getAgeDay() + "วัน" + ")", 24));


                        //วันหมดอายุ
                        String yy2 = (Integer.parseInt(m.getExpireDate().substring(6, 10)) + 543) + "";
                        String mfc2 = m.getExpireDate().substring(0, 2) + "/" + m.getExpireDate().substring(3, 5) + "/" + yy2.substring(2, 4);
                        Tsc.sendpicture(170, 547, TextAsBitmap.getTextBitmap("หมดอายุ ", 28));
                        Tsc.sendpicture(280, 547, TextAsBitmap.getTextBitmap(mfc2, 28));

                        // Print Item Detail
                        int row_cnt = (p * 24);
                        if (p == Page)
                            if (arrOfStr.length != 24) row_cnt = ((Page - 1) * 24) + last_row_count;
                        for (int l = row; l < row_cnt; l++) {
                            String[] vb = arrOfStr[l].split("#");

                            if (!arrOfStr[l].equals("-")) {
                                Tsc.sendpicture(+95, +130 + (row_y * 25), TextAsBitmap.getTextBitmap(vb[0].substring(0, vb[0].length() < 12 ? vb[0].length() : 12) + "...", 18));
                                String[] pp = vb[1].split(" ");
                                Log.d("BANKMUI",pp+"");
                                Tsc.sendpicture(240, 130 + (row_y * 25), TextAsBitmap.getTextBitmap(pp[0], 20));
                                row_y++;
                            } else {
                                Tsc.sendpicture(+95, +130 + (row_y * 25), TextAsBitmap.getTextBitmap("", 18));
                                row_y++;
                            }

                            //Log.d("BANKMUI",vb[0]+"");
                            //Log.d("BANKMUI",vb[1]+"");
                            //Log.d("BANKMUI",arrOfStr[l]+"");

                        }
                        Tsc.sendcommand("PRINT 1\r\n");
                        row = (p * 24);
                    }
                }

                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Tsc.closeport();
        return getID ;
    }

    private String StickerCase11_4(int Sel, ArrayList<String> PRINTER_IP,List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);

        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));

        Log.d("BANKRT77",Sel+"");
        Log.d("BANKRT77",PRINTER_IP+"");
        Log.d("BANKRT77",B_ID+"");

        Tsc.openport(PrinterIP, 9100);

        Iterator li = DATA_MODEL.iterator();

        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 3, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            try {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                int Page = 0;
                int row = 0;
                int itemCnt = 24;
                int last_row_count = 0;
                String[] arrOfStr = null;
                //================
                //    Count Page
                //================
                try {
                    arrOfStr = m.getItemSetData().split("%");
                    Page = (arrOfStr.length / itemCnt);
                    last_row_count = (arrOfStr.length % itemCnt);
                }catch (Exception e){
                    Page = 0;
                    last_row_count = 0;
                }
                if( last_row_count > 0 ) Page++;

                for(int z=1;z<(Page+1);z++) {
                    Tsc.clearbuffer();
                    int row_y = 0;

                    Tsc.sendpicture(95, 55, TextAsBitmap.getTextBitmap(Itemname[0], 30));

                    Tsc.sendpicture(95, 95, TextAsBitmap.getTextBitmap("No."+m.getUsageCode()+"", 26));

                    Tsc.sendpicture(165, 560, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+" - เตรียม", 19));

                    Tsc.sendpicture(165, 585, TextAsBitmap.getTextBitmap(m.getUsr_approve() +" - ตรวจ", 19));

                    Tsc.sendpicture(165, 610, TextAsBitmap.getTextBitmap("ตู้ : " + m.getMachineName()+"/" + m.getSterileRoundNumber(), 20));

                    //QR_Code
                    Tsc.qrcode(30, 510, "H", "5", "A", "0", "M2", "S1", m.getUsageCode());

                    //วันแพค
                    String yy1 = (Integer.parseInt( m.getSterileDate().substring(6,10) )+543)+"";
                    String mfc1 = m.getSterileDate().substring(0,2) +"/"+m.getSterileDate().substring(3,5)+"/"+ yy1.substring(2,4);
                    Tsc.sendpicture(165, 500, TextAsBitmap.getTextBitmap("ผลิต "+mfc1+" ("+ m.getAgeDay() +"วัน"+")", 24));

                    //วันหมดอายุ
                    String yy2 = (Integer.parseInt( m.getExpireDate().substring(6,10) )+543)+"";
                    String mfc2 = m.getExpireDate().substring(0,2) +"/"+m.getExpireDate().substring(3,5)+"/"+ yy2.substring(2,4);
                    Tsc.sendpicture(165, 527, TextAsBitmap.getTextBitmap( "หมดอายุ ", 28));
                    Tsc.sendpicture(280, 527, TextAsBitmap.getTextBitmap(mfc2 , 28));

                    // Print Item Detail
                    int row_cnt = (z*itemCnt);
                    if(z==Page) row_cnt = ((Page-1)*itemCnt)+last_row_count;

                    if (last_row_count <= 12) {
                        for (int l = row; l < row_cnt; l++) {
                            String[] vb = arrOfStr[l].split("#");
                            if (!arrOfStr[l].equals("-")) {
                                String[] vb1 = arrOfStr[l].split("#");
                                Tsc.sendpicture(95, 130 + (row_y * 31), TextAsBitmap.getTextBitmap(vb1[0], 22));
                                String[] pp = vb1[1].split(" ");
                                Tsc.sendpicture(385, 130 + (row_y * 31), TextAsBitmap.getTextBitmap(pp[0], 22));
                                row_y++;
                            }else {
                                Tsc.sendpicture( + 95,  + 130 + (row_y * 31), TextAsBitmap.getTextBitmap("", 22));
                                row_y++;
                            }
                        }
                    }else {
                        for (int l = row; l < row_cnt; l++) {
                            if (l < 12) {
                                String[] vb1 = arrOfStr[l].split("#");
                                Tsc.sendpicture(95, 130 + (row_y * 31), TextAsBitmap.getTextBitmap(vb1[0].substring(0, vb1[0].length() < 12 ? vb1[0].length() : 12) + "...", 18));
                                String[] pp = vb1[1].split(" ");
                                Tsc.sendpicture(220, 130 + (row_y * 31), TextAsBitmap.getTextBitmap(pp[0], 20));

                            } else {
                                String[] vb2 = arrOfStr[(l)].split("#");
                                Tsc.sendpicture(245, -245 + (row_y * 31), TextAsBitmap.getTextBitmap(vb2[0].substring(0, vb2[0].length() < 12 ? vb2[0].length() : 12) + "...", 18));
                                String[] pp2 = vb2[1].split(" ");
                                Tsc.sendpicture(385, -245 + (row_y * 31), TextAsBitmap.getTextBitmap(pp2[0], 20));
                            }
                            row_y++;
                        }
                    }

                    if(pQty > Integer.parseInt( m.getQty() ))
                        Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                    else
                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                    row = (z*itemCnt);
                }

                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Print = true;
        Tsc.closeport();
        return getID ;
    }

    private String StickerCase12_2(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        String Age1 = "";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        System.out.println("Sel"+ Sel);
        System.out.println("Printer"+PRINTER_IP.size());

        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);

        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));

        System.out.println("PrinterIP = " + PrinterIP);
        System.out.println("Heigth = " + Heigth);
        System.out.println("Width = " + Width);

        //Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);

        Tsc.openport(PrinterIP, 9100);

        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();
                String Itemname[] = TextTwoLine.make2line(m.getItemname());

//                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                Tsc.sendpicture(30, 7, TextAsBitmap.getTextBitmap(m.getItemname(), 30));

//                        }else{
//                            Tsc.sendpicture(20, 2, TextAsBitmap.getTextBitmap(Itemname[0], 20));
//                            Tsc.sendpicture(20, 22, TextAsBitmap.getTextBitmap(Itemname[1], 20));
//                        }
//                Tsc.sendpicture(xPos+20, yPos+65, TextAsBitmap.getTextBitmap("No."+m.getUsageCode(), 20));
//                Toast.makeText(aActivity, m.getUsageCode() + " ", Toast.LENGTH_SHORT).show();

//                try{
//                    String[] arrOfStr = m.getItemSetData().split("%");
//                    for(int l=0;l<arrOfStr.length;l++){
//                        Tsc.sendpicture(xPos+205, yPos+95+(l*25), TextAsBitmap.getTextBitmap((l+1) + " : " + arrOfStr[l], 16));
//                    }
//                }catch (Exception e){
//
//                }


                //แผนก
                if (PDept) {
                    Tsc.sendpicture(30, 60, TextAsBitmap.getTextBitmap(""+m.getDepName2()+"", 26));
                }

                //ชื่อโรงพยาบาล
                Tsc.sendpicture(260, 60, TextAsBitmap.getTextBitmap("รพ.วิชัยยุทธ", 26));

                //ชื่อUseage code
                Tsc.sendpicture(30, 120, TextAsBitmap.getTextBitmap("No."+m.getUsageCode()+"", 26));

                //เครื่อง
                if (PMachine || PRound) {
                    Tsc.sendpicture(30, 180, TextAsBitmap.getTextBitmap("ตู้:", 25));
                    Tsc.sendpicture(65, 180, TextAsBitmap.getTextBitmap(m.getMachineName(), 25));
                    Tsc.sendpicture(125, 180, TextAsBitmap.getTextBitmap("/", 25));
                    Tsc.sendpicture(135, 180, TextAsBitmap.getTextBitmap(m.getSterileRoundNumber(), 25));
                }


                //วันแพค
                String mYear = ( Integer.parseInt(m.getSterileDate().substring(6,10)) +543)+"";
                String mfgDate = m.getSterileDate().substring(0,2)+"/"+m.getSterileDate().substring(3,5)+"/"+mYear.substring(2,4) ;

                Tsc.sendpicture(180, 180, TextAsBitmap.getTextBitmap("ผลิต "+mfgDate+ " (" + m.getAgeDay() +" วัน "+ ")", 22));
                //วันหมดอายุ
                String eYear = ( Integer.parseInt(m.getExpireDate().substring(6,10))+543)+"";
                String expDate = "หมดอายุ "+m.getExpireDate().substring(0,2)+"/"+m.getExpireDate().substring(3,5)+"/"+eYear.substring(2,4) ;
                Tsc.sendpicture(175, 210, TextAsBitmap.getTextBitmap(expDate , 28));

                //QR_Code
                Tsc.qrcode(30, 220, "H", "5", "A", "0", "M2", "S1", m.getUsageCode());

                Tsc.sendpicture(170, 250, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+" -เตรียม", 20));
                Tsc.sendpicture(170, 280, TextAsBitmap.getTextBitmap(m.getUsr_approve()+" -ตรวจ", 20));


                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");

//                Tsc.sendcommand("PRINT 1,1\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Tsc.closeport();
        return getID;
    }

    private String StickerCase12(int Sel, ArrayList<String> PRINTER_IP ,List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        String Age1 = "";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        Log.d("BANKRT88",Sel+"");
        Log.d("BANKRT88",Sel-1+"");
        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);
        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));
        Tsc.openport(PrinterIP, 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();
                Tsc.sendpicture(210, 5, TextAsBitmap.getTextBitmap("รพ.วิชัยยุทธ", 28));
                //QR_Code
                Tsc.qrcode(13, 154, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                //แผนก
                if (PDept) {
                    Tsc.sendpicture(20, 50, TextAsBitmap.getTextBitmap(" CSSD ", 25));
                }

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

//                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                Tsc.sendpicture(20, 100, TextAsBitmap.getTextBitmap(Itemname[0].substring(0, Itemname[0].length()>25?25:Itemname[0].length()), 30));
//                        }else{
//                            Tsc.sendpicture(20, 2, TextAsBitmap.getTextBitmap(Itemname[0], 20));
//                            Tsc.sendpicture(20, 22, TextAsBitmap.getTextBitmap(Itemname[1], 20));
//                        }
                Tsc.sendpicture(125, 155, TextAsBitmap.getTextBitmap("No. " + m.getUsageCode(), 22));
                //เครื่อง
                if (PMachine || PRound) {
                    Tsc.sendpicture(125, 185, TextAsBitmap.getTextBitmap("ตู้ " + m.getMachineName()+"/"+ m.getSterileRoundNumber(), 22));
                }
                //วันแพค


                String yy1 = (Integer.parseInt( m.getSterileDate().substring(6,10) )+543)+"";
                String mfc1 = m.getSterileDate().substring(0,2) +"/"+m.getSterileDate().substring(3,5)+"/"+ yy1.substring(2,4);
                Tsc.sendpicture(120, 225, TextAsBitmap.getTextBitmap("ผลิต : " + mfc1 + " ( " + m.getAgeDay() + " วัน " + ")", 22));
                //วันหมดอายุ
                String yy2 = (Integer.parseInt( m.getExpireDate().substring(6,10) )+543)+"";
                String mfc2 = m.getExpireDate().substring(0,2) +"/"+m.getExpireDate().substring(3,5)+"/"+ yy2.substring(2,4);
                Tsc.sendpicture(20, 260, TextAsBitmap.getTextBitmap("หมดอายุ" , 32));
                Tsc.sendpicture(145, 255, TextAsBitmap.getTextBitmap(mfc2 , 40));

                Tsc.sendpicture(20, 320, TextAsBitmap.getTextBitmap(m.getUsr_approve()+"-ตรวจ/"
                        +m.getUsr_prepare()+"-ผู้ผลิต", 22));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Print = true;
        Tsc.closeport();
        return getID;
    }

    private String StickerCase14(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        String Age11_2 = "";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();

        Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);
        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(53, 83, PSpeed, PDensity, 0, 3, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            try {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                int Page = 0;
                int row = 0;
                int itemCnt = 10;
                int last_row_count = 0;
                String[] arrOfStr = null;
                //================
                //    Count Page
                //================
                try {
                    arrOfStr = m.getItemSetData().split("%");
                    Page = (arrOfStr.length / itemCnt);
                    last_row_count = (arrOfStr.length % itemCnt);
                }catch (Exception e){
                    Page = 0;
                    last_row_count = 0;
                }
                if( last_row_count > 0 ) Page++;

                for(int z=1;z<(Page+1);z++) {
                    Tsc.clearbuffer();
                    int row_y = 0;

                    Tsc.sendpicture(30, 18, TextAsBitmap.getTextBitmap(Itemname[0], 30));

                    Tsc.sendpicture(30, 80, TextAsBitmap.getTextBitmap("No."+m.getUsageCode()+"", 26));

                    Tsc.sendpicture(30, 435, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+" -เตรียม /  " +m.getUsr_approve() + " - ตรวจ", 19));
                    //Tsc.sendpicture(225, 435, TextAsBitmap.getTextBitmap(m.getUsr_approve()+" -ตรวจ ", 19));

                    Tsc.sendpicture(30, 465, TextAsBitmap.getTextBitmap("ตู้ : " + m.getMachineName()+"/" + m.getSterileRoundNumber(), 20));

                    //QR_Code
                    Tsc.qrcode(30, 510, "H", "5", "A", "0", "M2", "S1", m.getUsageCode());

                    //วันแพค
                    String yy1 = (Integer.parseInt( m.getSterileDate().substring(6,10) )+543)+"";
                    String mfc1 = m.getSterileDate().substring(0,2) +"/"+m.getSterileDate().substring(3,5)+"/"+ yy1.substring(2,4);
                    Tsc.sendpicture(175, 500, TextAsBitmap.getTextBitmap("ผลิต "+mfc1+" ("+ m.getAgeDay() +"วัน"+")", 24));



                    //วันหมดอายุ
                    String yy2 = (Integer.parseInt( m.getExpireDate().substring(6,10) )+543)+"";
                    String mfc2 = m.getExpireDate().substring(0,2) +"/"+m.getExpireDate().substring(3,5)+"/"+ yy2.substring(2,4);
                    Tsc.sendpicture(175, 542, TextAsBitmap.getTextBitmap( "หมดอายุ ", 28));
                    Tsc.sendpicture(285, 542, TextAsBitmap.getTextBitmap(mfc2 , 28));

                    // Print Item Detail
                    int row_cnt = (z*itemCnt);
                    if(z==Page) row_cnt = ((Page-1)*itemCnt)+last_row_count;

                    for (int l = row; l < row_cnt; l++) {

                        String[] vb1 = arrOfStr[l].split("#");
                        Tsc.sendpicture(40, 125 + (row_y * 30), TextAsBitmap.getTextBitmap(vb1[0].substring(0, vb1[0].length()<100?vb1[0].length():100), 20));
                        String[] pp = vb1[1].split(" ");
                        Tsc.sendpicture(370, 125 + (row_y * 30), TextAsBitmap.getTextBitmap(pp[0], 20));

                        row_y++;
                    }


                    if(pQty > Integer.parseInt( m.getQty() ))
                        Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                    else
                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                    row = (z*itemCnt);
                }

                getID += m.getID() + "@";


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Tsc.closeport();
        return getID;
    }

    private String StickerCasexx(int Sel, ArrayList<String> PRINTER_IP, List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);
        Iterator li = DATA_MODEL.iterator();

        return getID;
    }

    private String StickerCase20_2(int Sel, ArrayList<String> PRINTER_IP,List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        String Age1 = "";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        System.out.println("Sel"+ Sel);
        System.out.println("Printer"+PRINTER_IP.size());

        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);

        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));

        System.out.println("PrinterIP = " + PrinterIP);
        System.out.println("Heigth = " + Heigth);
        System.out.println("Width = " + Width);

        //Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);

        Tsc.openport(PrinterIP, 9100);

        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();
                String Itemname[] = TextTwoLine.make2line(m.getItemname());

//                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                Tsc.sendpicture(30, 7, TextAsBitmap.getTextBitmap(m.getItemname(), 30));

//                        }else{
//                            Tsc.sendpicture(20, 2, TextAsBitmap.getTextBitmap(Itemname[0], 20));
//                            Tsc.sendpicture(20, 22, TextAsBitmap.getTextBitmap(Itemname[1], 20));
//                        }
//                Tsc.sendpicture(xPos+20, yPos+65, TextAsBitmap.getTextBitmap("No."+m.getUsageCode(), 20));
//                Toast.makeText(aActivity, m.getUsageCode() + " ", Toast.LENGTH_SHORT).show();

//                try{
//                    String[] arrOfStr = m.getItemSetData().split("%");
//                    for(int l=0;l<arrOfStr.length;l++){
//                        Tsc.sendpicture(xPos+205, yPos+95+(l*25), TextAsBitmap.getTextBitmap((l+1) + " : " + arrOfStr[l], 16));
//                    }
//                }catch (Exception e){
//
//                }


                //แผนก
                if (PDept) {
                    Tsc.sendpicture(30, 60, TextAsBitmap.getTextBitmap(""+m.getDepName2()+"", 26));
                }

                //ชื่อโรงพยาบาล
                Tsc.sendpicture(260, 60, TextAsBitmap.getTextBitmap("รพ.วิชัยยุทธ", 26));

                //ชื่อUseage code
                Tsc.sendpicture(30, 120, TextAsBitmap.getTextBitmap("No."+m.getUsageCode()+"", 26));

                //เครื่อง
                if (PMachine || PRound) {
                    Tsc.sendpicture(30, 180, TextAsBitmap.getTextBitmap("ตู้:", 25));
                    Tsc.sendpicture(65, 180, TextAsBitmap.getTextBitmap(m.getMachineName(), 25));
                    Tsc.sendpicture(125, 180, TextAsBitmap.getTextBitmap("/", 25));
                    Tsc.sendpicture(135, 180, TextAsBitmap.getTextBitmap(m.getSterileRoundNumber(), 25));
                }


                //วันแพค
                String mYear = ( Integer.parseInt(m.getSterileDate().substring(6,10)) +543)+"";
                String mfgDate = m.getSterileDate().substring(0,2)+"/"+m.getSterileDate().substring(3,5)+"/"+mYear.substring(2,4) ;

                Tsc.sendpicture(180, 180, TextAsBitmap.getTextBitmap("ผลิต "+mfgDate+ " (" + m.getAgeDay() +" วัน "+ ")", 22));
                //วันหมดอายุ
                String eYear = ( Integer.parseInt(m.getExpireDate().substring(6,10))+543)+"";
                String expDate = "หมดอายุ "+m.getExpireDate().substring(0,2)+"/"+m.getExpireDate().substring(3,5)+"/"+eYear.substring(2,4) ;
                Tsc.sendpicture(175, 210, TextAsBitmap.getTextBitmap(expDate , 28));

                //QR_Code
                Tsc.qrcode(30, 220, "H", "5", "A", "0", "M2", "S1", m.getUsageCode());

                Tsc.sendpicture(170, 250, TextAsBitmap.getTextBitmap(m.getUsr_prepare()+" -เตรียม", 20));
                Tsc.sendpicture(170, 280, TextAsBitmap.getTextBitmap(m.getUsr_approve()+" -ตรวจ", 20));


                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");

//                Tsc.sendcommand("PRINT 1,1\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Tsc.closeport();
        return getID;
    }

    private String StickerCase12_VCY_0(int Sel, ArrayList<String> PRINTER_IP ,List<ModelSterileDetail> DATA_MODEL){
        TscWifi Tsc = new TscWifi();
        String getID="";
        String Age1 = "";
        boolean PMachine = p.isPMachine();
        boolean PPrice = p.isPPrice();
        boolean PRound = p.isPRound();
        boolean PDept = p.isPDept();
        boolean PEmp1 = p.isPEmp1();
        boolean PEmp2 = p.isPEmp2();
        boolean PEmp3 = p.isPEmp3();
        int PSpeed = p.getPSpeed();
        int PDensity = p.getPDensity();
        String POption = p.getPOption();
        System.out.println("Sel"+ Sel);
        System.out.println("Printer"+PRINTER_IP.size());

        ArrayList <String> Data = getData(Sel-1,PRINTER_IP);

        String PrinterIP = Data.get(0);
        int Heigth = Integer.parseInt(Data.get(1));
        int Width = Integer.parseInt(Data.get(2));

        System.out.println("PrinterIP = " + PrinterIP);
        System.out.println("Heigth = " + Heigth);
        System.out.println("Width = " + Width);

        //Tsc.openport(getPrinterIP(Sel,PRINTER_IP), 9100);

        Tsc.openport(PrinterIP, 9100);

        Iterator li = DATA_MODEL.iterator();
        while (li.hasNext()) {
            Tsc.setup(Width, Heigth, PSpeed, PDensity, 0, 2, 1);
            Tsc.sendcommand("DIRECTION 1,0\r\n");
            Tsc.clearbuffer();
            try {

                ModelSterileDetail m = (ModelSterileDetail) li.next();

                String Itemname[] = TextTwoLine.make2line(m.getItemname());

                Tsc.sendpicture(15, 7, TextAsBitmap.getTextBitmap(Itemname[0], 30));

                //แผนก
                if (PDept) {
                    Tsc.sendpicture(15, 60, TextAsBitmap.getTextBitmap(m.getDepName2(), 28));
                }

                Tsc.sendpicture(15, 100, TextAsBitmap.getTextBitmap1(m.getUsageCode(), 28));

                if (PMachine || PRound) {
                    Tsc.sendpicture(15, 135, TextAsBitmap.getTextBitmap1("เครื่อง - " + m.getMachineName(), 28));
                    Tsc.sendpicture(15, 165, TextAsBitmap.getTextBitmap1("รอบ - " + m.getSterileRoundNumber(), 28));
                }

                Tsc.sendpicture(155, 210, TextAsBitmap.getTextBitmap1("เตรียม - "+m.getUsr_prepare(), 24));
                Tsc.sendpicture(155, 240, TextAsBitmap.getTextBitmap1("ฆ่าเชื้อ - "+m.getUsr_sterile(), 24));

                //QR_Code
                Tsc.qrcode(18, 215, "H", "5", "A", "0", "M2", "S1", m.getUsageCode());

                //วันแพค
                String eYear1 = (Integer.parseInt(m.getSterileDate().substring(6, 10)) + 543) + "";
                String expDate1 = "ผลิต "+m.getSterileDate().substring(0, 2) + "/" + m.getSterileDate().substring(3, 5) + "/" + eYear1.substring(2, 4);
                Tsc.sendpicture(154,275, TextAsBitmap.getTextBitmap1(expDate1, 24));

                //วันหมดอายุ
                String eYear = (Integer.parseInt(m.getExpireDate().substring(6, 10)) + 543) + "";
                String expDate = "หมดอายุ "+m.getExpireDate().substring(0, 2) + "/" + m.getExpireDate().substring(3, 5) + "/" + eYear.substring(2, 4);
                Tsc.sendpicture(154,310, TextAsBitmap.getTextBitmap(expDate, 29));

                if(pQty > Integer.parseInt( m.getQty() ))
                    Tsc.sendcommand("PRINT 1," + pQty + "\r\n");
                else
                    Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                getID += m.getID() + "@";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Tsc.closeport();
        return getID;
    }
    // =============================================================================================
    // -- Add Sterile Detail
    // =============================================================================================
    public void Get_label_sticker(String xID) {
        class Get_label_sticker extends AsyncTask<String, Void, String> {
            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try{
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        xItemname1=c.getInt("xItemname1");
                        yItemname1=c.getInt("yItemname1");
                        xUsagecode1=c.getInt("xUsagecode1");
                        yUasgecode1=c.getInt("yUasgecode1");
                        xPrice=c.getInt("xPrice");
                        yPrice=c.getInt("yPrice");
                        xDept1=c.getInt("xDept1");
                        yDept1=c.getInt("yDept1");
                        xMachine=c.getInt("xMachine");
                        yMahcine=c.getInt("yMahcine");
                        xRound=c.getInt("xRound");
                        yRound=c.getInt("yRound");
                        xPackdate=c.getInt("xPackdate");
                        yPackdate=c.getInt("yPackdate");
                        xExp=c.getInt("xExp");
                        yExp=c.getInt("yExp");
                        xEmp1=c.getInt("xEmp1");
                        yEmp1=c.getInt("yEmp1");
                        xEmp2=c.getInt("xEmp2");
                        yEmp2=c.getInt("yEmp2");
                        xItemname2=c.getInt("xItemname2");
                        yItemname2=c.getInt("yItemname2");
                        xDept2=c.getInt("xDept2");
                        yDept2=c.getInt("yDept2=");
                        xUsagecode2=c.getInt("xUsagecode2");
                        yUsagecode2=c.getInt("yUsagecode2");
                        xQrcode1=c.getInt("xQrcode1");
                        yQrcode1=c.getInt("yQrcode1");
                        xQrcode2=c.getInt("xQrcode2");
                        yQrcode2=c.getInt("yQrcode2");
                        xPackdate2=c.getInt("xPackdate2");
                        yPackdate2=c.getInt("yPackdate2");
                        xExp2=c.getInt("xExp2");
                        yExp2=c.getInt("yExp2");
                        xEmp3=c.getInt("xEmp3");
                        yEmp3=c.getInt("yEmp3");
                        Log.d("ED_DEPTNAME",c.getString("ED_DepName") );
                    }
                    //etxt_dept.setText(ED_DeptName);
                    //getlistdata(deptsp_id,edittext.getText().toString(),"");
                }catch (JSONException e){
                    Log.d("XXX", "Error on get dept" );
                }
            }
            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("xID",params[0]);
                String result = ruc.sendPostRequest(getUrl.xUrl+"p/sticker/get_label_stickerXY.php",data);
                return result;
            }
        }
        Get_label_sticker ru = new Get_label_sticker();
        ru.execute(xID);
    }

    private String getPrinterIP(int Label,ArrayList<String> PRINTER_IP){
        if(PRINTER_IP == null || PRINTER_IP.size() == 0) {
            return null;
        }else {
            return PRINTER_IP.get(Label - 1);
        }
    }

    private ArrayList <String> getData (int Index,ArrayList<String> PRINTER_IP){
        if(PRINTER_IP == null || PRINTER_IP.size() == 0) {
            return null;
        }else {
            ArrayList <String> Data = new ArrayList<>();
            Data.add(PRINTER_IP.get(Index * 3));
            Data.add(PRINTER_IP.get((Index * 3)+1));
            Data.add(PRINTER_IP.get((Index * 3)+2));
            return Data;
        }
    }
}
