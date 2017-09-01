package engineer.thesis.core.service.Interface;

public interface IBasicService <Type, TypeDTO> {
    TypeDTO mapToDTO(Type data);
    Type mapFromDTO(TypeDTO dto);
}
