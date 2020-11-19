package com.globallypaid.util;

import com.globallypaid.exception.InvalidRequestException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.HttpStatus;

/** Utility class for generating HMAC signature and HMAC header. */
public class HmacUtils {

  private HmacUtils() {
    throw new IllegalStateException("HmacUtils class");
  }

  /**
   * Generate HMAC signature from provided message and secret key with algorithm.
   *
   * @param msg message to sign with HMAC
   * @param secretKey secret key
   * @param algorithm algorithm used for signature, like SHA256
   * @return Base64 encoded HMAC signature
   * @throws InvalidRequestException In case there is a problem during HMAC generation.
   */
  private static String hmacDigest(String msg, String secretKey, String algorithm)
      throws InvalidRequestException {
    String digest;
    try {
      byte[] secretKeyByte = Base64.getDecoder().decode(secretKey);
      SecretKeySpec key = new SecretKeySpec(secretKeyByte, algorithm);
      Mac mac = Mac.getInstance(algorithm);
      mac.init(key);

      byte[] hashMsg = mac.doFinal(msg.getBytes(StandardCharsets.US_ASCII));
      digest = Base64.getEncoder().encodeToString(hashMsg);
    } catch (InvalidKeyException | NoSuchAlgorithmException e) {
      throw new InvalidRequestException(
          HttpStatus.SC_BAD_REQUEST, "Failed to calculate hmac!", null, e);
    }
    return digest;
  }

  /**
   * Create HMAC header.
   *
   * @param message message to sign with HMAC
   * @param sharedSecret shared secret key for HMAC signature
   * @param appId api id key
   * @param algorithm algorithm used for signature, like SHA256
   * @return HMAC signature for header
   * @throws InvalidRequestException In case there is a problem during HMAC generation.
   */
  public static String createHmacHeader(
      String message, String sharedSecret, String appId, String algorithm)
      throws InvalidRequestException {
    UUID guid = UUID.randomUUID();
    String hashInBase64 = hmacDigest(message, sharedSecret, algorithm);
    String hmacHeader =
        appId.concat(":POST:").concat(guid.toString()).concat(":").concat(hashInBase64);
    return Base64.getEncoder().encodeToString(hmacHeader.getBytes(StandardCharsets.US_ASCII));
  }
}
