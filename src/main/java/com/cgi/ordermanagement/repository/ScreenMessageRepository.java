package com.cgi.ordermanagement.repository;

import com.cgi.ordermanagement.model.ScreenMessage;
import com.cgi.ordermanagement.model.enums.Language;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface ScreenMessageRepository extends AuditBaseRepository<ScreenMessage, Long> {

    List<ScreenMessage> findByLanguage(Language language);

    Optional<ScreenMessage> findByLanguageAndCode(Language language, String code);

}
