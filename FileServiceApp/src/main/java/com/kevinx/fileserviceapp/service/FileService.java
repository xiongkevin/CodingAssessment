package com.kevinx.fileserviceapp.service;

import java.io.File;
import java.io.InputStream;

import com.sun.jersey.core.header.FormDataContentDisposition;

public interface FileService {
	public String saveFileToRepo(InputStream uploadedInputStream, FormDataContentDisposition fileDetail);
	
	public String readFileFromRepo(String fileName);
	
	public File getFileByName(String fileName);
	
	public String getFileListByKeyword(String keyword);
	
	public void emailSendingSimulator();
}
