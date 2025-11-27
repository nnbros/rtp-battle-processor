package com.github.nnbros.rtp.pve.storyteller;

import com.github.nnbros.rtp.common.api.dto.character.DetailedCharacterWithSkillsView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "storyteller",
		url = "${spring.cloud.openfeign.client.config.storyteller.url}",
		path = "${spring.cloud.openfeign.client.config.storyteller.path}")
public interface StoryTellerClient {

	@GetMapping("/users/{userId}/characters")
	DetailedCharacterWithSkillsView getCharacter(@PathVariable Long userId);
}
