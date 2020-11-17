package com.globallypaid.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Config {
  private String apiKey;
  private String appIdKey;
  private String sharedSecretApiKey;
  private String sandbox;
}
