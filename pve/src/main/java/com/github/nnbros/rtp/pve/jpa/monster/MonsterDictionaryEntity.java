package com.github.nnbros.rtp.pve.jpa.monster;

import com.github.nnbros.rtp.common.api.dto.character.Archetype;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "monster_dictionary", schema = "pve")
public class MonsterDictionaryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Integer id;

	@Column(name = "name", nullable = false, unique = true, length = 32, insertable = false, updatable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, unique = true, length = 32, insertable = false, updatable = false)
	private Archetype type;

	@Column(name = "base_hp", nullable = false, insertable = false, updatable = false)
	private Integer baseHp;

	@Column(name = "base_atk", nullable = false, insertable = false, updatable = false)
	private Integer baseAtk;

	@Column(name = "base_def", nullable = false, insertable = false, updatable = false)
	private Integer baseDef;

	@Column(name = "army_name", nullable = false, unique = true, length = 32, insertable = false, updatable = false)
	private String armyName;

	@Enumerated(EnumType.STRING)
	@Column(name = "army_type", nullable = false, insertable = false, updatable = false)
	private Archetype armyType;

	@Column(name = "army_base_quantity", nullable = false, insertable = false, updatable = false)
	private Integer armyBaseQuantity;

	@Column(name = "army_base_hp", nullable = false, insertable = false, updatable = false)
	private Integer armyBaseHp;

	@Column(name = "army_base_atk", nullable = false, insertable = false, updatable = false)
	private Integer armyBaseAtk;

	@Column(name = "army_base_def", nullable = false, insertable = false, updatable = false)
	private Integer armyBaseDef;

	@Column(name = "army_tier", nullable = false, insertable = false, updatable = false)
	private Integer armyTier;
}
