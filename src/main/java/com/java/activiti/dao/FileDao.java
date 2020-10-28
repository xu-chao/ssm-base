package com.java.activiti.dao;

import com.java.activiti.model.File;
import com.java.activiti.model.Images;
import com.java.activiti.model.Repair;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FileDao {
//    int addFile(Repair repair);
    int addFiles(File fileInfo);

//    int addImage(Repair repair);
    int addImages(Images images);

    List<File>findFilesByRepairID(String repairFileID);

    List<Images>findImageByRepairID(String repairFileID);
//���������ļ�
    List<File>selectFileList(List<String> repairFileID);

    List<Images>selectImageList(List<String> repairFileID);

    int deleteImagebyID(String id);

    int deleteFilebyID(String id);
//����ɾ��
    int deleteListFile(List<String> id);

    int deleteListImage(List<String> id);
}
