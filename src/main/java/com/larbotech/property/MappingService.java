package com.larbotech.property;

import com.larbotech.property.config.ServerProperties;
import com.larbotech.property.config.TeamsProjectConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappingService {
  private TeamsProjectConfig teamsProjectConfig;
  private ServerProperties serverProperties;

  @Autowired
  public MappingService(TeamsProjectConfig teamsProjectConfig, ServerProperties serverProperties) {
    this.teamsProjectConfig = teamsProjectConfig;
    this.serverProperties = serverProperties;
  }

}
