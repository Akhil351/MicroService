package org.Akhil.common.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    @Autowired
    private ModelMapper modelMapper;
    public <S,T> T convertToDto(S source,Class<T> destinationClass){
        return modelMapper.map(source,destinationClass);
    }
}
