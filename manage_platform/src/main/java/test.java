/**
 * Created by xn056839 on 2017/3/3.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class test {
    private static final Logger logger = LoggerFactory.getLogger(test.class);

    public static void main(String[] args) {
//        //JSONArray
//        String jsonArrayData="[{\"a1\":\"12\",\"b1\":\"112\",\"c1\":\"132\",\"d1\":\"134\"},{\"a2\":\"12\",\"b2\":\"112\",\"c2\":\"132\",\"d2\":\"134\"},{\"a3\":\"12\",\"b3\":\"112\",\"c3\":\"132\",\"d3\":\"134\"}]";
//        JSONArray jsonArray = JSONArray.fromObject(jsonArrayData);
//
//        List<Map<String,Object>> mapListJson = (List)jsonArray;
//        for (int i = 0; i < mapListJson.size(); i++) {
//            Map<String,Object> obj=mapListJson.get(i);
//
//            for(Map.Entry<String,Object> entry : obj.entrySet()){
//                String strkey1 = entry.getKey();
//                Object strval1 = entry.getValue();
//                System.out.println("KEY:"+strkey1+"  -->  Value:"+strval1+"\n");
//            }
//        }
        String tempPath="d:test.txt";
        String savePath="d://test//test.txt";

        File tempFile=new File(tempPath);
        File saveFile=new File(savePath);

        try {
            Files.move(tempFile.toPath(), saveFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
