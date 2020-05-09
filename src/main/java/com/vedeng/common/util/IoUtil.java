package com.vedeng.common.util;

import com.common.constants.Contant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class IoUtil {
    public static Logger logger = LoggerFactory.getLogger(IoUtil.class);

   
    public static String readeFile(String filePath) {
        FileInputStream input = null;
        String result = "";
        try {
            //1.根据path实例化一个输入流的对象
            input = new FileInputStream(filePath);
            //2.返回这个输入流中可以被读的剩下的bytes字节的估计值；
            int size = input.available();
            //3.根据输入流的字节创建一个byte数组
            byte[] array = new byte[size];
            //4.把数据读取到byte数组中
            input.read(array);
            //5.根据获取的byte数组新建一个字符串，然后输出
            result = new String(array);
        } catch (FileNotFoundException e) {
            logger.error(Contant.ERROR_MSG, e);
        } catch (IOException e) {
            logger.error(Contant.ERROR_MSG, e);
        } finally {
            if(input != null){
                try {
                    //关闭
                    input.close();
                } catch (IOException e) {
                    logger.error(Contant.ERROR_MSG, e);
                }
            }
        }
        return result;
    }
    
    
    public static void writeFile(String path, String str) throws IOException{
        FileWriter fw = new FileWriter(path);
        //调用输出流对象的写数据的方法
        //写一个字符串数据
        fw.write(str);
        //数据没有直接写到文件，其实是写到了内存缓冲区
        fw.flush();
        
        //释放资源
        //通知系统释放和该文件相关的资源
        fw.close();
    };

    
    
    
}
