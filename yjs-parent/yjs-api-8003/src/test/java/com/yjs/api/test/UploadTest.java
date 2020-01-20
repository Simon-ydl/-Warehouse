package com.yjs.api.test;


import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yjs.utils.FileBase64ConvertUitl;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * fastDFS文件服务器调用示例
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadTest {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Test
    public void testUpload() throws FileNotFoundException {
        File file = new File("D:/upload/40.jpg");
        // 上传并且
        StorePath storePath = this.storageClient.uploadFile(
                new FileInputStream(file), file.length(), "jpg", null);
        // 带分组的路径
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
    }

    @Test
    public void testUploadAndCreateThumb() throws FileNotFoundException {
        File file = new File("D:\\test\\baby.png");
        // 上传并且生成缩略图
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                new FileInputStream(file), file.length(), "png", null);
        // 带分组的路径
        System.out.println(storePath.getFullPath());
        // 不带分组的路径
        System.out.println(storePath.getPath());
        // 获取缩略图路径
        String path = thumbImageConfig.getThumbImagePath(storePath.getPath());
        System.out.println(path);
    }
    /**
     * 删除文件方法
     */
    @Test
    public void testDeleteByFileId(){
        //文件id号
        storageClient.deleteFile("group1/M00/00/03/CpMCql4Ore2AFKLYAACRonib-VQ239.jpg");
    }

    @Test
    public void testGetFileById(){
        byte[] bytes = downloadFile("group1/M00/00/00/E2gzUF4UMA6AFcnnAACRonib-VQ491.jpg");
    }

    @Test
    public void testGetFileBase64(){
        String fileUrl ="group1/M00/00/03/CpMCql4OsdeAAv6GAAAmABvpaVM808.doc";
        String fileToBase64 = downFileToBase64(fileUrl);
        System.out.println(fileToBase64);
    }

    /**
     *
     * @param fileUrl fastDFS上的文件id
     * @return byte[]
     */
    public byte[] downloadFile(String fileUrl){
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));//分组号group1
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1); //分组号后面的路径M00/00/02/CpMCql4NsUaAUxBeAACHF-Sas1w317.jpg
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = this.storageClient.downloadFile(group, path, downloadByteArray);
        try {
            //将文件保存到d盘
            String filesuffix = fileUrl.substring(fileUrl.lastIndexOf("."),fileUrl.length());
            String downFileNamePreffix = "123";
            String downFileName = downFileNamePreffix + filesuffix;
            FileOutputStream fileOutputStream = new FileOutputStream("D:/"+downFileName);
            fileOutputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public String downFileToBase64(String fileUrl){
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));//分组号group1
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1); //分组号后面的路径M00/00/02/CpMCql4NsUaAUxBeAACHF-Sas1w317.jpg
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = this.storageClient.downloadFile(group, path, downloadByteArray);
        return new BASE64Encoder().encode(bytes);
    }
}
