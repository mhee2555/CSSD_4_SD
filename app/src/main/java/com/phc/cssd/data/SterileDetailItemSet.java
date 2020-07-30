package com.phc.cssd.data;

import com.phc.cssd.model.ModelSterileDetail;

import java.util.ArrayList;
import java.util.List;

public class SterileDetailItemSet {

    public static String data[] = {
            "8821","S1809-0163","2948","1","Douch Can (1) ","I00162","I00162-0019","7","7168","26-09-2018","03-10-2018","1","0","CSSD","CSSD","1","OR -","OR -","OR -","4","SA","12","TIME: 00:00","1","0","1","1",
            "Uterine Sound@2 ชิ้น#adair@1 ชิ้น#กรรไกรตัดเนื้อโค้ง@1 ชิ้น#กรรไกรตัดไหมโค้งแหลม@1 ชิ้น#A dupter Tube@1 ชิ้น#ข้อต่อ Volum@8 ชิ้น#กระปุก Forcep (10)@1 ชิ้น#กระปุก Forceps เล็ก+ อับสำลี@2 ชิ้น#กล่องใส่เข็ม	@4 ชิ้น#Adjustable tube	@1 ชิ้น",
            "8820","S1809-0163","2949","1","Douch Can (1) ","I00162","I00162-0020","7","7171","26-09-2018","03-10-2018","1","0","CSSD","CSSD","1","OR -","OR -","OR -","4","SA","12","TIME: 00:00","1","0","1","1",
            "Uterine Sound@2 ชิ้น#adair@1 ชิ้น#กรรไกรตัดเนื้อโค้ง@1 ชิ้น#กรรไกรตัดไหมโค้งแหลม@1 ชิ้น#A dupter Tube@1 ชิ้น#ข้อต่อ Volum@8 ชิ้น#กระปุก Forcep (10)@1 ชิ้น#กระปุก Forceps เล็ก+ อับสำลี@2 ชิ้น#กล่องใส่เข็ม	@4 ชิ้น#Adjustable tube	@1 ชิ้น"
    };

    public static List<ModelSterileDetail> getModelSterileDetail() {

        List<ModelSterileDetail> list = new ArrayList<>();

        try {
            int index = 0;

            for(int i=0;i<data.length;i+=28){

                list.add(
                        getSterileDetail(
                                data[i],
                                data[i + 1],
                                data[i + 2],
                                data[i + 3],
                                data[i + 4],
                                data[i + 5],
                                data[i + 6],
                                data[i + 7],
                                data[i + 8],
                                data[i + 9],
                                data[i + 10],
                                data[i + 11],
                                data[i + 12],
                                data[i + 13],
                                data[i + 14],
                                data[i + 15],
                                data[i + 16],
                                data[i + 17],
                                data[i + 18],
                                data[i + 19],
                                data[i + 20],
                                data[i + 21],
                                data[i + 22],
                                data[i + 23],
                                data[i + 24],
                                data[i + 25],
                                data[i + 26],
                                data[i + 27],
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

    static ModelSterileDetail getSterileDetail(
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
}
