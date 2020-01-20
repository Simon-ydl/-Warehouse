package com.yjs.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 自定义的MultipartFile实现类，用于base64转成MultipartFile
 */
public class CustomerMultipartFile implements MultipartFile, Serializable {

    private final String name;

    private String originalFilename;

    @Nullable
    private String contentType;

    private final byte[] content;

    /**
     * 创建一修带文件内容的CustomerMultipartFile类
     * @param name 文件名
     * @param content 文件的内容
     */
    public CustomerMultipartFile(String name, @Nullable byte[] content) {
        this(name, "", null, content);
    }

    /**
     * 创建一个新的带文件内容的CustomerMultipartFile类
     * @param name 文件名
     * @param contentStream 文件内容流
     * @throws IOException 如果读取失败
     */
    public CustomerMultipartFile(String name, InputStream contentStream) throws IOException {
        this(name, "", null, FileCopyUtils.copyToByteArray(contentStream));
    }

    /**
     * 创建一个带文件内容的CustomerMultipartFile类
     * @param name 文件名
     * @param originalFilename 文件的原始名（在原来的机器上）
     * @param contentType 文件类型
     * @param content 文件内容
     */
    public CustomerMultipartFile(
            String name, @Nullable String originalFilename, @Nullable String contentType, @Nullable byte[] content) {

        Assert.hasLength(name, "Name must not be null");
        this.name = name;
        this.originalFilename = (originalFilename != null ? originalFilename : "");
        this.contentType = contentType;
        this.content = (content != null ? content : new byte[0]);
    }

    /**
     * 创建一个新的带获取文件内容CustomerMultipartFile类
     * @param name 文件名
     * @param originalFilename 文件的原始名（在原来的机器上）
     * @param contentType 文件类型
     * @param contentStream 文件内容流
     * @throws IOException 失败的异常
     */
    public CustomerMultipartFile(
            String name, @Nullable String originalFilename, @Nullable String contentType, InputStream contentStream)
            throws IOException {

        this(name, originalFilename, contentType, FileCopyUtils.copyToByteArray(contentStream));
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Override
    @Nullable
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public boolean isEmpty() {
        return (this.content.length == 0);
    }

    @Override
    public long getSize() {
        return this.content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return this.content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.content, dest);
    }
}
