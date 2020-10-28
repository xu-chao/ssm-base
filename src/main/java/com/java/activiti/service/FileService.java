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
//	查找该派工单下所有文件
	List<File> findFilesByRepairID(String repairFileID);

	List<Images> findImagesByRepairID(String repairFileID);

	HttpServletResponse downloadFile(String path, HttpServletResponse response);
	/**
	 *   提取图片 转化为 字符
	 * @param   path  fileID
	 * @throws Exception
	 */
	String getImageStr(String path,String fileID) throws Exception;

	/**
	 *   删除文件
	 * @param  id
	 * @throws Exception
	 */
	int deleteFilebyID(String id);

	int deleteImagebyID(String id);

	 int deleteFileByFileID(List<String> fileIDs);
}