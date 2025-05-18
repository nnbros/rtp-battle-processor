package com.github.nnbros.rtp.pve.telegram.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParameterService {
//	private final Localization localization;
//
//	public Map<String, String> buildCharacterBaseParameters(ActionContext actionContext, DetailedCharacterView character, Parameter... additionalParams) {
//		List<Parameter> parameters = new ArrayList<>();
//		parameters.add(Parameter.of(DefaultParameter.CHAT_ID, actionContext.userId()));
//		parameters.add(Parameter.of(CharacterParameter.CHAR_NAME, character.getName()));
//		parameters.addAll(buildClassParameters(character.getActiveClass()));
//		parameters.addAll(buildArmyParameters(character.getActiveArmy()));
//		parameters.addAll(Arrays.asList(additionalParams));
//		return Parameters.buildParameters(parameters);
//	}
//
//	private Collection<Parameter> buildClassParameters(ActiveCharacterClass activeCharacterClass) {
//		Localization.Clazz clazz = localization.getClasses()
//				.get(activeCharacterClass.name());
//		return Set.of(
//				Parameter.of(CharacterParameter.CHAR_CLASS, clazz.getName()),
//				Parameter.of(CharacterParameter.CHAR_CLASS_DESCRIPTION, clazz.getDescription())
//		);
//	}
//
//	private Collection<Parameter> buildArmyParameters(ActiveCharacterArmy activeCharacterArmy) {
//		Localization.Army army = localization.getArmies()
//				.get(activeCharacterArmy.name());
//		return Set.of(
//				Parameter.of(CharacterParameter.CHAR_ARMY, army.getName()),
//				Parameter.of(CharacterParameter.CHAR_ARMY_TYPE, army.getType()),
//				Parameter.of(CharacterParameter.CHAR_ARMY_HP, activeCharacterArmy.baseHp()),
//				Parameter.of(CharacterParameter.CHAR_ARMY_COUNT, activeCharacterArmy.baseQuantity()),
//				Parameter.of(CharacterParameter.CHAR_ARMY_ATK, activeCharacterArmy.baseAtk()),
//				Parameter.of(CharacterParameter.CHAR_ARMY_DEF, activeCharacterArmy.baseDef())
//		);
//	}
}
