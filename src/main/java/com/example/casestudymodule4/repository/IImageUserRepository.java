package com.example.casestudymodule4.repository;

import com.example.casestudymodule4.model.entity.ImageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageUserRepository extends JpaRepository<ImageUser,Long> {
    @Query("select img from ImageUser img where img.status=1 and img.userId.id=:idU")
    public ImageUser findByUserIdAndStatus(Long idU);
}
