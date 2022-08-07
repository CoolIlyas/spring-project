package com.example.springproject.repositories;

import com.example.springproject.domain.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository
        extends CrudRepository<TacoOrder, Long> {
}
