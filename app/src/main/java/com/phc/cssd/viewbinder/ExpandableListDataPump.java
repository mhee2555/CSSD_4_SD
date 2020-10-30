package com.phc.cssd.viewbinder;

import com.phc.cssd.properties.Response_Aux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static HashMap<String, List<String>> getData(ArrayList<Response_Aux> getData,String xDate,int mode) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> data1 = new ArrayList<String>();
        List<String> data2 = new ArrayList<String>();
        List<String> data3 = new ArrayList<String>();

        List<String> data4 = new ArrayList<String>();
        List<String> data5 = new ArrayList<String>();
        List<String> data6 = new ArrayList<String>();
        for(int i=0;i<getData.size();i++) {
            //จ่ายต้องจัด
            if( getData.get(i).getFields7().equals("0") || getData.get(i).getFields7().equals("1") ) {
                if(getData.get(i).getFields14().equals("0")) {
                    data1.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7() + "," + getData.get(i).getFields8() + "," + getData.get(i).getFields10() + "," + getData.get(i).getFields11() + "," + getData.get(i).getFields12() + "," + getData.get(i).getFields2() + "," + getData.get(i).getFields13());
                }else{
                    data4.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7() + "," + getData.get(i).getFields8() + "," + getData.get(i).getFields10() + "," + getData.get(i).getFields11() + "," + getData.get(i).getFields12() + "," + getData.get(i).getFields2() + "," + getData.get(i).getFields13());
                }
            }else if( getData.get(i).getFields7().equals("6") || getData.get(i).getFields7().equals("9") ) {
                if(getData.get(i).getFields14().equals("0")) {
                    data2.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7() + "," + getData.get(i).getFields8() + "," + getData.get(i).getFields10() + "," + getData.get(i).getFields11() + "," + getData.get(i).getFields12() + "," + getData.get(i).getFields2() + "," + getData.get(i).getFields13());
                }else{
                    data6.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7() + "," + getData.get(i).getFields8() + "," + getData.get(i).getFields10() + "," + getData.get(i).getFields11() + "," + getData.get(i).getFields12() + "," + getData.get(i).getFields2() + "," + getData.get(i).getFields13());
                }
//                data2.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7()+ "," +getData.get(i).getFields8() + "," +getData.get(i).getFields10()+ "," +getData.get(i).getFields11()+ "," +getData.get(i).getFields12()+ "," +getData.get(i).getFields2()+"," +getData.get(i).getFields13());
            }else if( getData.get(i).getFields7().equals("2") || getData.get(i).getFields7().equals("3") ) {
                if(getData.get(i).getFields2().substring(0,10).equals( xDate ) ) {  //formatter.format(date)
                    if(getData.get(i).getFields14().equals("0")) {
                        data3.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7() + "," + getData.get(i).getFields8() + "," + getData.get(i).getFields10() + "," + getData.get(i).getFields11() + "," + getData.get(i).getFields12() + "," + getData.get(i).getFields2() + "," + getData.get(i).getFields13());
                    }else{
                        data5.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7() + "," + getData.get(i).getFields8() + "," + getData.get(i).getFields10() + "," + getData.get(i).getFields11() + "," + getData.get(i).getFields12() + "," + getData.get(i).getFields2() + "," + getData.get(i).getFields13());
                    }
                }
            }
        }

//        List<String> data2 = new ArrayList<String>();
//        for(int i=0;i<getData.size();i++) {
//            //จ่ายของแล้ว
//            if( getData.get(i).getFields7().equals("6") || getData.get(i).getFields7().equals("9") ) {
//                data2.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7()+ "," +getData.get(i).getFields8() + "," +getData.get(i).getFields10()+ "," +getData.get(i).getFields11()+ "," +getData.get(i).getFields12()+ "," +getData.get(i).getFields2()+"," +getData.get(i).getFields13());
//            }
//        }

//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

//        List<String> data3 = new ArrayList<String>();
//        for(int i=0;i<getData.size();i++) {
//            //จ่ายค้างจัด
//            if( getData.get(i).getFields7().equals("2") || getData.get(i).getFields7().equals("3") ) {
//                if(getData.get(i).getFields2().substring(0,10).equals( xDate ) ) {  //formatter.format(date)
//                    data3.add(getData.get(i).getFields1() + "," + getData.get(i).getFields7() + "," + getData.get(i).getFields8() + "," + getData.get(i).getFields10() + "," + getData.get(i).getFields11() + "," + getData.get(i).getFields12() + "," + getData.get(i).getFields2() + "," + getData.get(i).getFields13());
//                }
//            }
//        }

        if(mode==1){
            expandableListDetail.put("1 เอกสารยืม [ " + data4.size() + " ]", data4);
            expandableListDetail.put("2 เอกสารยืมค้างจัด [ " + data6.size() + " ]", data6);
            expandableListDetail.put("3 เอกสารยืมจัดแล้ว [ " + data5.size() + " ]", data5);
        }else{
            expandableListDetail.put("1 เอกสารที่ต้องจัด [ " + data1.size() + " ]       ", data1);
            expandableListDetail.put("2 เอกสารที่ค้างจัด [ " + data2.size() + " ]", data2);
            expandableListDetail.put("3 เอกสารจัดแล้ว [ " + data3.size() + " ]", data3);
        }

        return expandableListDetail;
    }

}