package com.becoder.service;

import com.becoder.model.UserDtls;
import com.becoder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//it is component so mark as service otherwise u got error
// post method work-register-submit-create user
@Service
public class UserServiceImpl implements UserService {

    //without repository we can't use that's why we use Autowired and this line
    @Autowired
    private UserRepository userRepo;

    //Password save as hash form so we have to encrypt so user can login after register
    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @Override
    public UserDtls createUser(UserDtls user) {

        user.setPassword(passwordEncode.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        //user registration happen-end here
//user passed on bracket same pass as entity there
        return userRepo.save(user);
    }

    //override check email from userservice and passed user repository code existByEmail etc here
    @Override
    public boolean checkEmail(String email) {

        return userRepo.existsByEmail(email);
    }

}
