package me.alanx.ecomer.web.dto.admin.merchant;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class StoreLanding {
	
	@Valid
	private List<StoreLandingDescription> descriptions = new ArrayList<StoreLandingDescription>();

	public void setDescriptions(List<StoreLandingDescription> descriptions) {
		this.descriptions = descriptions;
	}

	public List<StoreLandingDescription> getDescriptions() {
		return descriptions;
	}

}
