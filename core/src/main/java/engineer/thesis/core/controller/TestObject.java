package engineer.thesis.core.controller;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestObject {

    private Long id;

    private Map<String, String> mapa;


}
