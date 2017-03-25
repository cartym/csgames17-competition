package com.mirego.cschat.controller;

/**
 * Created by Carty on 2017-03-25.
 */

import com.mirego.cschat.models.request.LoginRequest;
import com.mirego.cschat.models.User;
import com.mirego.cschat.models.request.RegisterRequest;
import com.mirego.cschat.services.CSChatService;
import com.mirego.cschat.services.StorageService;

import io.reactivex.Flowable;

public class RegisterController {

    private final CSChatService chatService;
    private final StorageService storageService;

    public RegisterController(CSChatService chatService, StorageService storageService) {
        this.chatService = chatService;
        this.storageService = storageService;
    }

    public Flowable<User> register(String username, String password, String url) {
        return chatService.register(new RegisterRequest(username, password, url));
    }

    public void logout() {
        storageService.clearUserId();
    }

    public void saveUserId(String userId) {
        storageService.storeUserId(userId);
    }
}
