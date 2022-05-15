package me.sunrise.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        mapper.typeMap(ActionAuditDto.class, ActionAuditEntity.class)
//                .addMappings(mapping -> {
//                    mapping.map(ActionAuditDto::getUserIP, ActionAuditEntity::setIp);
//                });
        return mapper;
    }
}
