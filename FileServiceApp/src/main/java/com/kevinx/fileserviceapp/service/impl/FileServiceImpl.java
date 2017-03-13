package com.kevinx.fileserviceapp.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kevinx.fileserviceapp.service.FileService;
import com.sun.jersey.core.header.FormDataContentDisposition;

@Service
public class FileServiceImpl implements FileService {

	private static final Logger LOGGER = Logger.getLogger(FileServiceImpl.class.getName());
	
	@Value("${file.repository}")
	private String baseDir;

	@Override
	public String saveFileToRepo(InputStream inputStream, FormDataContentDisposition disposition) {
		File dir = new File(baseDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String fullPath = baseDir + disposition.getFileName();
		try {
			OutputStream outputStream = new FileOutputStream(new File(fullPath));
			int read = 0;
			byte[] bytes = new byte[1000];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			String msg = "Error closing outputStream...";
			LOGGER.log(Level.SEVERE, msg, e);
			return msg;
		}
		return fullPath;
	}

	@Override
	public String readFileFromRepo(String fileName) {
		String fullPath = baseDir + fileName;
		StringBuilder output = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(fullPath));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				output.append(line);
			}
		} catch (IOException e) {
			String msg = "Error reading file...";
			LOGGER.log(Level.SEVERE, msg, e);
			return msg;
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException e) {
				String msg = "Error closing bufferReader...";
				LOGGER.log(Level.SEVERE, msg, e);
				return msg;
			}
		}
		LOGGER.log(Level.INFO, output.toString());
		return output.toString();
	}
	
	@Override
	public File getFileByName(String fileName) {
		String fullPath = baseDir + fileName;
		File file = new File(fullPath);
		if(!file.exists()){
			LOGGER.log(Level.INFO, fileName+" cannot be found...");
			return null;
		}
		return file;
	}

	@Override
	public String getFileListByKeyword(String keyword) {
		File dir = new File(baseDir);
		StringBuilder output = new StringBuilder();
		File[] list = dir.listFiles();
		if (list != null) {
			for (File file : list) {
				if (file.getName().contains(keyword)) {
					output.append(file.getName() + " ");
				}
			}
		}
		LOGGER.log(Level.INFO, output.toString());
		return output.toString();
	}
	
	@Override
	@Scheduled(fixedDelayString="${fixed.delay.in.ms}")
	public void emailSendingSimulator(){
		String fileList = listFilesInLastHour();
		LOGGER.log(Level.INFO, fileList);
	}
	
	private String listFilesInLastHour() {
		File dir = new File(baseDir);
		StringBuilder output = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH");
		Date date = new Date(System.currentTimeMillis() - 3600 * 1000);
		String currentHour = sdf.format(date);
		File[] list = dir.listFiles();
		if (list != null) {
			for (File file : list) {
				if (currentHour.equals(sdf.format(file.lastModified()))) {
					output.append(file.getName() + " ");
				}
			}
		}
		return output.toString();
	}

}
