package engineer.thesis.core.service;

public interface IBasicService <Type, TypeDTO> {
    TypeDTO mapToDTO(Type data);
    Type mapFromDTO(TypeDTO dto);
}
