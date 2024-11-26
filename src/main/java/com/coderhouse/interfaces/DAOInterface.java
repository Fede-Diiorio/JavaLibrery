package com.coderhouse.interfaces;

import java.util.List;

public interface DAOInterface<T, resDTO> {

    List<resDTO> getAll();

    resDTO getById(Long id);
    
    resDTO save(T object);

    resDTO update(Long id, T object) throws Exception;

    void delete(Long id);
    
}
