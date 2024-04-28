package com.remessas.remessas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remessas.remessas.entity.Remessa;

public interface RemessasRepository extends JpaRepository<Remessa, Long> {

}
