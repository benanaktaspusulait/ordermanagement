package com.cgi.ordermanagement.service;

import com.cgi.ordermanagement.model.ScreenMessage;
import com.cgi.ordermanagement.model.dto.ScreenMessageDTO;
import com.cgi.ordermanagement.model.enums.Language;
import com.cgi.ordermanagement.repository.ScreenMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScreenMessageService {


    @Autowired
    private ScreenMessageRepository screenMessageRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ScreenMessageDTO save(ScreenMessageDTO dto) throws Exception{

        log.debug("Request to save ScreenMessage : {}", dto);
        ScreenMessage entity = null;
        if (dto.getId() != null) {
            entity = screenMessageRepository.findByIdT(dto.getId());
        }
        entity = modelMapper.map(dto, ScreenMessage.class);
        ScreenMessage result = screenMessageRepository.save(entity);
        return modelMapper.map(result, ScreenMessageDTO.class);
    }

    public List<ScreenMessageDTO> findByLanguage(Language language) {
        return screenMessageRepository.findByLanguage(language).stream().map(message -> modelMapper.map(message, ScreenMessageDTO.class)).collect(Collectors.toList());

    }

    public ScreenMessageDTO findByLanguageAndCode(Language language, String code) {

        log.debug("Request to get ScreenMessage : {}, {}", language,code);
        Optional<ScreenMessage> screenMessageOptional = screenMessageRepository.findByLanguageAndCode(language, code);

        if (!Optionals.isAnyPresent(screenMessageOptional)) {
           return new ScreenMessageDTO("Not defined");
        } else {
            ScreenMessageDTO dto = modelMapper.map(screenMessageOptional.get(), ScreenMessageDTO.class);
            return dto;
        }


    }
}
