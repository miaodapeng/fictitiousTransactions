package com.test;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
        Path p= Paths.get("d:\\aaa");
        List<java.lang.String> list= Files.readAllLines(p);

        Map<String, Map<String,String>> result=new HashMap<>();
        for(java.lang.String line:list){

            java.lang.String [] ll=line.split("\t");

            if(!result.containsKey(ll[0])){
                Map<java.lang.String,String> attrMap=new HashMap<>();
                result.put(ll[0],attrMap);
            }
            if(ll.length==3){
            Map<String,String> attrMap =result.get(ll[0]);
            if(!attrMap.containsKey(ll[1])){
                attrMap.put(ll[1],ll[2]);
            }else{
                System.out.println("已经计算了属性，一个属性不能有多个属性值"+line);
            } }
        }
        for(Map.Entry<String,Map<String,String>> entry:result.entrySet()){
            StringBuilder sb=new StringBuilder();
            sb.append(entry.getKey()+"\t");
            for(Map.Entry<String, String> entry2:entry.getValue().entrySet()){
                sb.append(entry2.getKey()+"\t"+entry2.getValue()+"\t");
            }
            System.out.println(sb.toString());
        }

    }
}
