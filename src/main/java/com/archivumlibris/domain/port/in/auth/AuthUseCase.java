package com.archivumlibris.domain.port.in.auth;

import com.archivumlibris.dto.request.auth.AuthRequestDTO;
import com.archivumlibris.dto.request.user.UserRequestDTO;

public interface AuthUseCase {

    public String register(UserRequestDTO userRequestDTO);

    public String login(AuthRequestDTO authRequestDTO);

}
