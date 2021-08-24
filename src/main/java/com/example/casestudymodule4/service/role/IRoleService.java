package com.example.casestudymodule4.service.role;


import com.example.casestudymodule4.model.entity.Role;
import com.example.casestudymodule4.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);
}
