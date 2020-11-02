
package com.phc.cssd.print_sticker;

import android.content.Context;
import android.util.Log;

import com.phc.cssd.model.ModelSterileDetail;
import com.phc.cssd.model.ModelWashDetailForPrint;

import java.util.List;


public class PrintWash {

    public PrintWash() {

    }

    public String print(Context context, int LabelType, final String IP, List<ModelWashDetailForPrint> Data) {
        String RETURN_DATA="";
        Log.d("V:K:DD",IP+"");
        Log.d("V:K:DD",LabelType+"");
        Log.d("V:K:DD",Data.size()+"");
        switch(LabelType) {
            case 2: Sticker_Indicator_DoubleLayer_Cerulean_50x70 s2 = new Sticker_Indicator_DoubleLayer_Cerulean_50x70(context, IP, Data);
                RETURN_DATA = s2.print();
                break;
            case 3: Sticker_Non_Indicator_DoubleLayer_Cerulean_50x70 s3 = new Sticker_Non_Indicator_DoubleLayer_Cerulean_50x70(context, IP, Data);
                RETURN_DATA = s3.print();
                break;
        }
        return RETURN_DATA;
    }
}





















