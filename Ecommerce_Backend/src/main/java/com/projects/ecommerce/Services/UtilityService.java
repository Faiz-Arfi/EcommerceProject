package com.projects.ecommerce.Services;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class UtilityService {

    public void validateSearchParamater(Pageable p, Set<String> validSortFields){
        if(p.getSort().isSorted()){
            p.getSort().forEach(sort -> {
                String field = sort.getProperty();
                if(!validSortFields.contains(field)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sorting field: " + field);
                }
            });
        }
    }
}
