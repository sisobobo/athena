#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.model;

import lombok.Data;


@Data
public class UserModel {

    private Long id;

    private String name;

    private Integer age;

}