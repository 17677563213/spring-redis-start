package org.example.business.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.business.entity.User;
import org.example.business.repository.UserRepository;
import org.example.business.service.UserService;
import org.example.common.redis.util.RedisUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    
    private static final String USER_CACHE_KEY_PREFIX = "user:";
    
    @Override
    @Transactional
    public User createUser(User user) {
        if (existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new RuntimeException("User ID cannot be null");
        }
        clearUserCache(user.getId());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        clearUserCache(id);
    }

    @Override
    public User getUserById(Long id) {
        String cacheKey = USER_CACHE_KEY_PREFIX + id;
        User user = (User) redisUtil.get(cacheKey);
        if (user != null) {
            return user;
        }
        
        user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        redisUtil.set(cacheKey, user, 3600); // 缓存1小时
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    private void clearUserCache(Long userId) {
        String cacheKey = USER_CACHE_KEY_PREFIX + userId;
        redisUtil.delete(cacheKey);
    }
}
