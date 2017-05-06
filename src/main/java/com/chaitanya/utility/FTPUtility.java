package com.chaitanya.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPUtility {
	
	static String ftpServerAddress = "192.168.43.188";
    static String userName = "anonymous";
    static String password = "";
    static FTPClient ftpclient;
    private static Logger logger= LoggerFactory.getLogger(FTPUtility.class);
    
    public static Boolean connect() throws SocketException, IOException{
    	logger.debug("FTPUtility: connect-Start");
    	ftpclient = new FTPClient();
    	ftpclient.connect(ftpServerAddress);
        boolean result = ftpclient.login(userName, password);
        
        if (result == true) {
            logger.debug("Logged in Successfully !");
	    } else {
	        logger.debug("Login Fail!");
	    }
        logger.debug("FTPUtility: connect-End");
        return result;
    }
    
    public static void main(String[] arg) throws SocketException, IOException{
    	connect();
    }
    public static Boolean uploadFile(File file,String filePath) throws IOException{
    	logger.debug("FTPUtility: uploadFile-Start.");
    	if(connect()){
	    	ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
	
	        ftpclient.changeWorkingDirectory("/Attachment/");
	
	        ftpclient.makeDirectory(filePath);
	        ftpclient.changeWorkingDirectory("/Attachment/"+filePath);
	        FileInputStream fileInputStream = new FileInputStream(file);
	
	        // Upload file to the ftp server
	        boolean result = ftpclient.storeFile(file.getName(), fileInputStream);
	
	        if (result == true) {
	            logger.debug("File is uploaded successfully");
	        } else {
	            logger.debug("File uploading failed");
	        }
	        ftpclient.logout();
    	}
    	else{
    		logger.error("FTP connection not available");
    		throw new IOException("FTP connection not available");
    	}
        logger.debug("FTPUtility: upload-Start");
    	return true;
    }

    public static Boolean uploadFile(String fileName, FileInputStream fileInputStream) throws IOException{
    	logger.debug("FTPUtility: uploadFile-Start");
    	connect();
    	ftpclient.setFileType(FTP.BINARY_FILE_TYPE);

        ftpclient.changeWorkingDirectory("/");

        // Upload file to the ftp server
        boolean result = ftpclient.storeFile(fileName, fileInputStream);

        if (result == true) {
            logger.debug("File is uploaded successfully");
        } else {
            logger.debug("File uploading failed");
        }
        ftpclient.logout();
        logger.debug("FTPUtility: upload-Start");
    	return result;
    }
}
