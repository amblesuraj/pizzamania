package com.ngu.pizzamania.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileStorageService {

    String storeFile(MultipartFile file) throws IOException;

    List<String> storeMultipleFiles(List<MultipartFile> files) throws IOException;
    Path rootLocation();

    Resource loadFileAsResource(String fileName);

    void deleteFile(String fileName) throws IOException;
}
