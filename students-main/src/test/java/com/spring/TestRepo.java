package com.spring;

import com.spring.entities.Students;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepo extends JpaRepository<Students,Integer> {

}
