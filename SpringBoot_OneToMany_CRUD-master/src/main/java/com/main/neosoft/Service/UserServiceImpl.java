package com.main.neosoft.Service;

import com.main.neosoft.entity.UserDetails;
import com.main.neosoft.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired
    UserDetailsRepository userDetailsRepository;


    public UserDetails saveData(UserDetails userDetails) {

        return userDetailsRepository.save(userDetails);
    }


}
