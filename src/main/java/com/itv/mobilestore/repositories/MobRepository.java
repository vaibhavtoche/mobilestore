package com.itv.mobilestore.repositories;


import java.util.List;

//import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.itv.mobilestore.entities.mob;
@CrossOrigin(origins = {"*"},maxAge=4800, allowCredentials = "false")

public interface MobRepository extends JpaRepository<mob, Integer> {

    List<mob> findBymobname(String mobname);

    List<mob> findBycolorContaining(String color);
}