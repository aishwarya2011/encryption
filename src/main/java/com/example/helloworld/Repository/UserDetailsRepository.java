package com.example.helloworld.Repository;


import com.example.helloworld.Entity.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository   extends CrudRepository<UserDetails, Long> {
}
