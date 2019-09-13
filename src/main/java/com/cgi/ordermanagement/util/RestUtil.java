package com.cgi.ordermanagement.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class RestUtil {

    public static String createOrder(Pageable pageable) {
        return org.springframework.util.StringUtils.collectionToCommaDelimitedString(StreamSupport
                .stream(pageable.getSort().spliterator(), false)
                .map(o -> o.getProperty() + " " + o.getDirection())
                .collect(Collectors.toList()));
    }

    public static void createQuery(Pageable pageable, Query q) {
        if (pageable != null) {
            int pn = pageable.getPageNumber() == 0 ? 0 : (pageable.getPageNumber());
            int ps = pageable.getPageSize();
            q.setFirstResult(pn * ps);
            q.setMaxResults(ps);

        }
    }

}
