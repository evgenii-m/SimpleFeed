/**
 * 
 */
package org.push.simplefeed.model.service;

import java.util.ArrayList;
import java.util.List;

import org.push.simplefeed.model.entity.RoleEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.repository.RoleRepository;
import org.push.simplefeed.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author push
 *
 */
@Service
@Transactional
public class UserService implements IUserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    
    @Override
    public void save(UserEntity user) {
        user.setEnabled(true);
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(roleRepository.findByRole("ROLE_USER"));
        user.setRoles(roles);
        userRepository.save(user);
    }
    
    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
    
    
    
    @Override
    public UserEntity findOne(Long id) {
        return userRepository.findOne(id);
    }
    
    @Override
    public UserEntity findOne(String email) {
        return userRepository.findByEmail(email);
    }
    
}