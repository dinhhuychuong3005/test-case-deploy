package com.example.casestudymodule4.service.imageUse;

import com.example.casestudymodule4.model.entity.ImageUser;
import com.example.casestudymodule4.repository.IImageUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageUseService implements IImageUseService {
    @Autowired
    private IImageUserRepository iImageUserRepository;

    @Override
    public Iterable<ImageUser> findAll() {
        return iImageUserRepository.findAll();
    }

    @Override
    public Optional<ImageUser> findById(Long id) {
        return iImageUserRepository.findById(id);
    }

    @Override
    public ImageUser save(ImageUser imageUser) {
        return iImageUserRepository.save(imageUser);
    }

    @Override
    public void remove(Long id) {
        iImageUserRepository.deleteById(id);
    }

    @Override
    public ImageUser findByUserIdAndStatus(Long idU) {
        return iImageUserRepository.findByUserIdAndStatus(idU);
    }
}
