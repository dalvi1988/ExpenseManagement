package com.chaitanya.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPUtility {
	
	static String ftpServerAddress = "localhost";
    static String userName = "anonymous";
    static String password = null;
    static FTPClient ftpclient;
    private static Logger logger= LoggerFactory.getLogger(FTPUtility.class);
    
    public static Boolean connect() throws IOException {
    	logger.info("FTPUtility: connect-Start");
    	ftpclient = new FTPClient();
    	ftpclient.connect(ftpServerAddress);
        boolean result=false;
        result= ftpclient.login(userName, password);
        
        if (result == true) {
            logger.info("Logged in Successfully !");
	    } else {
	        logger.info("Login Fail!");
	    }
        logger.info("FTPUtility: connect-End");
        return result;
    }
    
 
    public static Boolean uploadFile(File file,String filePath) throws Exception{
    	logger.info("FTPUtility: uploadFile-Start.");
    	if(connect()){
	    	ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
	
	        ftpclient.changeWorkingDirectory("/Attachment/");
	
	        ftpclient.makeDirectory(filePath);
	        ftpclient.changeWorkingDirectory("/Attachment/"+filePath);
	        FileInputStream fileInputStream = new FileInputStream(file);
	
	        // Upload file to the ftp server
	        boolean result = ftpclient.storeFile(file.getName(), fileInputStream);
	
	        if (result == true) {
	            logger.info("File is uploaded successfully");
	        } else {
	            throw new Exception("File uploading failed");
	        }
	        ftpclient.logout();
    	}
    	else{
    		logger.error("FTP connection not available");
    		throw new IOException("FTP connection not available");
    	}
        logger.info("FTPUtility: upload-Start");
    	return true;
    }

    public static Boolean uploadFile(String fileName, FileInputStream fileInputStream) throws IOException{
    	logger.info("FTPUtility: uploadFile-Start");
    	connect();
    	ftpclient.setFileType(FTP.BINARY_FILE_TYPE);

        ftpclient.changeWorkingDirectory("/");

        // Upload file to the ftp server
        boolean result = ftpclient.storeFile(fileName, fileInputStream);
        
        if (result == true) {
            logger.info("File is uploaded successfully");
        } else {
            logger.info("File uploading failed");
        }
        ftpclient.logout();
        logger.info("FTPUtility: upload-Start");
    	return result;
    }
    
    public static InputStream retriveFile(String filePath) throws IOException{
    	logger.info("FTPUtility: retriveFile-Start");
    	connect();
    	ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
    	ftpclient.setControlKeepAliveTimeout(30);
    	ftpclient.enterLocalPassiveMode();
    	InputStream inputStream =null;
    	try  {
    	
    		inputStream =ftpclient.retrieveFileStream(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
       
       // ftpclient.logout();
        logger.info("FTPUtility: retriveFile-end");
        return inputStream;
    }
    
    public static void main(String[] arg) throws SocketException, IOException{
    	connect();
    }
}
