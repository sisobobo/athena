#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\' )
package ${package}.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import io.github.sisobobo.athena.dto.SingleResponse;

import ${package}.dao.dataobject.UserDO;
import ${package}.dao.mapper.UserMapper;
import ${package}.api.UserService;
import ${package}.api.dto.UserDTO;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private static final BeanCopier copier = BeanCopier.create(UserDTO.class, UserDO.class, false);

    public SingleResponse<String> getUserName(Long id) {
        UserDO userDO = userMapper.getById(id);
        String res = userDO != null ? userDO.getName() : null;
        return SingleResponse.of(res);
    }

    public SingleResponse<UserDTO> addUser(UserDTO user) {
        UserDO userDO = new UserDO();
        copier.copy(user, userDO, null);

        Long id = userMapper.insert(userDO);
        user.setId(id);
        return SingleResponse.of(user);
    }
}
