package engineer.thesis.service;

public interface IBasicService <Type, TypeDTO> {
    TypeDTO mapToDTO(Type data);
    Type mapFromDTO(TypeDTO dto);
}
