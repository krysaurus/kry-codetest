package com.jlundho.kry.store;

import com.jlundho.kry.models.db.Service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceStore extends CrudRepository<Service, Long> {

    List<Service> findAll();
}
