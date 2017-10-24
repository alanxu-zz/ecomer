package me.alanx.ecomer.core.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.model.auth.SecurityQuestion;
import me.alanx.ecomer.core.repositories.security.SecurityQuestionRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;
import me.alanx.ecomer.core.services.security.SecurityQuestionService;

@Service
public class SecurityQuestionServiceImpl extends
SalesManagerEntityServiceImpl<Long, SecurityQuestion>  implements SecurityQuestionService{

	@Autowired
	public SecurityQuestionServiceImpl(SecurityQuestionRepository repository) {
		super(repository);
	}

}
