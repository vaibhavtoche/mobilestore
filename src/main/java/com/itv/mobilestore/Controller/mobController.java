package com.itv.mobilestore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itv.mobilestore.Services.MobServices;
import com.itv.mobilestore.dtos.RegistorMobDto;
import com.itv.mobilestore.entities.mob;

//import jakarta.annotation.Resource;
import jakarta.validation.Valid;
@CrossOrigin(origins = {"*"},maxAge=4800, allowCredentials = "false")

@RestController
public class mobController {
    @Autowired
    private MobServices MobServices;

    // adding data
    @PostMapping("/mob")
    public ResponseEntity<?> registerMob(@RequestBody @Valid RegistorMobDto registerMobDto) {
        return new ResponseEntity<>(this.MobServices.registerMob(registerMobDto), HttpStatus.CREATED);
    }

    // retriving data
    @GetMapping("/mob")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(this.MobServices.getAll());
    }

    // searching data
    @GetMapping("/mob/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        mob mobExits = this.MobServices.getById(id);
        if (mobExits != null) {
            return new ResponseEntity<>(mobExits, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("mob not found", HttpStatus.NOT_FOUND);
        }
    }

    // 
    @DeleteMapping("/mob/{id}")
    public ResponseEntity<?> deletemob(@PathVariable Integer id) {
        mob mobExits = this.MobServices.getById(id);
        if (mobExits != null) {
            this.MobServices.deletemob(id);
            return new ResponseEntity<>("mob deleted sucessfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("mob not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/mob/{id}")
    public ResponseEntity<?> updatemob(@PathVariable Integer id) {
        mob mobExits = this.MobServices.getById(id);
        if (mobExits != null) {
            this.MobServices.deletemob(id);
            return new ResponseEntity<>("mob update sucessfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("mob not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/mob/searchbymobname")
    public ResponseEntity<?> findBymobname(@RequestParam("mobname") String mobname) {
        List<mob> mobiels = this.MobServices.findBymobname(mobname);
        if (mobiels.isEmpty()) {
            return new ResponseEntity<>("no mob exist with this  name", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(this.MobServices.findBymobname(mobname), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/mob/searchbycolor")
    public ResponseEntity<?> findBycolorContaining(@RequestParam String color) {
        List<mob> mobiels = this.MobServices.findBycolor(color);
        if (mobiels.isEmpty()) {
            return new ResponseEntity<>("no mob exist with this  color", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mobiels, HttpStatus.NOT_FOUND);
}
}
@PutMapping("/mobs/{id}/upload")
    public ResponseEntity<?> upload(@PathVariable Integer id, 
                                @RequestParam MultipartFile file){
        System.out.println("File Upload");
        return ResponseEntity.ok(this.MobServices.uploadFile(id, file));
    }

    /**
     * @param filename
     * @return
     */
    @GetMapping("/mobs/download/{filename}")
    public ResponseEntity<?> download(@PathVariable String filename){
        UrlResource resource = this.MobServices.downloadFile(filename);
        return ResponseEntity.ok()
                             .header( "attachment; filename=\"" + filename + "\"")
                             .body(resource);
    }
}