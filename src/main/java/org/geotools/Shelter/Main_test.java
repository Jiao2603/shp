package org.geotools.Shelter;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jiao.xue on 2017/02/08.
 */
public class Main_test {
    public static void main(String args[]) throws Exception {

        File full_shape=new File("/Users/jiao.xue/Desktop/split_map/全道路リンク.shp");

        File shelter = new File("/Users/jiao.xue/Dropbox/shelter/shelter_area.csv");//location_judge2で作ったshelterのリスト　小学校区内の避難所
        File mesh5 = new File("/Users/jiao.xue/Dropbox/shelter/5mesh_area.csv");//location_judge_5meshで作った5meshのリスト　小学校区内の　5thmesh


        File Id_list = new File("/Users/jiao.xue/Dropbox/shelter/Id_list.csv");//小学校区のリスト
// 　

        File outfile = new File("/Users/jiao.xue/Dropbox/shelter/output.csv");//結果を記録します
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outfile, false), "SHIFT_JIS"));


        BufferedReader Id = new BufferedReader(new InputStreamReader(new FileInputStream(Id_list), "SHIFT_JIS"));
        String value;
        String line;
        line = Id.readLine();//第一行を飛ばす
        while ((line = Id.readLine()) != null) {
            String pair[] = line.split(",");
            value=pair[0];

            File shelter_temp = new File("/Users/jiao.xue/Dropbox/shelter/shelter_area_temp.csv");
            refile.select(shelter, shelter_temp, value);//小学校区内の避難所を抽出する

            File mesh5_temp = new File("/Users/jiao.xue/Dropbox/shelter/5mesh_area_temp.csv");
            refile.select(mesh5, mesh5_temp, value);//小学校区内の5thmeshを抽出する

            File destfilepath = new File("/Users/jiao.xue/Desktop/split_map/test.shp");//分割されたファイルを記録

            BufferedReader mesh5_name = new BufferedReader(new InputStreamReader(new FileInputStream(mesh5_temp), "SHIFT_JIS"));//小学校区の２ndメッシュを探す
            String line1 = mesh5_name.readLine();//第一行を飛ば
            ArrayList<String> mesh2nd_list=new ArrayList<>();
            while((line1 = mesh5_name.readLine())!= null){
                String pair1[] = line1.split(",");
                String mesh2nd_value=pair1[2];
                if(!mesh2nd_list.contains(mesh2nd_value.substring(0,6))) {
                    mesh2nd_list.add(mesh2nd_value.substring(0,6));
                }
            }
            if (mesh2nd_list.size()==1){
                String mesh2nd_value=mesh2nd_list.get(0);
               destfilepath=new File("/Users/jiao.xue/Desktop/split_map/out/" + mesh2nd_value + "_fig.shp");//分割された地図
            }
            else
                split_map.slipt_Shape(full_shape,destfilepath,mesh2nd_list);



            File out_temp = new File("/Users/jiao.xue/Dropbox/shelter/out_temp1.csv");//全距離を計算する
            File out_temp2 = new File("/Users/jiao.xue/Dropbox/shelter/out_temp2.csv");//一番近い距離を取る
            new find_nearest(destfilepath, mesh5_temp,shelter_temp,out_temp);//避難所の近くの病院を探す
            find_nearest.find_top(out_temp,out_temp2);
            refile.add(out_temp2,outfile);//outファイルを書き出す


        }

    }
    }
