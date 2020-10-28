package com.java.activiti.service;


import com.java.activiti.model.File;
import com.java.activiti.model.Images;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface FileService {


	int addFileImage(MultipartFile file, HttpServletRequest request, String fileID) throws IOException;
//	���Ҹ��ɹ����������ļ�
	List<File> findFilesByRepairID(String repairFileID);

	List<Images> findImagesByRepairID(String repairFileID);

	HttpServletResponse downloadFile(String path, HttpServletResponse response);
	/**
	 *   ��ȡͼƬ ת��Ϊ �ַ�
	 * @param   path  fileID
	 * @throws Exception
	 */
	String getImageStr(String path,String fileID) throws Exception;

	/**
	 *   ɾ���ļ�
	 * @param  id
	 * @throws Exception
	 */
	int deleteFilebyID(String id);

	int deleteImagebyID(String id);

	 int deleteFileByFileID(List<String> fileIDs);
}