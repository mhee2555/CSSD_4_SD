package com.phc.core.string;

import android.util.Log;

public class TextTwoLine {

    public static String[] make2line(String str){

        String[] line = new String[2];

        try {

            line[0] = "";
            line[1] = "";
            str = str.substring(0, str.length() - 4);
            if ((str.length() >= 0) && (str.length() < 31)) {
                line[0] = str.substring(0, str.length());
            } else {
                line[0] = str.substring(0, 29);
                line[1] = str.substring(29, str.length());
            }

            line[0] = (line[0] == null) ? "" : line[0];
            line[1] = (line[1] == null) ? "" : line[1];


            return line;
        }catch (Exception e){
            line[0] = str;
            line[1] = "";

            return line;
        }
    }

    public static String[] make2line1(String str){
        String[] line = new String[2];

        try {
            line[0]="";
            line[1]="";

            str = str.substring(0, str.length()-0);

            if( (str.length()>0) && (str.length()<13) ) {
                line[0] = str.substring(0, str.length());
            }else{
                line[0] = str.substring(0, 12);
                line[1] = str.substring(12,str.length());
            }

            line[0] = (line[0] == null) ? "" : line[0];
            line[1] = (line[1] == null) ? "" : line[1];

            return line;
        }catch (Exception e){
            line[0] = str;
            line[1] = "";

            return line;
        }
    }

    public static String[] make2line2(String str){
        String[] line = new String[2];

        try {
            line[0]="";
            line[1]="";

            str = str.substring(25, str.length()-4);

            if( (str.length() >= 0) && (str.length() < 27) ) {
                line[0] = str.substring(0, str.length());
            }else{
                line[0] = str.substring(26,str.length());
                line[1] = str.substring(26,str.length());
            }

            line[0] = (line[0] == null) ? "" : line[0];
            line[1] = (line[1] == null) ? "" : line[1];

            return line;
        }catch (Exception e){
            line[0] = str;
            line[1] = "";

            return line;
        }
    }

    public static String[] make2lineFooter(String str){
        String[] line = new String[2];
        line[0]="";
        line[1]="";
        str = str.substring(0, str.length()-4);
        if(str.length()>22){
            char[]arr=str.toCharArray();
            for(int i=18;i<22;i++) {
                if (Character.isWhitespace(arr[i])) {
                    line[0] =str.substring(0,i);
                    line[1] = str.substring(i, str.length());
                }else{
                    //ตัดstringหลังตัวที่20ไปอีกบรรทัดใหม่
                    line[0] =str.substring(0,30);
                    line[1] = str.substring(30, str.length());
                }
            }
        }else{
            line[0]=str;
        }

        return line;
    }
}
