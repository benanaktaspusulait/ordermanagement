package com.cgi.ordermanagement.resource.v1;

import com.cgi.ordermanagement.exception.AppException;
import com.cgi.ordermanagement.model.dto.project.CreateProjectDTO;
import com.cgi.ordermanagement.model.dto.project.ProjectDTO;
import com.cgi.ordermanagement.service.ProjectService;
import com.cgi.ordermanagement.util.Rights;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/projects")
@Api(tags = "projects")
public class ProjectResource {

    @Autowired
    private ProjectService projectService;

    @ApiOperation(value = "Create an project")
    @PostMapping
    @RolesAllowed(value = {Rights.ADMIN,Rights.PROJECT})
    public ResponseEntity<?> create(@RequestBody @Valid CreateProjectDTO dto) throws AppException {

        log.debug("REST funds to save ProjectDTO: {}", dto);
        return new ResponseEntity<>(projectService.save(dto), HttpStatus.CREATED);
    }

    @ResponseBody
    @ApiOperation(value = "Search a project with an ID", response = ProjectDTO.class)
    @GetMapping(value = "/{id}")
    @RolesAllowed(value = {Rights.ADMIN,Rights.PROJECT})
    public ResponseEntity<?> get(@PathVariable Long id) throws AppException {

        log.debug("REST funds to get Project : {}", id);

        ProjectDTO projectDTO = projectService.findById(id);
        return Optional.ofNullable(projectDTO)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @ApiOperation(value = "Update a project")
    @PatchMapping
    @RolesAllowed(value = {Rights.ADMIN,Rights.PROJECT})
    public ResponseEntity<?> update(@RequestBody @Valid ProjectDTO projectDTO) throws AppException {

        log.debug("REST funds to update basket : {}", projectDTO);
        return new ResponseEntity<>(projectService.update(projectDTO), HttpStatus.OK);
    }

    @Timed
    @ApiOperation(value = "Get all projects ", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = ProjectDTO.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Access is denied")

    })
    @GetMapping
    public ResponseEntity<?> findAllProjects(Pageable pageable) throws AppException {

        log.debug("REST request to get getAll countries");
        return new ResponseEntity<>(projectService.findAll(pageable), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    @RolesAllowed(value = {Rights.ADMIN,Rights.PROJECT})
    public ResponseEntity<?> delete(@PathVariable Long id) throws AppException {

        log.debug("REST funds to delete product : {}", id);
        projectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
