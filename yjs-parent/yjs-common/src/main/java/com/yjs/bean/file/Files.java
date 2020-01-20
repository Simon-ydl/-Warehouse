package com.yjs.bean.file;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "ro_files")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id ;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "original_fileName")
    private String originalFileName;
    @Column(name = "nameEx")
    private String nameEx;
    @Column(name = "file_url")
    private String fileUrl;
    @Column(name = "business_code")
    private String businessCode;
    @Column(name = "item_id")
    private Integer itemId ;
    @Column(name = "file_code")
    private String fileCode;
    @Column(name = "file_again")
    private int fileAgain;
}
