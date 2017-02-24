package org.geotools.Shelter;

import org.geotools.App;
import org.geotools.data.FeatureWriter;
import org.geotools.data.FileDataStoreFactorySpi;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jiao.xue on 2017/02/10.
 */
public class split_school_area {

    public static void main( String[] args ) throws IOException {

        File roadDir= new File("/Users/jiao.xue/Desktop/shelter_neccesary_file/split_map");//
        File Dir=new File("/Users/jiao.xue/Desktop/shelter_neccesary_file/shelter");

            trans_Shape(roadDir,Dir);//分割


        }


        public static void trans_Shape(File roadDir, File Dir) throws IOException {
            String t;
            File full_shape = new File(roadDir.getPath()+"/全道路リンク.shp");//全道路リンク
            File mesh5 = new File(Dir.getPath()+"/5mesh_area.csv");//location_judge_5meshで作った5meshのリスト　小学校区内の　5thmesh

            File Id_list = new File(Dir.getPath()+"/Id_list_map.csv");

            BufferedReader Id = new BufferedReader(new InputStreamReader(new FileInputStream(Id_list), "SHIFT_JIS"));
            String area_name;
            String line;
            line = Id.readLine();//第一行を飛ばす
            while ((line = Id.readLine()) != null) {//各小学校区を計算する
                String pair[] = line.split(",");
                area_name = pair[0];

                //地図を分割する

                File mesh5_temp = new File(Dir.getPath()+"/5mesh_area_temp.csv");
                refile.select(mesh5, mesh5_temp, area_name);//


                File destfilepath= new File(roadDir.getPath()+"/out_school_area/" + area_name+ "_fig.shp");//全道路の2次メッシュコードによる分割
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
                    File filepath=new File(roadDir.getPath()+"/out/" + mesh2nd_value + "_fig.shp");//分割された地図
                    split_map.slipt_Shape(filepath,destfilepath,mesh2nd_list);

                    //System.out.println(mesh2nd_value );
                }
                else {

                    split_map.slipt_Shape(full_shape,destfilepath,mesh2nd_list);
                }
                //split_map.slipt_Shape(full_shape,destfilepath,mesh2nd_list);

                mesh5_name.close();



            }


            //String mesh="ファイル名";
            // String value="523644";
            // File srcfilepath= new File("/Users/jiao/Desktop/shortestpath_test/5mesh_latitude_longtitude.csv");//全道路リンク
            // File destfilepath = new File("/Users/jiao/Desktop/shortestpath_test/1.csv");
           /* try {
                //元shapeファイル
                ShapefileDataStore shapeDS = (ShapefileDataStore) new ShapefileDataStoreFactory().createDataStore(srcfilepath.toURI().toURL());
                shapeDS.setCharset(Charset.forName("SHIFT-JIS"));//文字コード

                //出力shapeファイル作り
                Map<String, Serializable> params = new HashMap<String, Serializable>();
                FileDataStoreFactorySpi factory = new ShapefileDataStoreFactory();
                params.put(ShapefileDataStoreFactory.URLP.key, destfilepath.toURI().toURL());
                ShapefileDataStore ds = (ShapefileDataStore) factory.createNewDataStore(params);
                // 属性設定
                SimpleFeatureSource fs = shapeDS.getFeatureSource(shapeDS.getTypeNames()[0]);

                ds.createSchema(SimpleFeatureTypeBuilder.retype(fs.getSchema(), DefaultGeographicCRS.WGS84));

                //writer初期化

                FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(ds.getTypeNames()[0], Transaction.AUTO_COMMIT);

                //書き出す
                SimpleFeatureIterator it = fs.getFeatures().features();
                try {
                    while (it.hasNext()) {
                        SimpleFeature f = it.next();
                        Iterator<Property> its = f.getProperties().iterator();
                        while (its.hasNext()) {
                            Property pro = its.next();
                            t = String.valueOf(pro.getValue());
                            if (t.equals(value)) {
                                //System.out.println(pro.getValue());
                                SimpleFeature fNew = writer.next();
                                fNew.setAttributes(f.getAttributes());
                                writer.write();
                            }
                        }
                    }
                } finally {
                    it.close();
                }
                writer.close();
                ds.dispose();
                shapeDS.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
}
