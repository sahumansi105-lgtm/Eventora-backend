package com.pronight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pronight.entity.ContactEntity;

public interface ContactRepo
extends JpaRepository<ContactEntity,Long>{

}