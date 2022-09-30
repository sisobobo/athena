#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api;

import io.github.sisobobo.athena.dto.SingleResponse;
import ${package}.api.dto.UserDTO;


public interface UserService {

    SingleResponse<String> getUserName(Long id);

    SingleResponse<UserDTO> addUser(UserDTO user);
}
