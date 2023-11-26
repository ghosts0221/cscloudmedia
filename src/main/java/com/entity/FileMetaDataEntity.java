package com.entity;

import com.azure.cosmos.spring.data.core.mapping.Container;
import org.springframework.data.annotation.Id;

@Container(containerName = "fileMetadata")
public class FileMetadataEntity {

    @Id
    private String id;

    private String fileName;
    private String fileType;
    private long fileSize;  // 添加文件大小字段
    private String fileExtension;  // 添加文件后缀名字段
    // 添加其他元数据字段

    // 构造函数、Getter 和 Setter 方法

    public FileMetadataEntity() {
        // 默认构造函数
    }

    public FileMetadataEntity(String fileName, String fileType, long fileSize, String fileExtension) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileExtension = fileExtension;
        // 设置其他元数据字段
    }

    // 添加其他元数据字段的 Getter 和 Setter 方法

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
