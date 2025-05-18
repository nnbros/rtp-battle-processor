package com.github.nnbros.rtp.pve.jpa.monster;

import com.github.nnbros.rtp.pve.monster.MonsterDictionary;
import org.mapstruct.Mapper;

@Mapper
public interface MonsterDictionaryMapper {

	MonsterDictionary toClassDictionary(MonsterDictionaryEntity source);

	MonsterDictionaryEntity toClassDictionaryEntity(MonsterDictionary source);
}
