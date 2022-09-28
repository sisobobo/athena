#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.mapper;

import ${package}.dao.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDO getByName(String name);

    UserDO getById(Long id);

    Long insert(UserDO userDO);
}

