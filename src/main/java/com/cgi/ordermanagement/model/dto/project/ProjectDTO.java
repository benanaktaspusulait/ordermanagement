package com.cgi.ordermanagement.model.dto.project;

import com.cgi.ordermanagement.model.dto.AuditBaseDTO;
import com.cgi.ordermanagement.model.project.Dealer;
import com.cgi.ordermanagement.model.project.Project;
import lombok.Data;

@Data
public class ProjectDTO extends AuditBaseDTO {

    private Long id;
    private String name;
    private Dealer dealer;
    private Long dealerId;

    public static Project toEntity(ProjectDTO dto, Project entity) {

        entity = entity == null ? new Project() : entity;
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDealerId(dto.getDealerId());
        return entity;
    }

}
