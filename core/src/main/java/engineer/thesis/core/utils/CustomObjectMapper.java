package engineer.thesis.core.utils;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomObjectMapper {

    private ModelMapper modelMapper;

    public CustomObjectMapper() {
        modelMapper = new ModelMapper();
    }

    public <OriginType, ReturnedType> ReturnedType convert(OriginType object, Class<ReturnedType> contentClass){
        return modelMapper.map(object, contentClass);
    }

}
