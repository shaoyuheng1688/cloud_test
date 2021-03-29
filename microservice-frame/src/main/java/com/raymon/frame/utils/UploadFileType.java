package com.raymon.frame.utils;

import com.raymon.frame.web.exception.ApplicationException;

public enum UploadFileType {
    jpg(1, "jpg"),
    jpeg(1, "jpeg"),
    png(1, "png"),
    bmp(1, "bmp"),
    pdf(2, "pdf"),
    doc(2, "doc"),
    docx(2, "docx"),
    dbf(2, "dbf"),
    xls(2, "xls"),
    rar(2, "rar"),
    zip(2, "zip");

    private long fileType;

    private String name;

    UploadFileType(int fileType, String name){
        this.fileType = fileType;
        this.name = name;
    }

    public long getFileType() {
        return fileType;
    }

    public String getName() {
        return name;
    }

    public static UploadFileType getByString(String value){
        for (UploadFileType uploadFileType : UploadFileType.values()) {
            if(uploadFileType.getName().equals(value.toLowerCase())){
                return uploadFileType;
            }
        }
        throw new ApplicationException("暂不支持上传该格式的文件！");
    }
}
