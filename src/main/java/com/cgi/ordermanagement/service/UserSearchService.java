package com.cgi.ordermanagement.service;

import com.cgi.ordermanagement.model.dto.security.UserDTO;
import com.cgi.ordermanagement.model.security.User;
import com.cgi.ordermanagement.payload.request.UserSearchRequestDTO;
import com.cgi.ordermanagement.util.RestUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class UserSearchService {

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public Page<UserDTO> findAll(Pageable pageable, UserSearchRequestDTO dto) throws Exception {

        log.debug("Request to get  User");
        String normalSql = "select pd from User pd";
        String countSql = "select count(pd) as total from User pd";

        // TOTAL ITEM SQL
        String query = find(dto);
        Query qt = entityManager.createQuery(countSql + query);

        // NORMAL SQL
        String order = RestUtil.createOrder(pageable);

        if (!org.springframework.util.StringUtils.isEmpty(order)) {
            query += " ORDER BY " + order;
        }

        Query q = entityManager.createQuery(normalSql + query);

        Long total = (Long) qt.getSingleResult();

        RestUtil.createQuery(pageable, q);
        List<User> lists = q.getResultList();

        return new PageImpl<>(lists.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList()), pageable, total);
    }



    private String find(UserSearchRequestDTO dto) {
        String query = "";
        boolean andNeed = true;

        query += " where pd.userType = '0' ";

        if (dto.getId() != null) {
            if (andNeed) query += " and ";
            query += " pd.id = " + dto.getId();
        }

        if (dto.getEmail() != null) {
            if (andNeed) query += " and ";
            query += " (pd.email like '%" + dto.getEmail() + "%'  " + ")";
        }
        return query;

    }


}
