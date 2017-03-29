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
	
	static String ftpServerAddress = "10.90.19.28";
    static String userName = "CDalvi";
    static String password = "MStar300";
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
    
    public static Boolean uploadFile(String filePath) throws IOException{
    	logger.debug("FTPUtility: uploadFile-Start");
    	ftpclient.setFileType(FTP.BINARY_FILE_TYPE);

        ftpclient.changeWorkingDirectory("/");

        File file = new File(filePath);
        String testName = file.getName();
        FileInputStream fileInputStream = new FileInputStream(file);

        // Upload file to the ftp server
        boolean result = ftpclient.storeFile(testName, fileInputStream);

        if (result == true) {
            logger.debug("File is uploaded successfully");
        } else {
            logger.debug("File uploading failed");
        }
        ftpclient.logout();
        logger.debug("FTPUtility: upload-Start");
    	return result;
    }

    public static Boolean uploadFile(String fileName, FileInputStream fileInputStream) throws IOException{
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
    	return result;
    }
}
