package com.rebu.storage.service;

import com.rebu.common.util.FileUtils;
import com.rebu.storage.exception.StorageConnectionErrorException;
import com.rebu.storage.exception.StorageUploadFailException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FtpStorageService implements StorageService{

    @Value("${ftp.server}")
    private String server;

    @Value("${ftp.port}")
    private String port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Override
    public String uploadFile(MultipartFile file, String destDirectoryPath, String destFileName) {
        FTPClient ftpClient = connect();
        String filePath;
        try {
            filePath = upload(ftpClient, file, destDirectoryPath, destFileName);
        } catch (IOException e) {
            throw new StorageUploadFailException();
        } finally{
            disconnect(ftpClient);
        }
        return filePath;
    }

    @Override
    public List<String> uploadFiles(List<MultipartFile> files, String destDirectoryPath, List<String> destFileNames) {
        FTPClient ftpClient = connect();
        List<String> filePaths = new ArrayList<>();
        try {
            for(int i=0; i<files.size(); i++) {
                String filePath = upload(ftpClient, files.get(i), destDirectoryPath, destFileNames.get(i));
                filePaths.add(filePath);
            }
        } catch (IOException e) {
            throw new StorageUploadFailException();
        } finally{
            disconnect(ftpClient);
        }
        return filePaths;
    }

    @Override
    public String removeFile(String destFilePath) {
        return "";
    }

    @Override
    public String removeFiles(String destDirectoryPath) {
        return "";
    }

    private FTPClient connect(){
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");
        try {
            ftpClient.connect(server, Integer.parseInt(port));
            ftpClient.login(username,password);
        } catch (IOException e) {
            throw new StorageConnectionErrorException();
        }
        return ftpClient;
    }

    private void disconnect(FTPClient ftpClient){
        if(!ftpClient.isConnected())
            return;
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            throw new StorageConnectionErrorException();
        }
    }

    private String upload(FTPClient ftpClient, MultipartFile file, String destDirectoryPath, String destFileName) throws IOException{
        String filePath = FileUtils.generateFilePath(destDirectoryPath, destFileName, FileUtils.getExtension(file.getOriginalFilename()));
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.storeFile(filePath, new ByteArrayInputStream(file.getBytes()));
        return filePath;
    }
}
