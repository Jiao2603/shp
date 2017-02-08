package org.geotools.Shelter;

import java.io.*;

/**
 * Created by jiao.xue on 2017/02/08.
 */
public class Main_test {
    public static void main(String args[]) throws Exception {

        File shelter = new File("/Users/jiao.xue/Dropbox/shelter/shelter_area.csv");//location_judge2で作ったshelterのリスト　小学校区内の避難所
        File mesh5 = new File("/Users/jiao.xue/Dropbox/shelter/5mesh_area.csv");//location_judge_5meshで作った5meshのリスト　小学校区内の　5thmesh

        File Id_list = new File("/Users/jiao.xue/Dropbox/shelter/Id_list.csv");//小学校区のリスト
// 　

        File outfile = new File("/Users/jiao/Desktop/shortestpath_test/output.csv");//結果を記録します
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outfile, false), "SHIFT_JIS"));


        BufferedReader Id = new BufferedReader(new InputStreamReader(new FileInputStream(Id_list), "SHIFT_JIS"));
        String value;
        String line;
        line = Id.readLine();//第一行を飛ばす
        while ((line = Id.readLine()) != null) {
            String pair[] = line.split(",");
            value=pair[0];
             File destfilepath= new File("/Users/jiao/Desktop/split_map/test.shp");//分割された地図

            File shelter_temp = new File("/Users/jiao.xue/Dropbox/shelter/shelter_area_temp.csv");
            File mesh5_temp = new File("/Users/jiao.xue/Dropbox/shelter/5mesh_area_temp.csv");
            File out_temp = new File("/Users/jiao.xue/Dropbox/shelter/out_temp1.csv");//全距離を計算する
            File out_temp2 = new File("/Users/jiao.xue/Dropbox/shelter/out_temp1.csv");//一番近い距離を取る
            new find_nearest(destfilepath, mesh5_temp,shelter_temp,out_temp);//避難所の近くの３つの病院を探す
            find_nearest.find_top(out_temp,out_temp2);


        }

    }
    }
