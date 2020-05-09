package com.vedeng.common.util;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;  
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;  
import java.util.Map;

import com.common.constants.Contant;
import com.vedeng.common.service.impl.BaseServiceimpl;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.vedeng.common.key.CryptBase64Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequest {
    public static Logger logger = LoggerFactory.getLogger(HttpRequest.class);

	/** 
     * 向指定URL发送GET方法的请求 
     *  
     * @param url 
     *            发送请求的URL 
     * @param param 
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。 
     * @return URL 所代表远程资源的响应结果 
     */  
    public static String sendGet(String url, String param) {  
        String result = "";  
        BufferedReader in = null;  
        try {  
            String urlNameString = url + "?" + param;  
            URL realUrl = new URL(urlNameString);  
            // 打开和URL之间的连接  
            URLConnection connection = realUrl.openConnection();  
            // 设置通用的请求属性  
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 建立实际的连接  
            connection.connect();  
            // 获取所有响应头字段  
            Map<String, List<String>> map = connection.getHeaderFields();  
            // 遍历所有的响应头字段  
            for (String key : map.keySet()) {  
                System.out.println(key + "--->" + map.get(key));  
            }  
            // 定义 BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(  
                    connection.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            logger.error("发送GET请求出现异常！",e);
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
        return result;  
    }  
  
    /**  
     * 向指定 URL 发送POST方法的请求  
     * @param url 发送请求的 URL  
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。  
     * @return 所代表远程资源的响应结果  
     */  
    public static String sendPost(String url, String param) {  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "text/html");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            //1.获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            //2.中文有乱码的需要将PrintWriter改为如下  
            //out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")  
            // 发送请求参数  
            out.print(param);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            logger.error("发送POST请求出现异常！",e);
        }  
        //使用finally块来关闭输出流、输入流  
        finally{  
            try{  
                if(out!=null){  
                    out.close();  
                }  
                if(in!=null){  
                    in.close();  
                }  
            }  
            catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }
        logger.info("post推送结果！"+result);
        return result;
    }  
    

      
	
	
    public static void main(String[] args) throws Exception {  
//        //发送 GET 请求  
//        String s=HttpRequest.sendGet("http://localhost:6144/Home/RequestString", "key=123&v=456");  
//        System.out.println(s);  
          
        //发送 POST 请求  
    	//String sr=HttpRequest.sendPost("http://192.168.2.72:8080/sd-0.0.1-SNAPSHOT/test","");
    	//String sr=HttpRequest.postHttp("http://192.168.2.72:8080/sd-0.0.1-SNAPSHOT/test","");
    	 
       //String sr=HttpRequest.postHttp("http://192.168.2.72:8080/sd-0.0.1-SNAPSHOT/receService", "Pqly3c9g48M6zd2d+IiDDQHcDinA+o2TPVmuts8FWvwWeOQO3J7nUfA3KUClD3CMGq7g8T5yBNKQ+0pi3mALnD43kFWaw7WtihF3Z49f6Nt1oxMVzj7w33C2oHFkBR97YyG/IFfm0uYtybgTAFeCBFTghR/yBZxhmccrDnrsRgRjIb8gV+bS5vhDGUpr66TGAOc0qLPkLnCoTizjwVekWuDp5plw6Zt7ZDqY3LHJzGM5arLopsmglQNr7U2wV5YS8ovGiNFH59+DvBVryeH9U50Kg4Xos+HMEsSg/EmFIBb+36qVwKQndEiAU5RZSaoxFCvp1jSc9SnUTgykU4UMRA8MuqEiGxKbak2aWrhFJvtjIb8gV+bS5s15xljW+5DPrlmvzCpOwFVDndvxwi3pF2fKTRsIDWdOBjaBEFkMLEpFxl7d8MqrMlaJ4hghJsK0IDFIAoe8CQHwUqBLoc9l4RZflkMOxlJ1/io9c6eQXYL4mLI+iJElxnhdYAQW7D1WXkFdi8dytFxwSKUZqBuEjSyvCfYBY2Uo2X3GRpWhVTvLpghV2rRdE8Cg3jz7+S8HEnP1MVx3tdGltOooFvvYd1IFvT2yMRAs6dGYNB7h2t0LRDHgYyPBFDn9Ft4t2zeKGHaW/urwZmZkiGMfLNkG0YctWSwbXUfGOZ0t0YkINwUfx1JS9N8JqJH4Ik65Oq82g7wVa8nh/VNwzNx4r+zt1JjBLb9OQ7Jm8ovGiNFH59+DvBVryeH9Uws6W79W9rYEhhltgCgp87BQlt/MH1Kz6kXRe4nJ1DmDX69oq8fHbpUarP8UPX37hUcKanhuu6F1LjRuA00+N1djIb8gV+bS5s15xljW+5DPbTlnSSNA7FRDndvxwi3pF2fKTRsIDWdOBjaBEFkMLEpFxl7d8MqrMgyZYf7dFx25HUCy0Fu8To/wUqBLoc9l4RZflkMOxlJ1/io9c6eQXYJ7zWiQIDlL58ptuIGYlR47IjtmvClbu171aQ/0ZFbEnujV59al91ilOHabMTZZe4L4RevNVJhX8Iu70jHZgis4JoUeomc5GYu4yJqmeIGYE/8+OVThWqMNWImnxqLvRAX929oIcwTVL8bNOQhGZXBIl9cTvmP2S69hPFibz9nVxzZwKBtDDdcWdm5soXamy9pVnJlphCaHlzXw3h61jTTLQmlfEaNmfsZqZqf/YXkjSJ0He9YkISMJbLZtNWAMsgl/EQAeLLKqZSExq9JPdGX2bVFi0iNTnsYeq1MpIyV6n3GLmBDW2Y6O1E4MpFOFDEReDgLIL1jXiuskL/qrfBa16dGYNB7h2t1a6rJvD4qoHe+aDMZSJDUqtklv/AV3IhvpXvOmCNNgXe+okq7mcuIMULlYAQPNFwKOZ15EBydqZS1J+IZiC6390h0jPceBkiYrtzIlAqJUsmWF5QdRbk7/yYed4b6ysJ2oAXpp5FcyMOIFYBYGMTNG7fVfZYUA8NIKTFS8aORjLiDdtOJoULc2e9kYVrzF7Kw4dpsxNll7gvhF681UmFfwi7vSMdmCKzjFd9hcjvvdgexc8brqsNzCVZHSXa7fWos=");
    	
    	String param = "Pqly3c9g48M6zd2d+IiDDQHcDinA+o2TPVmuts8FWvwWeOQO3J7nUfA3KUClD3CMGq7g8T5yBNKQ+0pi3mALnD43kFWaw7WtihF3Z49f6Nt1oxMVzj7w33C2oHFkBR97YyG/IFfm0ua7vBz0omA0zBKM3U9Jd7RN/AtdtPJIs0N0Bz//h5wqjvw+AYRhhQcp+rrpoTpRtulW1euqMDvwkTh2vXLE/lqqh4yy/CSyP2hPTI7br3QZZxUd4ln5U39wVZyZaYQmh5c18N4etY00y9TCCuTRM/M2cwOr/4QbMfydB3vWJCEjCTh2vXLE/lqqdvqCjO97+d/P9ONrtZfg2bhrlduoGXdlcSOHniVCaytkiGMfLNkG0X8v8Ie8uoDdWWky4Ay+XU05fdPVMesMSgLdXd2Aml6Hg7wVa8nh/VN8YVPPd9jjFFwZAmyU41ysUWDoTyDgGOqDvBVryeH9UwGRHRrVFIfwJTEPF/pE9NzYkwIyK24hX9MCmLmDpanr9WkP9GRWxJ7o1efWpfdYpTh2mzE2WXuC6dGYNB7h2t1a6rJvD4qoHQGE98DgJAiPtklv/AV3IhvpXvOmCNNgXWwankMsBBjXsL+DbQhF5ddIMKW0aQPoD1t9wEW7tkBAn2G4fevQXI0qAceZTuSDrlyDXWrB5jFT2T1LLHsEn7j4RevNVJhX8Iu70jHZgis4xXfYXI773YHsXPG66rDcwlWR0l2u31qL";
    	String params = CryptBase64Tool.desDecrypt(param, "12346780987654");
    	System.out.println(params);
    	try {
			param = URLEncoder.encode(param,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(Contant.ERROR_MSG, e);
		}
    	//System.out.println(param);
       String sr=HttpRequest.sendPost("http://192.168.2.72:7892/ved/invoiceService", "msg="+param);  
       
       //String sr=HttpRequest.sendPost("http://192.168.2.72:8080/sd-0.0.1-SNAPSHOT/invoiceService", "msg=Pqly3c9g48M6zd2d+IiDDQHcDinA+o2TPVmuts8FWvwWeOQO3J7nUfA3KUClD3CMGq7g8T5yBNKQ+0pi3mALnD43kFWaw7WtihF3Z49f6Nt1oxMVzj7w33C2oHFkBR97YyG/IFfm0uYtybgTAFeCBPpff0rYIcZCmccrDnrsRgRjIb8gV+bS5vhDGUpr66TGyjIzG1Y65lS1pvyX9E2jK8HKTIV3xbsG3cFceQs8sViwaCroVMCPEUVXgX4a8FNd8ovGiNFH59+DvBVryeH9U7OnkreK0/f4FBvUBmE4z5X9kj9IC6e0uduZcJBuq8WUdSZ1PkKRbpa0eSuDiRtuBzqANaWdv4IqAVr61PYK7TlEiivBcWDFGDivY+jbl4Rh6ja29FaIzJ4fgE7t9kNdKetl0bMCjwz71E4MpFOFDESE6aj1YnGtsU4HQlxDb3iC6dGYNB7h2t2dWGh1uP1MgJpAdRHg7wwfgRpHy69WD/FQLxxW8RQdqWMhvyBX5tLmPL5NsuSwdNridF+0E1bTtnxcr0Dl3j83dSZ1PkKRbpau0fMpJxSvkuT72hx4QmgSaMwDwpmkhbIzf2+87vgmvIO8FWvJ4f1TAZEdGtUUh/AlMQ8X+kT03NiTAjIrbiFf0wKYuYOlqev1aQ/0ZFbEnujV59al91ilOHabMTZZe4L4RevNVJhX8Iu70jHZgis4xXfYXI773YHsXPG66rDcwlWR0l2u31qL");  
       //String sr=HttpRequest.sendPost("http://192.168.2.72:8080/sd-0.0.1-SNAPSHOT/ctService", "msg=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");  
       System.out.println(sr);  
    }  
}
