/*
 interface service
 */
package com.alkemy.ong.service;

import com.alkemy.ong.models.request.UserRequest;
import com.alkemy.ong.models.response.UserResponse;

public interface UserService {

        void delete(Long id);
        public UserResponse update(Long id, UserRequest request);

}
