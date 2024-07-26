package com.rebu.storage.test;

import com.rebu.common.util.FileUtils;
import com.rebu.storage.service.StorageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class StorageServiceTest {

    @Autowired
    private StorageService storageService;

    @DisplayName("StorageServiceTest Start")
    @BeforeEach
    public void setUp() {
        System.out.println("StorageServiceTest Start");
    }

    @DisplayName("File upload Test")
    @Test
    public void uploadTest() {
        //given
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());
        String destPath = "/images";
        String destName = "0";

        //when
        String result = storageService.uploadFile(file, destPath, destName);

        //then
        assert(result).equals(destPath+"/"+destName+"."+FileUtils.getExtension(file.getName()));
    }

    @DisplayName("File upload Test")
    @Test
    public void uploadFilesTest() {
        //given
        List<MultipartFile> files = new ArrayList<>();
        List<String> destNames = new ArrayList<>();
        destNames.add("2");
        destNames.add("3");
        String destPath = "/images";

        //when
        List<String> result = storageService.uploadFiles(files, destPath, destNames);

        //then
        for(int i=0; i<files.size(); i++)
            assert(result.get(i)).equals(destPath+"/"+destNames.get(i)+"."+FileUtils.getExtension(files.get(i).getName()));
    }

    @DisplayName("File remove Test")
    @Test
    public void removeFile() {
        //given
        String destFilePath = "/images/0.png";

        //when
        String result = storageService.removeFile(destFilePath);

        //then
        assert(result).equals(destFilePath);
    }

    @DisplayName("Files remove Test")
    @Test
    public void removeFiles() {
        //given
        String destDirectoryPath = "/images/0.png";

        //when
        String result = storageService.removeFiles(destDirectoryPath);

        //then
        assert(result).equals(destDirectoryPath);
    }

    @DisplayName("StorageServiceTest End")
    @AfterEach
    public void tearDown() {
        System.out.println("StorageServiceTest End");
    }
}