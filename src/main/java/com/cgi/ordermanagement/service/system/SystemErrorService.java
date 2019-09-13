package com.cgi.ordermanagement.service.system;

import com.cgi.ordermanagement.model.system.SystemError;
import com.cgi.ordermanagement.model.dto.system.SystemErrorDTO;
import com.cgi.ordermanagement.repository.system.SystemErrorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SystemErrorService {

    @Autowired
    private SystemErrorRepository systemErrorRepository;


    @Transactional
    public void save(SystemErrorDTO systemErrorDTO) {

        SystemError systemError = SystemErrorDTO.toEntity(systemErrorDTO);
       // String currentLogin = SecurityUtils.getCurrentLogin ();
        systemErrorRepository.save(systemError);
    }
}

