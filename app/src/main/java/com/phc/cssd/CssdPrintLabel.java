package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;

import com.example.tscdll.TscWifiActivity;
import com.phc.core.connect.HTTPConnect;
import com.phc.core.data.AsonData;
import com.phc.core.string.FormatDate;
import com.phc.core.string.TextAsBitmap;
import com.phc.core.string.TextRotation;
import com.phc.core.string.TextSplitName;
import com.phc.core.string.TextTwoLine;
import com.phc.cssd.model.ModelSterileDetail;
import com.phc.cssd.url.Url;
import com.phc.cssd.url.getUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CssdPrintLabel extends Activity {



    String DocNo = null;
    final int DELAY_TIME = 0;
    private List<ModelSterileDetail> MODEL_STERILE_DETAIL = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cssd_print_label);

        byIntent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void byIntent(){
        // Argument
        Intent intent = getIntent();
        DocNo = intent.getStringExtra("DocNo");

        loadSterileDetail(DocNo);
    }

    // =============================================================================================
    // -- Display Sterile Detail
    // =============================================================================================

    public void loadSterileDetail(final String p_docno) {
        if(p_docno == null || p_docno.equals("-") || p_docno.equals("")){
            return ;
        }

        class DisplaySterileDetail extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    try {
                        MODEL_STERILE_DETAIL = getModelSterileDetail();

                        declarePrinter();

                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_docno", p_docno);

                String result = null;

                try{
                    HTTPConnect httpConnect = new HTTPConnect();
                    result = httpConnect.sendPostRequest(Url.URL + "cssd_display_sterile_detail.php", data);
                }catch(Exception e){
                    e.printStackTrace();

                }

                System.out.println("URL = " + result);

                return result;
            }

            private List<ModelSterileDetail> getModelSterileDetail() {

                List<ModelSterileDetail> list = new ArrayList<>();

                try {
                    int index = 0;

                    for(int i=0;i<data.size();i+=size){

                        list.add(
                                getSterileDetail(
                                        data.get(i),
                                        data.get(i + 1),
                                        data.get(i + 2),
                                        data.get(i + 3),
                                        data.get(i + 4),
                                        data.get(i + 5),
                                        data.get(i + 6),
                                        data.get(i + 7),
                                        data.get(i + 8),
                                        data.get(i + 9),
                                        data.get(i + 10),
                                        data.get(i + 11),
                                        data.get(i + 12),
                                        data.get(i + 13),
                                        data.get(i + 14),
                                        data.get(i + 15),
                                        data.get(i + 16),
                                        data.get(i + 17),
                                        data.get(i + 18),
                                        data.get(i + 19),
                                        data.get(i + 20),
                                        data.get(i + 21),
                                        data.get(i + 22),
                                        data.get(i + 23),
                                        data.get(i + 24),
                                        data.get(i + 25),
                                        data.get(i + 26),
                                        data.get(i + 27),
                                        "",
                                        "",
                                        index
                                )
                        );

                        index++;
                    }

                }catch(Exception e){
                    e.printStackTrace();
                }

                return list;
            }

            // =========================================================================================
        }



        DisplaySterileDetail obj = new DisplaySterileDetail();
        obj.execute();
    }

    // =============================================================================================
    // -- Get Printer
    // =============================================================================================
    ArrayList<Integer> PRINTER = new ArrayList<>();
    ArrayList<String> PRINTER_IP = new ArrayList<>();

    public void declarePrinter() {

        class DeclarePrinter extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    try{
                        for(int i=0;i<data.size();i+=size) {
                            PRINTER.add( Integer.valueOf(data.get(i)).intValue() );
                            PRINTER_IP.add(data.get(i+1));
                        }

                        onPrint();

                    }catch(Exception e){
                        e.printStackTrace();
                        return;
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                HTTPConnect httpConnect = new HTTPConnect();
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_select_label.php", data);

                return result;
            }

            // =========================================================================================
        }

        DeclarePrinter obj = new DeclarePrinter();
        obj.execute();
    }

    private String getPrinterIP(int Label){
        if(PRINTER_IP == null || PRINTER_IP.size() == 0) {
            return null;
        }else {
            return PRINTER_IP.get(Label - 1);
        }
    }

    private List<ModelSterileDetail> getItemLabel(String Label){
        try {
            List<ModelSterileDetail> list = new ArrayList<>();
            List<ModelSterileDetail> DATA_MODEL = MODEL_STERILE_DETAIL;

            Iterator li = DATA_MODEL.iterator();

            String LABEL_ID = null;

            while(li.hasNext()) {
                ModelSterileDetail m = (ModelSterileDetail) li.next();
                LABEL_ID = m.getLabelID();

                if (LABEL_ID.equals(Label) && m.getPrintCount() == 0) {
                    list.add(
                            getSterileDetail(
                                    m.getID(),
                                    m.getDocNo(),
                                    m.getItemStockID(),
                                    m.getQty(),
                                    m.getItemname(),

                                    m.getItemcode(),
                                    m.getUsageCode(),
                                    m.getAge(),
                                    m.getImportID(),
                                    m.getSterileDate(),

                                    m.getExpireDate(),
                                    m.getIsStatus(),
                                    m.getOccuranceQty(),
                                    m.getDepName(),
                                    m.getDepName2(),

                                    m.getLabelID(),
                                    m.getUsr_sterile(),
                                    m.getUsr_prepare(),
                                    m.getUsr_approve(),
                                    m.getSterileRoundNumber(),

                                    m.getMachineName(),
                                    m.getPrice(),
                                    m.getTime(),
                                    m.getSterileProcessID(),
                                    m.getQty_Print(),

                                    m.getPrinter(),
                                    m.getUsageCount(),
                                    m.getItemSetData(),
                                    m.getBasketName(),
                                    m.getIsRemarkExpress(),
                                    m.getIndex()
                            )
                    );
                }
            }

            return list;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private ModelSterileDetail getSterileDetail(
            String ID,
            String DocNo,
            String ItemStockID,
            String Qty,
            String itemname,

            String itemcode,
            String UsageCode,
            String age,
            String ImportID,
            String SterileDate,

            String ExpireDate,
            String IsStatus,
            String OccuranceQty,
            String DepName,
            String DepName2,

            String LabelID,
            String usr_sterile,
            String usr_prepare,
            String usr_approve,
            String SterileRoundNumber,

            String MachineName,
            String Price,
            String Time,
            String SterileProcessID,
            String PrintCount,

            String Printer,
            String UsageCount,
            String ItemSetData,
            String BasketName,
            String IsRemarkExpress,
            int index
    ){
        return new ModelSterileDetail(
                ID,
                DocNo,
                ItemStockID,
                Qty,
                itemname,

                itemcode,
                UsageCode,
                age,
                ImportID,
                SterileDate,

                ExpireDate,
                IsStatus,
                OccuranceQty,
                DepName,
                DepName2,

                LabelID,
                usr_sterile,
                usr_prepare,
                usr_approve,
                SterileRoundNumber,

                MachineName,
                Price,
                Time,
                SterileProcessID,
                PrintCount,

                Printer,
                UsageCount,
                ItemSetData,
                BasketName,
                IsRemarkExpress,
                index
        );
    }

    private void onPrint() throws Exception {
        final List<ModelSterileDetail> Label_1 = getItemLabel("1");
        final List<ModelSterileDetail> Label_2 = getItemLabel("2");
        final List<ModelSterileDetail> Label_3 = getItemLabel("3");
        final List<ModelSterileDetail> Label_4 = getItemLabel("4");
        final List<ModelSterileDetail> Label_5 = getItemLabel("5");
        final List<ModelSterileDetail> Label_6 = getItemLabel("6");
        final List<ModelSterileDetail> Label_7 = getItemLabel("7");

        //=========================================================================================

        if(Label_1 != null && Label_1.size() > 0) {
            printLabel( 1, getPrinterIP(1), Label_1);
            /*
            final Handler handler0 = new Handler();
            handler0.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0);
            */
        }

        if(Label_2 != null && Label_2.size() > 0) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    printLabel(2, getPrinterIP(2), Label_2);

                }
            }, DELAY_TIME);
        }

        if(Label_3 != null && Label_3.size() > 0) {
            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    printLabel( 3, getPrinterIP(3), Label_3);
                }
            }, DELAY_TIME);
        }


        if(Label_4 != null && Label_4.size() > 0) {
            final Handler handler4 = new Handler();
            handler4.postDelayed(new Runnable() {
                @Override
                public void run() {
                    printLabel( 4, getPrinterIP(4), Label_4);
                }

            }, DELAY_TIME);
        }

        if(Label_5 != null && Label_5.size() > 0) {
            final Handler handler5 = new Handler();
            handler5.postDelayed(new Runnable() {
                @Override
                public void run() {
                    printLabel( 5, getPrinterIP(5), Label_5);
                }

            }, DELAY_TIME);
        }

        if(Label_6 != null && Label_6.size() > 0) {
            final Handler handler6 = new Handler();
            handler6.postDelayed(new Runnable() {
                @Override
                public void run() {
                    printLabel(6, getPrinterIP(6), Label_6);
                }

            }, DELAY_TIME);
        }

        if(Label_7 != null && Label_7.size() > 0) {
            final Handler handler7 = new Handler();
            handler7.postDelayed(new Runnable() {
                @Override
                public void run() {
                    printLabel(7, getPrinterIP(7), Label_7);
                }

            }, DELAY_TIME);
        }

        finish();
    }

    void printLabel(int label, String ip, List<ModelSterileDetail> sterile) {

        //==================================================================================================================================

        //System.out.println("label = " + label);

        /*
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
        */

        boolean PMachine = true;
        boolean PPrice = true;
        boolean PRound = true;
        boolean PDept = true;
        boolean PEmp1 = true;
        boolean PEmp2 = true;
        boolean PEmp3 = true;
        int PSpeed = 7;
        int PDensity = 7;
        String POption = "1";

        String itemname = "";
        String Price = "";
        String DepName2 = "";
        String itemcode = "";
        String UsageCode = "";
        String MachineName = "";
        String SterileRoundNumber = "";
        String SterileDate = "";
        String ExpireDate = "";
        String usr_prepare = "";
        String usr_approve = "";
        String usr_sterile = "";
        String qty = "0";
        String age = "0";

        String DATA="";

        TscWifiActivity Tsc = new TscWifiActivity();

        //==================================================================================================================================

        switch(label) {
            //==================================================================================================================================
            // Sticker Big Hot Air Steam
            //==================================================================================================================================

            case 1:
                List<ModelSterileDetail> DATA_MODEL_4 = sterile;

                Iterator li4 = DATA_MODEL_4.iterator();

                System.out.println("IP : " + ip);


                Tsc.openport(ip, 9100);

                while (li4.hasNext()) {
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li4.next();

                        Tsc.setup(50, 45, PSpeed, PDensity, 0, 1, 1);
                        Tsc.sendcommand("DIRECTION 1,0\r\n");
                        Tsc.clearbuffer();

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(30, 15, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendbitmap(30, 12, TextAsBitmap.getTextBitmap(Itemname[0], 22));
                            Tsc.sendbitmap(30, 36, TextAsBitmap.getTextBitmap(Itemname[1], 22));
                        }
                        Tsc.sendbitmap(130, 65, TextAsBitmap.getTextBitmap(m.getDepName2()+" เครื่อง:"+m.getMachineName()+" รอบ:"+m.getSterileRoundNumber(), 14));
                        Tsc.sendbitmap(130, 85, TextAsBitmap.getTextBitmap("No."+m.getUsageCode(), 20));
                        Tsc.sendbitmap(130, 113, TextAsBitmap.getTextBitmap("Packing Date: "+m.getSterileDate(), 16));
                        Tsc.qrcode(10, 75, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                        Tsc.sendbitmap(210, 138, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 35));
                        //คนเครียม ตรวจ
                        Tsc.sendbitmap(25, 185, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "/" + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ตรวจ"  : "-ตรวจ") + "/" + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-ฆ่าเชื้อ"  : "-ฆ่าเชื้อ"), 16));

                        //ส่วนล่างสติ๊กเกอร์
                        //ชื่ออุปกรณ์
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(30, 210, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendbitmap(30, 210, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(30, 230, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }

                        //แผนก
                        Tsc.sendbitmap(190, 272, TextAsBitmap.getTextBitmap(m.getDepName2(), 18));
                        //Tsc.sendbitmap(50,465,TextAsBitmap.getTextBitmap(m.getDepName2(),18));
                        //ชื่อเซ็ท
                        Tsc.sendbitmap(20, 272, TextAsBitmap.getTextBitmap("No."+m.getUsageCode(), 18));
                        //QR_Code
                        Tsc.qrcode(295, 252, "H", "4", "A", "0", "M2", "S1", m.getUsageCode());
                        //วันหมดอายุ

                        Tsc.printerfont(40, 297, "0", 0, 9, 9, "Pakcing Date:"+m.getSterileDate());
                        //วันหมดอายุ
                        Tsc.printerfont(120, 315, "0", 0, 12, 12, m.getExpireDate());
                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                        //Tsc.closeport();

                        DATA += m.getID() + "@";

                        //System.out.println("DATA = " + DATA);



                    } catch (Exception e) {
                        e.printStackTrace();
                        //continue;
                    }

                }

                Tsc.closeport();

                break;


            //==================================================================================================================================
            // Sticker Small Hot Air Steam
            //==================================================================================================================================
            case 2:
                List<ModelSterileDetail> DATA_MODEL_5 = sterile;

                Iterator li5 = DATA_MODEL_5.iterator();


                while (li5.hasNext()) {

                    try {

                        Tsc.openport(ip, 9100);
                        Tsc.setup(60, 57, PSpeed, PDensity, 0, 2, 2);
                        Tsc.sendcommand("DIRECTION 0,0\r\n");

                        ModelSterileDetail m = (ModelSterileDetail) li5.next();
                        Tsc.clearbuffer();
                        //ส่วนบนสติ๊กเกอร์
                        //ชื่ออุปกรณ์

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(50, 20, TextAsBitmap.getTextBitmap(Itemname[0], 22));
                        }else{
                            Tsc.sendbitmap(50, 15, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(50, 42, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //ราคา
                        if (PPrice) {
                            Tsc.printerfont(300, 100, "1", 0, 1, 1, "Price: " + m.getPrice());
                        }
                        //แผนก
                        if (PDept) {
                            Tsc.sendbitmap(50, 85, TextAsBitmap.getTextBitmap(m.getDepName2(), 22));
                        }
                        //ชื่อเซ็ท
                        Tsc.sendbitmap(100, 120, TextAsBitmap.getTextBitmap(POption.equals("1") ? m.getItemcode() : m.getUsageCode(), 20));

                        //QR_Code
                        Tsc.qrcode(30, 160, "H", "6", "A", "0", "M1", "S1", m.getUsageCode());
                        //เครื่อง
                        if (PMachine) {
                            Tsc.sendbitmap(210, 155, TextAsBitmap.getTextBitmap(m.getMachineName(), 18));
                        }
                        //รอบ
                        if (PRound) {
                            Tsc.sendbitmap(310, 155, TextAsBitmap.getTextBitmap(m.getSterileRoundNumber(), 18));
                        }
                        //วันแพค
                        Tsc.printerfont(210, 210, "0", 0, 9, 9, m.getSterileDate() + m.getTime());
                        //วันหมดอายุ
                        Tsc.sendbitmap(210, 245, TextAsBitmap.getTextBitmap(m.getExpireDate() , 26));
                        //Tsc.printerfont(210,250,"0",0,12,12, m.getExpireDate());
                        //คนเครียม ตรวจ
                        if (PEmp1 || PEmp2) {
                            Tsc.sendbitmap(220, 280, TextAsBitmap.getTextBitmap(m.getUsr_prepare() + "/" + m.getUsr_approve(), 18));
                        }
                        //ฆ่าเชื้อ
                        if (PEmp3) {
                            Tsc.sendbitmap(220, 308, TextAsBitmap.getTextBitmap(m.getUsr_sterile(), 18));
                        }

                        //Tsc.sendcommand("PRINT 1\r\n");
                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                        Tsc.closeport();

                        DATA += m.getID() + "@";


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



                break;

           /* //==================================================================================================================================
            // Sticker Big Gas Steam
            //==================================================================================================================================
            case 3:
                List<ModelSterileDetail> DATA_MODEL_6 = sterile;

                Iterator li6 = DATA_MODEL_6.iterator();

                while (li6.hasNext()) {
                    Tsc.openport(ip, 9100);
                    Tsc.setup(55, 56, PSpeed, PDensity, 0, 2, 1);
                    Tsc.sendcommand("DIRECTION 1,0\r\n");
                    Tsc.clearbuffer();

                    ModelSterileDetail m = (ModelSterileDetail) li6.next();

                    itemname = m.getItemname();
                    Price = m.getPrice();
                    DepName2 = m.getDepName2();
                    itemcode = m.getItemcode();
                    UsageCode = m.getUsageCode();
                    MachineName = m.getMachineName();
                    SterileRoundNumber = m.getSterileRoundNumber();
                    SterileDate = m.getSterileDate();
                    ExpireDate = m.getExpireDate();
                    usr_prepare = m.getUsr_prepare();
                    usr_approve = m.getUsr_approve();
                    usr_sterile = m.getUsr_sterile();
                    qty = m.getQty();
                    age = m.getExpDay();


                    try {

                        String Itemname[] = TextTwoLine.make2line(itemname);
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(40, 20, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendbitmap(40, 15, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(40, 42, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        if (PPrice) {
                            Tsc.printerfont(300, 85, "1", 0, 1, 1, "Price:" + Price + ".-");
                        }

                        if (PDept) {
                            Tsc.sendbitmap(30, 65, TextAsBitmap.getTextBitmap(DepName2, 20));
                        }
                        Tsc.sendbitmap(80, 95, TextAsBitmap.getTextBitmap(POption.equals("1") ? itemcode : UsageCode, 20));
                        if (PMachine) {
                            Tsc.sendbitmap(180, 120, TextAsBitmap.getTextBitmap("เครื่อง: " + MachineName, 20));
                        }
                        if (PRound) {
                            Tsc.sendbitmap(320, 120, TextAsBitmap.getTextBitmap("รอบ: " + SterileRoundNumber, 20));
                        }

                        Tsc.qrcode(30, 125, "H", "5", "A", "0", "M2", "S1", UsageCode);
                        Tsc.printerfont(200, 165, "0", 0, 9, 9, SterileDate);
                        Tsc.sendbitmap(200, 215, TextAsBitmap.getTextBitmap(m.getExpireDate() + " (" + m.getAgeDay() + ")", 26));

                        if (PEmp1 || PEmp2) {
                            Tsc.sendbitmap(180, 245, TextAsBitmap.getTextBitmap(usr_prepare + "/" + usr_approve + "/" + usr_sterile, 15));
                        }

                        //ส่วนล่างสติ๊กเกอร์
                        //ชื่ออุปกรณ์
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(30, 285, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendbitmap(30, 285, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(30, 305, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //แผนก
                        if (PDept) {
                            Tsc.sendbitmap(210, 350, TextAsBitmap.getTextBitmap(DepName2, 18));
                        }

                        //ชื่อเซต
                        Tsc.sendbitmap(65, 350, TextAsBitmap.getTextBitmap(UsageCode, 18));
                        //QR_Code
                        Tsc.qrcode(310, 330, "H", "4", "A", "0", "M2", "S1", UsageCode);
                        //วันหมดอายุ
                        Tsc.printerfont(25, 405, "0", 0, 9, 9, SterileDate);
                        //วันหมดอายุ
                        Tsc.printerfont(150, 400, "0", 0, 12, 12, ExpireDate);


                        Tsc.sendcommand("PRINT 1," + qty + "\r\n");
                        Tsc.closeport();

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



                break;*/
            //==================================================================================================================================
            // Sticker Indicator Blue 5x2.5cm
            //==================================================================================================================================
            case 3:
                List<ModelSterileDetail> DATA_MODEL_1 = sterile;
                Iterator li1 = DATA_MODEL_1.iterator();
                Tsc.openport(ip, 9100);
                while (li1.hasNext()) {
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li1.next();

                        Tsc.setup(51, 26, PSpeed, PDensity, 0, 1, 1);
                        Tsc.sendcommand("DIRECTION 1,0\r\n");
                        Tsc.clearbuffer();

                        /*String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(50, 20, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendpicture(50, 15, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendpicture(50, 42, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //Tsc.sendpicture(60, 30, TextAsBitmap.getTextBitmap(m.getItemname(), 30));
                        Tsc.sendpicture(20, 70, TextAsBitmap.getTextBitmap(m.getDepName2(), 25));
                        Tsc.sendpicture(25, 100, TextAsBitmap.getTextBitmap(m.getUsageCode(), 25));
                        Tsc.sendpicture(25, 130, TextAsBitmap.getTextBitmap(m.getSterileDate(), 25));
                        Tsc.qrcode(400, 100, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                        Tsc.sendpicture(110, 110, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 40));
                        //คนเครียม ตรวจ
                        //Tsc.sendpicture(25, 170, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "," + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ห่อ"  : "-ห่อ") + "," + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-นึ่ง"  : "-นึ่ง"), 16));
*/

/*
                        //ฟ้าเล็กแบบเก่า
                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(20, 20, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                        }else{
                            Tsc.sendpicture(20, 20, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendpicture(20, 45, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //TscLAN.sendpicture(60, 30, TextAsBitmap.getTextBitmap(m.getItemname(), 30));
                        Tsc.sendpicture(130, 70, TextAsBitmap.getTextBitmap(m.getDepName2()+"   เครื่อง:"+m.getMachineName()+"  รอบ:"+m.getSterileRoundNumber(), 14));
                        Tsc.sendpicture(130, 90, TextAsBitmap.getTextBitmap("No."+m.getUsageCode(), 20));
                        Tsc.sendpicture(130, 118, TextAsBitmap.getTextBitmap("Packing Date: "+FormatDate.dateString(m.getSterileDate()), 16));
                        Tsc.qrcode(20, 75, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                        Tsc.sendpicture(210, 148, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 35));
                        //คนเครียม ตรวจ
                        Tsc.sendpicture(25, 185, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "/" + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ตรวจ"  : "-ตรวจ") + "/" + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-ฆ่าเชื้อ"  : "-ฆ่าเชื้อ"), 16));

                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                        DATA += m.getID() + "@";

                        System.out.println("DATA = " + DATA);

                        System.gc();
                        System.runFinalization();
                        System.gc();*/


                        String Itemname[] = TextTwoLine.make2line(m.getItemname());

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(70, 25, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                        }else{
                            Tsc.sendbitmap(70, 25, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(70, 50, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //TscLAN.sendpicture(60, 30, TextAsBitmap.getTextBitmap(m.getItemname(), 30));
                        Tsc.sendbitmap(20, 70, TextAsBitmap.getTextBitmap(m.getDepName2(), 14));
                        Tsc.sendbitmap(25, 72, TextAsBitmap.getTextBitmap( "เครื่อง:" + (PMachine ? m.getMachineName() : "") + "  รอบ:" + (PRound ? m.getSterileRoundNumber() : ""), 16));
                        //Tsc.sendpicture(25, 72, TextAsBitmap.getTextBitmap("No."+m.getUsageCode(), 20));
                        Tsc.sendbitmap(25, 115, TextAsBitmap.getTextBitmap("Packing Date: "+FormatDate.dateString(m.getSterileDate())+"("+m.getAge()+"วัน)", 16));
                        Tsc.qrcode(290, 62, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                        Tsc.sendbitmap(110, 125, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 35));
                        //คนเครียม ตรวจ
                        Tsc.sendbitmap(25, 170, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "/" + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ตรวจ"  : "-ตรวจ") + "/" + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-ฆ่าเชื้อ"  : "-ฆ่าเชื้อ"), 16));

                        Tsc.sendcommand("PRINT 1," + qty + "\r\n");


                        DATA += m.getID() + "@";
                       /* String Itemname[] = TextTwoLine.make2line(itemname);
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(40, 20, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendpicture(40, 15, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendpicture(40, 42, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        if (PPrice) {
                            Tsc.printerfont(300, 85, "1", 0, 1, 1, "Price:" + Price + ".-");
                        }

                        if (PDept) {
                            Tsc.sendpicture(30, 65, TextAsBitmap.getTextBitmap(DepName2, 20));
                        }
                        Tsc.sendpicture(80, 95, TextAsBitmap.getTextBitmap(POption.equals("1") ? itemcode : UsageCode, 20));
                        if (PMachine) {
                            Tsc.sendpicture(180, 120, TextAsBitmap.getTextBitmap("เครื่อง: " + MachineName, 20));
                        }
                        if (PRound) {
                            Tsc.sendpicture(320, 120, TextAsBitmap.getTextBitmap("รอบ: " + SterileRoundNumber, 20));
                        }

                        Tsc.qrcode(30, 125, "H", "5", "A", "0", "M2", "S1", UsageCode);
                        Tsc.printerfont(200, 165, "0", 0, 9, 9, SterileDate);
                        Tsc.sendpicture(200, 215, TextAsBitmap.getTextBitmap(m.getExpireDate() + " (" + m.getAgeDay() + ")", 26));

                        if (PEmp1 || PEmp2) {
                            Tsc.sendpicture(30, 252, TextAsBitmap.getTextBitmap(usr_prepare +"เตรียม"+ "/" + usr_approve +"ตรวจ"+ "/" + usr_sterile+"ฆ่าเชื้อ", 15));
                        }

                        //ส่วนล่างสติ๊กเกอร์
                        //ชื่ออุปกรณ์
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendpicture(30, 285, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendpicture(30, 285, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendpicture(30, 305, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        //แผนก
                        if (PDept) {
                            Tsc.sendpicture(210, 350, TextAsBitmap.getTextBitmap(DepName2, 18));
                        }

                        //ชื่อเซต
                        Tsc.sendpicture(65, 350, TextAsBitmap.getTextBitmap(UsageCode, 18));
                        //QR_Code
                        Tsc.qrcode(310, 330, "H", "4", "A", "0", "M2", "S1", UsageCode);
                        //วันหมดอายุ
                        Tsc.printerfont(25, 405, "0", 0, 9, 9, SterileDate);
                        //วันหมดอายุ
                        Tsc.printerfont(150, 400, "0", 0, 12, 12, ExpireDate);


                        Tsc.sendcommand("PRINT 1," + qty + "\r\n");*/

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Tsc.closeport();

                break;

            //==================================================================================================================================
            // Sticker Small Gas Steam
            //==================================================================================================================================
            case 4:
                List<ModelSterileDetail> DATA_MODEL_7 = sterile;

                Iterator li7 = DATA_MODEL_7.iterator();



                while (li7.hasNext()) {
                    Tsc.clearbuffer();

                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li7.next();

                        //Tsc.openport(mUsbManager, getPrinter(4));
                        Tsc.openport(ip, 9100);
                        Tsc.setup(55, 56, PSpeed, PDensity, 0, 2, 1);
                        Tsc.sendcommand("DIRECTION 1,0\r\n");
                        Tsc.clearbuffer();

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());
                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(50, 5, TextAsBitmap.getTextBitmap(Itemname[0], 26));
                        }else{
                            Tsc.sendbitmap(50, 5, TextAsBitmap.getTextBitmap(Itemname[0], 20));
                            Tsc.sendbitmap(50, 25, TextAsBitmap.getTextBitmap(Itemname[1], 20));
                        }
                        Tsc.sendbitmap(60, 60, TextAsBitmap.getTextBitmap(m.getDepName2(), 26));
                        Tsc.sendbitmap(120, 105, TextAsBitmap.getTextBitmap(POption.equals("1") ? m.getItemcode() : m.getUsageCode(), 20));
                        Tsc.sendbitmap(180, 130, TextAsBitmap.getTextBitmap(m.getMachineName(), 20));
                        Tsc.sendbitmap(280, 130, TextAsBitmap.getTextBitmap(m.getSterileRoundNumber(), 20));
                        Tsc.sendbitmap(350, 130, TextAsBitmap.getTextBitmap(m.getTime(), 20));
                        Tsc.qrcode(60, 150, "H", "4", "A", "0", "M1", "S1", m.getUsageCode());
                        Tsc.printerfont(170, 190, "0", 0, 9, 9, m.getSterileDate());
                        Tsc.printerfont(310, 190, "0", 0, 9, 9, m.getExpireDate());
                        Tsc.sendbitmap(180, 220, TextAsBitmap.getTextBitmap(m.getUsr_prepare() + "/" + m.getUsr_approve() + "/" + m.getUsr_sterile(), 15));

                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");
                        Tsc.closeport();

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



                break;

            //==================================================================================================================================
            // Sticker Yellow (Pasin)
            //==================================================================================================================================
            case 5:

                //Tsc.openport(mUsbManager, getPrinter(5));
                Tsc.openport(ip, 9100);
                Tsc.setup(50, 49, PSpeed, PDensity, 0, 1, 1);
                Tsc.sendcommand("DIRECTION 1,0\r\n");

                System.out.println(" -----------------> 5");

                List<ModelSterileDetail> DATA_MODEL = sterile;

                Iterator li = DATA_MODEL.iterator();


                while (li.hasNext()) {
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li.next();

                        String Itemname[] = TextTwoLine.make2line(m.getItemname());
                        Tsc.clearbuffer();
                        Tsc.printerfont(30,20,"0",0,9,9, m.getDepName2());

                        //แผนก
                        if (PDept) {
                            Tsc.sendbitmap(30, 20, TextAsBitmap.getTextBitmap(m.getDepName2(), 26));
                        }

                        if( (Itemname[0].length() > 1) && (Itemname[1].length() == 0) ){
                            Tsc.sendbitmap(30, 70, TextAsBitmap.getTextBitmap(Itemname[0], 36));
                        }else{
                            Tsc.sendbitmap(30, 70, TextAsBitmap.getTextBitmap(Itemname[0], 30));
                            Tsc.sendbitmap(30, 95, TextAsBitmap.getTextBitmap(Itemname[1], 30));
                        }
                        //เครื่อง
                        if (PMachine) {
                            Tsc.sendbitmap(30, 160, TextAsBitmap.getTextBitmap("เครื่อง:" + m.getMachineName(), 24));
                        }

                        //รอบ
                        if (PRound) {
                            Tsc.sendbitmap(190, 160, TextAsBitmap.getTextBitmap("รอบ:" + m.getSterileRoundNumber(), 24));
                        }

                        //วันแพค
                        Tsc.sendbitmap(30, 190, TextAsBitmap.getTextBitmap("ผลิต "+m.getSterileDate() + m.getExpDay(), 26));

                        //วันหมดอายุ
                        Tsc.sendbitmap(30, 230, TextAsBitmap.getTextBitmap("หมด", 30));
                        Tsc.sendbitmap(30, 260, TextAsBitmap.getTextBitmap("อายุ", 30));
                        Tsc.sendbitmap(120, 240, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 45));

                        //คนเครียม ตรวจ
                        if (PEmp1||PEmp2){Tsc.sendbitmap(30,310,TextAsBitmap.getTextBitmap(TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม",22));}
                        //ฆ่าเชื้อ
                        if (PEmp3){Tsc.sendbitmap(130,340,TextAsBitmap.getTextBitmap( TextSplitName.SplitName(m.getUsr_approve())+"-ห่อ"+"/"+TextSplitName.SplitName(m.getUsr_sterile())+"-นึ่ง",22));}



                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                Tsc.closeport();

                break;

            //==================================================================================================================================
            // Sticker Red (Pasin)
            //==================================================================================================================================
            case 6:

                System.out.println(" -----------------> 6");

                List<ModelSterileDetail> DATA_MODEL_2 = sterile;

                Iterator li2 = DATA_MODEL_2.iterator();

                //Tsc.openport(mUsbManager, getPrinter(6));
                Tsc.openport(ip, 9100);
                int x = 15;
                int y = 60;
                final int inc = 40;
                final int x_ = 300;
                final int s = 26;

                while (li2.hasNext()) {
                    try {
                        //======================================================================
                        // Model
                        //======================================================================
                        ModelSterileDetail m = (ModelSterileDetail) li2.next();

                        itemname = m.getItemname();
                        Price = m.getPrice();
                        DepName2 = m.getDepName2();
                        itemcode = m.getItemcode();
                        UsageCode = m.getUsageCode();
                        MachineName = m.getMachineName();
                        SterileRoundNumber = m.getSterileRoundNumber();
                        SterileDate = m.getSterileDate();
                        ExpireDate = m.getExpireDate();
                        usr_prepare = m.getUsr_prepare();
                        usr_approve = m.getUsr_approve();
                        usr_sterile = m.getUsr_sterile();
                        qty = m.getQty();
                        age = m.getExpDay();

                        String[] parts = m.getItemSetData().split("%");

                        //======================================================================
                        // Sticker
                        //======================================================================
                        for (int i = 0; i < parts.length; i = i + 10) {
                            // Setup
                            Tsc.setup(47, 83, PSpeed, PDensity, 0, 1, 1);
                            Tsc.sendcommand("DIRECTION 0,0\r\n");
                            Tsc.clearbuffer();

                            // Header
                            Tsc.sendbitmap(20, 10, TextAsBitmap.getTextBitmap(itemname, 30));
                            Tsc.sendbitmap(285, 10, TextAsBitmap.getTextBitmap("(" + ((i/10)+1) + "/" +  ( ( parts.length / 11) + 1 ) + ")", s) );
                            Tsc.printerfont(x, 45, "0", 0, 10, 10, "------------------------------------------------");

                            // Detail (Set)
                            try {
                                String[] set = parts[i].split("#");
                                Tsc.sendbitmap(x, y, TextAsBitmap.getTextBitmap((i + 1) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y, TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 1].split("#");
                                Tsc.sendbitmap(x, y + (inc * 1), TextAsBitmap.getTextBitmap((i + 2) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 1), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 2].split("#");
                                Tsc.sendbitmap(x, y + (inc * 2), TextAsBitmap.getTextBitmap((i + 3) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 2), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 3].split("#");
                                Tsc.sendbitmap(x, y + (inc * 3), TextAsBitmap.getTextBitmap((i + 4) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 3), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 4].split("#");
                                Tsc.sendbitmap(x, y + (inc * 4), TextAsBitmap.getTextBitmap((i + 5) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 4), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 5].split("#");
                                Tsc.sendbitmap(x, y + (inc * 5), TextAsBitmap.getTextBitmap((i + 6) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 5), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 6].split("#");
                                Tsc.sendbitmap(x, y + (inc * 6), TextAsBitmap.getTextBitmap((i + 7) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 6), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 7].split("#");
                                Tsc.sendbitmap(x, y + (inc * 7), TextAsBitmap.getTextBitmap((i + 8) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 7), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 8].split("#");
                                Tsc.sendbitmap(x, y + (inc * 8), TextAsBitmap.getTextBitmap((i + 9) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 8), TextAsBitmap.getTextBitmap(set[1], s));

                                set = parts[i + 9].split("#");
                                Tsc.sendbitmap(x, y + (inc * 9), TextAsBitmap.getTextBitmap((i + 10) + ". " + set[0], s));
                                Tsc.sendbitmap(x_, y + (inc * 9), TextAsBitmap.getTextBitmap(set[1], s));
                            }catch(Exception e){

                            }

                            // Footer
                            Tsc.sendbitmap(x, 465, TextAsBitmap.getTextBitmap("หมดอายุ", 30));
                            Tsc.sendbitmap(150, 465, TextAsBitmap.getTextBitmap(FormatDate.dateString(ExpireDate), 45));

                            Tsc.sendbitmap(x, 515, TextAsBitmap.getTextBitmap(SterileDate + "  (ค-" + (PMachine ? MachineName : "") + "/ร-" + (PRound ? SterileRoundNumber : "") + ")", s));
                            Tsc.sendbitmap(x, 565, TextAsBitmap.getTextBitmap((PEmp1 ? usr_prepare : "") + "," + (PEmp2 ?usr_approve  : "") + "," + (PEmp3 ? usr_sterile : ""), 20));

                            Tsc.sendbitmap(250, 600, TextAsBitmap.getTextBitmap(age, s));

                            // Print
                            Tsc.sendcommand("PRINT 1," + qty + "\r\n");
                        }

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                Tsc.closeport();
                break;

            //==================================================================================================================================
            // Sticker Blue (Pasin)
            //==================================================================================================================================
            case 7:

                System.out.println(" -----------------> 7");

                //Tsc.openport(mUsbManager, getPrinter(7));
                Tsc.openport(ip, 9100);
                Tsc.setup(50, 25, PSpeed, PDensity, 0, 2, 1);
                Tsc.sendcommand("DIRECTION 1,0\r\n");

                List<ModelSterileDetail> DATA_MODEL_3 = sterile;

                Iterator li3 = DATA_MODEL_3.iterator();

                //System.out.println(" Printer -----> " + getPrinter(7).getDeviceName() );

                while (li3.hasNext()) {
                    try {
                        ModelSterileDetail m = (ModelSterileDetail) li3.next();
                        Tsc.clearbuffer();

                        Tsc.sendbitmap(60, 40, TextAsBitmap.getTextBitmap(m.getDepName2()+" " + m.getItemname(), 30));
                        Tsc.sendbitmap(20, 70, TextAsBitmap.getTextBitmap("ผลิต "+m.getSterileDate() + "(" + (PMachine ? m.getMachineName() : "") +"/"+ (PRound ? m.getSterileRoundNumber() : "") + ")", 25));
                        Tsc.sendbitmap(25, 100, TextAsBitmap.getTextBitmap("หมด", 25));
                        Tsc.sendbitmap(25, 130, TextAsBitmap.getTextBitmap("อายุ", 25));
                        Tsc.sendbitmap(350, 90, TextRotation.RotateBitmap(TextAsBitmap.getTextBitmap(m.getExpDay(), 25), 90));
                        Tsc.sendbitmap(110, 110, TextAsBitmap.getTextBitmap(FormatDate.dateString(m.getExpireDate()), 40));
                        //คนเครียม ตรวจ
                        Tsc.sendbitmap(25, 170, TextAsBitmap.getTextBitmap((PEmp1 ? TextSplitName.SplitName(m.getUsr_prepare())+"-เตรียม"  : "-เตรียม") + "," + (PEmp2 ? TextSplitName.SplitName(m.getUsr_approve())+"-ห่อ"  : "-ห่อ") + "," + (PEmp3 ? TextSplitName.SplitName(m.getUsr_sterile())+"-นึ่ง"  : "-นึ่ง"), 20));

                        Tsc.sendcommand("PRINT 1," + m.getQty() + "\r\n");

                        DATA += m.getID() + "@";

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                Tsc.closeport();

                break;

        }

        //updatePrintStatus(DATA);

    }

    // =============================================================================================
    // -- Add Sterile Detail
    // =============================================================================================

    public void updatePrintStatus(final String p_data) {

        class UpdatePrintStatus extends AsyncTask<String, Void, String> {

            //------------------------------------------------
            // Background Worker Process Variable
            private boolean Success = false;
            private ArrayList<String> data = null;
            private int size = 0;
            //------------------------------------------------

            // variable
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                AsonData ason = new AsonData(result);

                Success = ason.isSuccess();
                size = ason.getSize();
                data = ason.getASONData();

                if(Success && data != null) {
                    //Runtime.getRuntime().gc();
                    //System.gc();
                    //finish();

                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();

                data.put("p_data", p_data);

                HTTPConnect httpConnect = new HTTPConnect();
                String result = httpConnect.sendPostRequest(Url.URL + "cssd_update_sterile_detail_print_status.php", data);

                System.out.println("URL = " + result);

                return result;
            }

            // =========================================================================================
        }

        UpdatePrintStatus obj = new UpdatePrintStatus();
        obj.execute();
    }


    // =============================================================================================

    boolean PMachine=false;
    boolean PPrice=false;
    boolean PRound=false;
    boolean PDept=false;
    boolean PEmp1=false;
    boolean PEmp2=false;
    boolean PEmp3=false;
    boolean PBarCode = false;
    String PSpeed = "6";
    String PDensity = "13";
    String POption = "2";



    public void PreviewSticker() {


        class getPreviewSticker extends AsyncTask<String, Void, String> {



            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                getUrl iFt = new getUrl();
                HTTPConnect ruc = new HTTPConnect();
                String TAG_RESULTS="result";
                JSONArray setRs = null;

                try {
                    JSONObject jsonObj = new JSONObject(s);
                    setRs = jsonObj.getJSONArray(TAG_RESULTS);

                    for (int i = 0; i < setRs.length(); i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        if (c.getString("bool").equals("true")) {
                            PMachine = c.getString("PMachine").equals("1");
                            PPrice = c.getString("PPrice").equals("1");
                            PRound = c.getString("PRound").equals("1");
                            PDept = c.getString("PDept").equals("1");
                            PEmp1 = c.getString("PEmp1").equals("1");
                            PEmp2 = c.getString("PEmp2").equals("1");
                            PEmp3 = c.getString("PEmp3").equals("1");
                            PSpeed = c.getString("PSpeed");
                            PDensity = c.getString("PDensity");
                            POption = c.getString("POption");

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("pass", params[0]);
                HTTPConnect httpConnect = new HTTPConnect();
                String result = httpConnect.sendPostRequest(Url.URL + "PreviewSticker/getpreviewsticker.php" ,data);

                //System.out.println(Url.URL + "PreviewSticker/getpreviewsticker.php");
                return  result;
            }
        }

        getPreviewSticker ru = new getPreviewSticker();
        ru.execute();
    }

}



