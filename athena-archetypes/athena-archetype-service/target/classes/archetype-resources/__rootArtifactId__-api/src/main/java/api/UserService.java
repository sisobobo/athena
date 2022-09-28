#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api;

import ${package}.api.model.UserModel;

public interface UserService {

    String getUserName(Long id);

    UserModel addUser(UserModel user);
}
