package com.cgi.ordermanagement.service;

import com.cgi.ordermanagement.model.dto.project.CreateProjectDTO;
import com.cgi.ordermanagement.model.dto.project.ProjectDTO;
import com.cgi.ordermanagement.model.project.Project;
import com.cgi.ordermanagement.repository.order.ProjectRepository;
import com.cgi.ordermanagement.service.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class ProjectService extends BaseService<ProjectDTO, ProjectRepository> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProjectRepository projectRepository;

    /**
     *  create a new project
     * @param dto
     * @return
     */
    @Transactional
    public ProjectDTO save(CreateProjectDTO dto) {

        log.debug("Request to save User : {}", dto);
        Project entity = modelMapper.map(dto, Project.class);
        Project result = projectRepository.save(entity);
        return modelMapper.map(result, ProjectDTO.class);
    }

    /**
     * update an existing project
     * @param dto
     * @return
     */
    @Transactional
    public ProjectDTO update(ProjectDTO dto) {

        log.debug("Request to update User : {}", dto);
        Project entity;
        if (dto.getId() != null) {
            entity = projectRepository.findById(dto.getId()).get();
            entity = ProjectDTO.toEntity(dto, entity);
        } else {
            entity = modelMapper.map(dto, Project.class);
        }
        Project result = projectRepository.save(entity);
        return modelMapper.map(result, ProjectDTO.class);
    }

    /**
     * delete an project
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.deleteById(id);
    }

    /**
     * to get all projects pageable
     * @param pageable
     * @return
     */
    public Page<ProjectDTO> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable).map(project -> modelMapper.map(project, ProjectDTO.class));
    }


}
