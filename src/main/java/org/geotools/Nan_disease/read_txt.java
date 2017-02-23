package org.geotools.Nan_disease;

import java.io.*;

/**
 * Created by jiao.xue on 2017/02/23.
 */
public class read_txt {

    public static void main(String arg[]){
        try{
            File file = new File("/Users/jiao.xue/Dropbox/難病/特定疾患/010/data/010_2003新規（様式2）.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            File list = new File("/Users/jiao.xue/Dropbox/難病/特定疾患/010/data/list.csv");
            PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(list, false), "SHIFT_JIS"));


            String str = br.readLine();
            str = br.readLine();
            str = br.readLine();
            str = br.readLine();//第5行から

            while((str=br.readLine())!= null){
                String[] pair = str.split(",");
                //String Id_now=pair[52];
                output.write("\n");
                if(pair.length>=79) {
                    output.write(pair[0]+",");
                    for(int i=52;i<79;i++) {
                        output.write(pair[i]+",");
                    }
                    System.out.println(pair.length);
                }
                else if(pair.length>=52){
                    output.write(pair[0]+",");
                    for(int i=52;i<pair.length;i++) {
                        output.write(pair[i]+",");
                    }
                }
                else{
                    output.write(pair[0]+",");
                }
            }

            br.close();
        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }
    }
}
