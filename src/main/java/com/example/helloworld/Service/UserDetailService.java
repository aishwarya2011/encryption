package com.example.helloworld.Service;


import com.example.helloworld.Entity.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.List;


public interface UserDetailService {
    UserDetails saveUserDetails(UserDetails userDetails);

    // Read operation
    List<UserDetails> fetchUserDetailsList();

    // Update operation
    UserDetails updateUserDetails(UserDetails department,
                                Long departmentId);

    // Delete operation
    void deleteUserDetailsById(Long departmentId);

}
