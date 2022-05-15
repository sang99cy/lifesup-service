package me.sunrise.service.impl;

import me.sunrise.dto.BaseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BaseService {
    @Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    private ActionAuditRepository actionAuditRepository;

    protected <T, E> T map(E entity, Class<T> clazz) {
        return modelMapper.map(entity, clazz);
    }

    protected <T, E extends BaseDTO> T map(E dto, Class<T> clazz) {
        return modelMapper.map(dto, clazz);
    }

    protected <T, E> void mapData(T source, E destination) {
        modelMapper.map(source, destination);
    }

    protected <T, E> List<T> mapList(List<E> inputData, Class<T> clazz) {
        return CollectionUtils.isEmpty(inputData) ?
                Collections.emptyList() :
                inputData.stream()
                        .map(i -> modelMapper.map(i, clazz))
                        .collect(Collectors.toList());
    }

    protected String getCurrentUser() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
