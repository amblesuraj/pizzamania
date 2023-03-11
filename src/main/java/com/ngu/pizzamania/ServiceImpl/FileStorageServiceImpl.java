package com.ngu.pizzamania.ServiceImpl;

import com.ngu.pizzamania.Exception.FileStorageException;
import com.ngu.pizzamania.Exception.MyFileNotFoundException;
import com.ngu.pizzamania.Service.FileStorageService;
import jakarta.transaction.Transactional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageServiceImpl() {
        this.fileStorageLocation = Paths.get(System.getProperty("user.dir") + "/upload").toAbsolutePath().normalize();

        System.out.println(fileStorageLocation);

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored",
                    ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {

        File f = new File(fileStorageLocation + file.getOriginalFilename());

        if (f.exists()){
            f.delete();
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence" + fileName);
            }
            String newFileName = "PIZZA_" + fileName;
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return newFileName;
        } catch (IOException ex) {
            throw new FileStorageException(String.format("Could not store file %s !! Please try again!", fileName), ex);
        }

    }

    @Override
    public List<String> storeMultipleFiles(List<MultipartFile> files) throws IOException {

        List<String> fileNames = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = storeFile(file);
            fileNames.add(filename);

        }

        return fileNames;
    }


    @Override
    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public void deleteFile(String fileName) throws IOException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                System.out.println("From DeleteFileResource ::" + resource.getFilename());
                System.out.println("From DeleteFileResource ::" + resource.getFile().getAbsolutePath());
                resource.getFile().delete();
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    @Override
    public Path rootLocation() {
        // TODO Auto-generated method stub
        return this.fileStorageLocation;
    }

}