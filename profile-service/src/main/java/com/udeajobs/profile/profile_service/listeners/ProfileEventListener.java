package com.udeajobs.profile.profile_service.listeners;

import com.udeajobs.profile.profile_service.config.RabbitMQConfig;
import com.udeajobs.profile.profile_service.events.CuentaVerificadaEvent;
import com.udeajobs.profile.profile_service.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileEventListener {
    private final ProfileService profileService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void onProfileCreated(CuentaVerificadaEvent event) {
        profileService.createBaseUser(event);
    }

}
