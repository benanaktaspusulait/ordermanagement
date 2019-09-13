package com.cgi.ordermanagement.controller;

import com.cgi.ordermanagement.exception.AppException;
import com.cgi.ordermanagement.service.UserService;
import com.cgi.ordermanagement.model.dto.security.UserDTO;
import com.cgi.ordermanagement.payload.request.UserSearchRequestDTO;
import com.cgi.ordermanagement.model.security.UserType;
import com.cgi.ordermanagement.service.UserSearchService;
import com.cgi.ordermanagement.util.Rights;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/admin/users")
@Api(tags = "admin/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserSearchService userSearchService;

    @ResponseBody
    @ApiOperation(value = "Search a user with an ID", response = UserDTO.class)
    @GetMapping(value = "/{id}")
    @RolesAllowed(value = {Rights.ADMIN})
    public ResponseEntity<?> get(@PathVariable Long id) throws AppException {

        log.debug("REST funds to get User : {}", id);

        UserDTO userDTO = userService.findById(id);
        return Optional.ofNullable(userDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @ResponseBody
    @ApiOperation(value = "Search a user with an ID", response = UserDTO.class)
    @GetMapping(value = "/byEmail/{usernameOrEmail}")
    @RolesAllowed(value = {Rights.ADMIN})
    public ResponseEntity<?> getByName(@PathVariable String usernameOrEmail) throws AppException,NotFoundException {

        log.debug("REST funds to get User : {}", usernameOrEmail);

        UserDTO userDTO = userService.findByEmail(usernameOrEmail);
        return Optional.ofNullable(userDTO)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @ApiOperation(value = "Add a user")
    @PostMapping
    @RolesAllowed(value = {Rights.ADMIN})
    public ResponseEntity<?> create(@RequestBody @Valid UserDTO dto) throws AppException,Exception {

        log.debug("Create user", dto);
        return new ResponseEntity<>(userService.save(dto), HttpStatus.CREATED);

    }


    @ApiOperation(value = "Update a user")
    @PatchMapping
    @RolesAllowed(value = {Rights.ADMIN})
    public ResponseEntity<?> update(@RequestBody @Valid UserDTO userDTO) throws AppException,Exception {
        log.debug("REST funds to update basket : {}", userDTO);
        if (userDTO.getId() == null) {
            return create(userDTO);
        }
        return new ResponseEntity<>(userService.save(userDTO), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    @RolesAllowed(value = {Rights.ADMIN})
    public ResponseEntity<?> delete(@PathVariable Long id)throws AppException {

        log.debug("REST funds to delete product : {}", id);
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @RolesAllowed(value = {Rights.ADMIN})
    @GetMapping(value = "/list")
    public ResponseEntity<?> getUserList()throws AppException {

        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @RolesAllowed(value = {Rights.ADMIN})
    @GetMapping
    public ResponseEntity<?> getUsers(Pageable pageable)throws AppException {
        return new ResponseEntity<>(userService.findByUserType(pageable, UserType.ADMIN), HttpStatus.OK);
    }
    @ApiOperation(value = "search transactions", produces = "application/json")
    @GetMapping("/search")
    @RolesAllowed(value = {Rights.ADMIN})
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable,
                                                        @RequestParam(value = "id", required = false) Long id,
                                                        @RequestParam(value = "email", required = false) String email) throws Exception {
        log.debug("REST request to get getAll users");
        return new ResponseEntity<>(userSearchService.findAll(pageable, new UserSearchRequestDTO(id, email)), HttpStatus.OK);

    }

}
