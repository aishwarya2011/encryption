package com.example.helloworld.Service;

import com.example.helloworld.Entity.UserDetails;
import com.example.helloworld.Repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails saveUserDetails(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }

    @Override
    public List<UserDetails> fetchUserDetailsList() {
        return (List<UserDetails>)
                userDetailsRepository.findAll();
    }

    @Override
    public UserDetails updateUserDetails(UserDetails userDetails, Long userId) {
        UserDetails userDb
                = userDetailsRepository.findById(userId)
                .get();

        if (Objects.nonNull(userDetails.getUserName())
                && !"".equalsIgnoreCase(
                userDetails.getUserName())) {
            userDb.setUserName(
                    userDetails.getUserName());
        }

        if (Objects.nonNull(userDetails.getUserPassword())
                && !"".equalsIgnoreCase(
                userDetails.getUserPassword())) {
            userDb.setUserName(
                    userDetails.getUserPassword());
        }
        return userDetailsRepository.save(userDb);
    }



    @Override
    public void deleteUserDetailsById(Long userId) {
        userDetailsRepository.deleteById(userId);
    }
}
