package com.rebu.storage.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    String uploadFile(MultipartFile file, String destDirectoryPath, String destFileName);

    List<String> uploadFiles(List<MultipartFile> files, String destDirectoryPath, List<String> destFileNames);

    String removeFile(String destFilePath);

    String removeFiles(String destDirectoryPath);
}
