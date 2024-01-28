package com.itv.mobilestore.Services;

import com.itv.mobilestore.FileStorageProperties;
import com.itv.mobilestore.dtos.RegistorMobDto;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.itv.mobilestore.entities.mob;
import com.itv.mobilestore.repositories.MobRepository;
//import jakarta.annotation.Resource;

@Service
public class MobServices {

    @Autowired
    private MobRepository repository;

    public mob registerMob(RegistorMobDto registorMobDto) {
        mob mob = new mob();
        mob.setMobname(registorMobDto.getMobname());
        mob.setLastname(registorMobDto.getLastname());
        mob.setColor(registorMobDto.getColor());
        repository.save(mob);
        return mob;

    }

    public List<mob> getAll() {
        return repository.findAll();
    }

    public mob getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void deletemob(Integer id) {
        repository.deleteById(id);
    }

    public mob updatemob(Integer id, mob mob) {
        mob.setId(id);
        return repository.save(mob);
    }

    public List<mob> findBymobname(String mobname) {
        return this.repository.findBymobname(mobname);
    }

    public List<mob> findBycolor(String color) {
        return this.repository.findBycolorContaining(color);
    }

    public List<mob> findByname(String mobname) {
        return null;
    }

    public Object findByMobname(String mobname) {
        return null;
    }

    private final java.nio.file.Path rootLocation;

    public MobServices(FileStorageProperties properties) {
        this.rootLocation = Paths.get(properties.getUploadDir());
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Upload Directory Not Created");
        }
    }

    public String uploadFile(Integer id, MultipartFile file) {
       
        java.nio.file.Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename()));

        
        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "File Cannot be uploaded");
        }

       
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/mobs/download/")
                .path(file.getOriginalFilename())
                .toUriString();

        mob mob = this.repository.findById(id).get();
        mob.setImageUrl(imageUrl);
        this.repository.save(mob);

        return "File Uploaded Successfully";
    }

    public UrlResource downloadFile(String filename) {
       
        java.nio.file.Path file = rootLocation.resolve(filename);

        try {
           
            UrlResource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable())
                return resource;
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File does not exists");
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not read the file");
        }
    }

}