/*
 * Copyright (c) 2016 Felipe Guerço Oliveira.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.spotify.helios.agent;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.HostConfig;
import com.spotify.docker.client.messages.ImageInfo;
import com.spotify.docker.client.messages.LogConfig;
import com.spotify.helios.common.descriptors.LoggingConfiguration;
import com.spotify.helios.common.descriptors.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides options for set the log driver and options for containers
 */
public class LogConfigContainerDecorator implements ContainerDecorator {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void decorateHostConfig(Job job, Optional<String> dockerVersion,
                                 HostConfig.Builder hostConfig) {
    final LoggingConfiguration logging = job.getLogging();

    if (logging != Job.EMPTY_LOGGING) {
      final ImmutableMap.Builder<String, String> logOpts = ImmutableMap.builder();
      logOpts.putAll(logging.getOptions());
      hostConfig.logConfig(LogConfig.create(job.getLogging().getDriver(), logOpts.build()));
    }
  }

  @Override
  public void decorateContainerConfig(Job job, ImageInfo imageInfo,
                                      Optional<String> dockerVersion,
                                      ContainerConfig.Builder containerConfig) {
    // nothing do do here
  }
}
