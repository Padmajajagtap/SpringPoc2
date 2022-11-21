package com.main.neosoft.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.main.neosoft.entity.UserDetails;


@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{

	Boolean existsByEmail (String email);

	@Query("SELECT p FROM UserDetails p WHERE CONCAT(p.firstName, ' ', p.lastName, ' ', p.email) LIKE %?1%") 
	List<UserDetails> searchUserDetails(String query);
}
