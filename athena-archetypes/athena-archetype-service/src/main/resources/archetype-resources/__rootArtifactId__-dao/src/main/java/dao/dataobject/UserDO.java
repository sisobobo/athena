#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.dataobject;

import lombok.Data;

@Data
public class UserDO {

    private Long id;

    private String name;

    private Integer age;

}
