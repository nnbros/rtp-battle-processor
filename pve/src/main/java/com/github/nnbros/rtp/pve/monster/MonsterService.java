package com.github.nnbros.rtp.pve.monster;

import com.github.nnbros.rtp.pve.jpa.monster.MonsterDictionaryMapper;
import com.github.nnbros.rtp.pve.repository.MonsterDictionaryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MonsterService {
	private final MonsterDictionaryRepository monsterRepository;
	private final MonsterDictionaryMapper monsterMapper;
	private List<MonsterDictionary> monsterCache;

	@PostConstruct
	private void init() {
		monsterCache = monsterRepository.findAll()
				.stream()
				.map(monsterMapper::toClassDictionary)
				.toList();
	}

	public MonsterDictionary getRandomMonster() {
		int monsterIndex = ThreadLocalRandom.current().nextInt(0, monsterCache.size());
		return monsterCache.get(monsterIndex);
	}
}
