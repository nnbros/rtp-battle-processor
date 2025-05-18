package com.github.nnbros.rtp.pve.repository;

import com.github.nnbros.rtp.pve.jpa.monster.MonsterDictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterDictionaryRepository extends JpaRepository<MonsterDictionaryEntity, Integer> {

}
