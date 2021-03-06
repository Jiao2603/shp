package org.geotools.Shelter;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by jiao.xue on 2017/02/22.
 * 入力：shelterのリスト　小学校区内の避難所
 * 5meshのリスト　小学校区内の　5thmesh
 * 分割された小学校区の地図　小学校区__fig.shp(例：EA2714304_fig
 *
 *
 *
 * 出力ファイル
 * 全小学校区内の避難所と５次メッシュの距離
 */
public class Main_test {


    public static void main(String args[]) throws Exception {
        File roadDir= new File("/Users/jiao.xue/Desktop/shelter_neccesary_file/split_map");//
        File Dir=new File("/Users/jiao.xue/Desktop/shelter_neccesary_file/shelter");
        output(roadDir,Dir);

    }


    //roadDir 道路に関する情報全て
    //Dir避難所情報など、道路以外の情報
    public static void  output(File roadDir,File Dir) throws Exception {


        // File full_shape=new File("/Users/jiao.xue/Desktop/split_map/全道路リンク.shp");
        File full_shape = new File(roadDir.getPath()+"/全道路リンク.shp");//全道路リンク


        //File shelter = new File("/Users/jiao.xue/Dropbox/shelter/shelter_area.csv");//location_judge2で作ったshelterのリスト　小学校区内の避難所
        File shelter = new File(Dir.getPath()+"/shelter_area.csv");//location_judge2で作ったshelterのリスト　小学校区内の避難所

        //File mesh5 = new File("/Users/jiao.xue/Dropbox/shelter/5mesh_area_test.csv");//location_judge_5meshで作った5meshのリスト　小学校区内の　5thmesh
        File mesh5 = new File(Dir.getPath()+"/5mesh_area.csv");//location_judge_5meshで作った5meshのリスト　小学校区内の　5thmesh

         //小学校区のリストを作る
        //File Id_list = new File("/Users/jiao.xue/Dropbox/shelter/Id_list.csv");
        File Id_list = new File(Dir.getPath()+"/Id_list_left.csv");
/*
        PrintWriter output_list = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Id_list, false), "SHIFT_JIS"));
        output_list.write("Id");
        BufferedReader shelter_list = new BufferedReader(new InputStreamReader(new FileInputStream(shelter), "SHIFT_JIS"));
        String line0 = shelter_list.readLine();//第一行を飛ばす
        line0 = shelter_list.readLine();//第２行から
        String pair0[] = line0.split(",");
        String Id_now=pair0[0];
        output_list.write("\n"+pair0[0]);
        while ((line0 = shelter_list.readLine()) != null) {
            String pairx[] = line0.split(",");
            if(!pairx[0].equals(Id_now)){
              output_list.write("\n"+pairx[0]);
              Id_now=pairx[0];
          }
        }
        shelter_list.close();
        output_list.close();
*/

// 　

        File outfile = new File(Dir.getPath()+"/output.csv");//結果を記録します
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outfile, false), "SHIFT_JIS"));
        out.write("5thmesh"+","+"ecode"+","+"distance");
        out.close();

        BufferedReader Id = new BufferedReader(new InputStreamReader(new FileInputStream(Id_list), "SHIFT_JIS"));
        String value;
        String line;
        line = Id.readLine();//第一行を飛ばす
        while ((line = Id.readLine()) != null) {//各小学校区を計算する
            String pair[] = line.split(",");
            value=pair[0];
            System.out.println(value);

            File shelter_temp = new File(Dir.getPath()+"/shelter_area_temp.csv");
            refile.select(shelter, shelter_temp, value);//小学校区内の避難所を抽出する　　shelter_tempは空の可能性もある

            File mesh5_temp = new File(Dir.getPath()+"/5mesh_area_temp.csv");
            refile.select(mesh5, mesh5_temp, value);//小学校区内の5thmeshを抽出する

            /*//地図を分割する
            File destfilepath = null;//分割されたファイルを記録
            int i=0;//中間生成したShpのファイル番号を記録する

            BufferedReader mesh5_name = new BufferedReader(new InputStreamReader(new FileInputStream(mesh5_temp), "SHIFT_JIS"));//小学校区の２ndメッシュを探す
            String line1 = mesh5_name.readLine();//第一行を飛ば
            ArrayList<String> mesh2nd_list=new ArrayList<>();
            while((line1 = mesh5_name.readLine())!= null){
                String pair1[] = line1.split(",");
                String mesh2nd_value=pair1[1];
                if(!mesh2nd_list.contains(mesh2nd_value.substring(0,6))) {
                   // System.out.println(mesh2nd_value.substring(0,6) );
                    mesh2nd_list.add(mesh2nd_value.substring(0,6));
                }
            }
            if (mesh2nd_list.size()==1){
                String mesh2nd_value=mesh2nd_list.get(0);
               destfilepath=new File(roadDir.getPath()+"/out/" + mesh2nd_value + "_fig.shp");//分割された地図
                //System.out.println(mesh2nd_value );
            }
            else {
                destfilepath = new File(roadDir.getPath()+"/test/"+ ++i +"test.shp");//分割されたファイルを記録

                split_map.slipt_Shape(full_shape,destfilepath,mesh2nd_list);
            }
            mesh5_name.close();
*/

                        //分割された地図を利用する
            File destfilepath = new File(roadDir.getPath()+"/out_school_area/" + value + "_fig.shp");//分割された地図



            File out_temp = new File(Dir.getPath()+"/out_temp1.csv");//全距離を計算する
            File out_temp2 = new File(Dir.getPath()+"/out_temp2.csv");//一番近い距離を取る
            new find_nearest(destfilepath, mesh5_temp,shelter_temp,out_temp);//避難所の近くの病院を探す
            find_nearest.find_top(out_temp,out_temp2);
            refile.add(out_temp2,outfile);//outファイルを書き出す


        }

    }
    }
