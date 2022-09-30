#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package}.api.dto;

import lombok.Data;
import io.github.sisobobo.athena.dto.DTO;

@Data
public class UserDTO extends DTO {

    private Long id;

    private String name;

    private Integer age;

}