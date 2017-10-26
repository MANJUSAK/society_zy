package com.goodsoft.society_zy.domain.entity.file;

import java.beans.Transient;
import java.util.Objects;

/**
 * function 文件数据实体类
 * Created by 严彬荣 on 2017/8/4.
 * version v1.0
 */
public class FileData implements java.io.Serializable {
    private static final long serialVersionUID = -7500263342165526473L;
    private String fileId;//文件编号
    private String path;//文件路径
    private String bases;//根目录
    private String sort;//类别
    private String fileName;//原文件名
    private String newFileName;//新文件名
    private String suffix;//文件后缀

    @Transient
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getBases() {
        return bases;
    }

    public void setBases(String bases) {
        this.bases = bases == null ? null : bases.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }


    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName == null ? null : newFileName.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileData)) return false;
        FileData fileData = (FileData) o;
        return Objects.equals(fileId, fileData.fileId) &&
                Objects.equals(path, fileData.path) &&
                Objects.equals(bases, fileData.bases) &&
                Objects.equals(sort, fileData.sort) &&
                Objects.equals(fileName, fileData.fileName) &&
                Objects.equals(newFileName, fileData.newFileName) &&
                Objects.equals(suffix, fileData.suffix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, path, bases, sort, fileName, newFileName, suffix);
    }
}


