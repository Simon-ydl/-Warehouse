package com.yjs.utils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class FileBase64ConvertUitl  {
 
 /**
  * 将文件转成base64 字符串
  * @param path 文件路径
  * @return   
  * @throws Exception
  */
 
 public static String encodeBase64File(String path) throws Exception {
   File file = new File(path);
   FileInputStream inputFile = new FileInputStream(file);
   byte[] buffer = new byte[(int) file.length()];
   inputFile.read(buffer);
   inputFile.close();
   return new BASE64Encoder().encode(buffer);
 
 }
 
 /**
  * 将base64字符解码保存文件
  * @param base64Code
  * @param targetPath
  * @throws Exception
  */
 
 public static void decoderBase64File(String base64Code, String targetPath)
    throws Exception {
   byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
   FileOutputStream out = new FileOutputStream(targetPath);
   out.write(buffer);
   out.close();
 
 }
 
 /**
  * 将base64字符保存文本文件
  * @param base64Code
  * @param targetPath
  * @throws Exception
  */
 
 public static void toFile(String base64Code, String targetPath)
    throws Exception {
 
    byte[] buffer = base64Code.getBytes();
    FileOutputStream out = new FileOutputStream(targetPath);
    out.write(buffer);
    out.close();
  }

    /**
     * 将base64字符编码转成inputStream
     * @param base64 base64编码的字符串
     * @return InputStream 文件输入流
     */
    public static InputStream base64ToInputStream (String base64) {
        ByteArrayInputStream stream = null;

        BASE64Decoder decoder = new BASE64Decoder();

        byte[] bytes = new byte[1024];

        try {
            bytes = decoder.decodeBuffer(base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 将base64字符串转换成一个MultipartFile对象
     * @param fileName 文件名
     * @param base64String 文件的base64编码的字符串
     * @return MultipartFile类型的文件对象
     */
    public static MultipartFile base64ToMultipartFile(String fileName, String base64String){
        InputStream inputStream = FileBase64ConvertUitl.base64ToInputStream(base64String);
        try{
           return new CustomerMultipartFile(fileName,inputStream);
        }
        catch(Exception e){
           e.printStackTrace();
           return null;
        }
    }

    /**
     * 将MultipartFile转成base64编码
     * @param file MultipartFile文件
     * @return base64File base64编码的字符串
     */
    public static String multipartFile2base64(MultipartFile file){
        BASE64Encoder base64Encoder =new BASE64Encoder();
        try{
            String base64File = base64Encoder.encode(file.getBytes());
            return base64File;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
 
 public static void main(String[] args) {
     try {
        String base64Code = encodeBase64File("D:/0101-2011-qqqq.tif");
        System.out.println(base64Code);
        decoderBase64File(base64Code, "D:/2.tif");
        toFile(base64Code, "D:\\three.txt");
     } catch (Exception e) {
        e.printStackTrace();
 
     }
 }
 
}
