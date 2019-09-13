package com.cgi.ordermanagement.service;

import com.cgi.ordermanagement.exception.AppException;
import com.cgi.ordermanagement.model.dto.security.UserDTO;
import com.cgi.ordermanagement.model.dto.system.ErrorDTO;
import com.cgi.ordermanagement.model.enums.RoleName;
import com.cgi.ordermanagement.model.security.Role;
import com.cgi.ordermanagement.model.security.User;
import com.cgi.ordermanagement.model.security.UserRole;
import com.cgi.ordermanagement.model.security.UserType;
import com.cgi.ordermanagement.payload.request.SignUpRequestDTO;
import com.cgi.ordermanagement.repository.security.RoleRepository;
import com.cgi.ordermanagement.repository.security.UserRepository;
import com.cgi.ordermanagement.repository.security.UserRoleRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO registerUser(SignUpRequestDTO signUpRequest) throws Exception {
        if (userRepository.existsByEmail(signUpRequest.getEmail()))
            throw new AppException(ErrorDTO.ResponseCodes.ALREADY_EXIST_EMAIL);

        User user = new User();

        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());

        user.setMobile(signUpRequest.getMobile());
        userRepository.save(user);
        addRole(user, user, RoleName.ROLE_USER);

        user.getUserRoleList().stream().forEach(ur -> ur.setUserId(user.getId()));
        userRoleRepository.saveAll(user.getUserRoleList());
        UserDTO merchantDTO = modelMapper.map(user, UserDTO.class);
        return merchantDTO;
    }

    private void addRole(User user, User result, RoleName roleName) throws Exception {

        Optional<Role> optRole = roleRepository.findByName(roleName);

        if (!optRole.isPresent()) {
            throw new AppException(ErrorDTO.ResponseCodes.ROLE_NOT_FOUND);
        }
        Role merchantAdminRole = optRole.get();
        UserRole userRole = new UserRole();
        userRole.setRoleId(merchantAdminRole.getId());
        userRole.setUserId(user.getId());
        result.setUserRoleList(Collections.singletonList(userRole));
    }

    @Transactional
    public UserDTO save(UserDTO dto) throws Exception {

        log.debug("Request to save User : {}", dto);
        User entity = new User();
        if (dto.getId() != null) {
            entity = userRepository.findByIdT(dto.getId());
            if (!StringUtils.isEmpty(dto.getEmail()))
                entity.setEmail(dto.getEmail());
            if (!StringUtils.isEmpty(dto.getPassword()))
                entity.setPassword(passwordEncoder.encode(dto.getPassword()));
            if (dto.getUserType() != null)
                entity.setUserType(dto.getUserType());
        } else {
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
            entity = modelMapper.map(dto, User.class);
        }


        User result = userRepository.save(entity);
        return modelMapper.map(result, UserDTO.class);
    }

    public Page<UserDTO> findAll(Pageable pageable) {
        log.debug(pageable.toString());
        return userRepository.findAll(pageable).map(acquire -> modelMapper.map(acquire, UserDTO.class));
    }

    public Page<UserDTO> findByUserType(Pageable pageable, UserType userType) {
        log.debug(pageable.toString());
        return userRepository.findByUserType(pageable, userType).map(acquire -> modelMapper.map(acquire, UserDTO.class));
    }


    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(acquire -> modelMapper.map(acquire, UserDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        userRepository.deleteById(id);
    }


    public UserDTO findById(Long id) throws AppException {

        return modelMapper.map(userRepository.findByIdT(id), UserDTO.class);
    }

    public UserDTO findByEmail(String principal) throws NotFoundException {

        log.debug("Request to get User : {}", principal);
        Optional<User> userOptional = userRepository.findByEmail(principal);

        if (!userOptional.isPresent()) {
            throw new NotFoundException("user not found with id :" + principal);
        } else {
            UserDTO dto = modelMapper.map(userOptional.get(), UserDTO.class);
            return dto;
        }
    }

}
