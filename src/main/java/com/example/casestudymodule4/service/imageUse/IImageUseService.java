package com.example.casestudymodule4.service.imageUse;

import com.example.casestudymodule4.model.entity.ImageUser;
import com.example.casestudymodule4.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;

public interface IImageUseService extends IGeneralService<ImageUser> {
    @Query("select img from ImageUser img where img.status=1 and img.userId.id=:idU")
    public ImageUser findByUserIdAndStatus(Long idU);
}
