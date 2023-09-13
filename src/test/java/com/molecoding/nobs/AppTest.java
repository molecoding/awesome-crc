package com.molecoding.nobs;

import com.google.common.io.BaseEncoding;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
  /**
   * Rigorous Test :-)
   */
  @Test
  public void shouldAnswerWithTrue() {
    assertTrue(true);
  }

  @Test
  public void testCrc_shouldOk() {
    String hex = "0F" + // imei len
      "864333047098827000" + // imei with right padding zero
      "01" + // cmd
      "00" + // ord
      "251241BE4F9C8A26" // data encrypted
      ;
    assertEquals("72A4", new App().crc(hex));
  }

  @Test
  public void testEncrypt_shouldOk() throws Exception {
    String key = "0C 0F 07 0C 04 0E 0E 06".replace(" ", "");
    String data = new String(new char[]{0x01, 0x02, 0x03});

//    String expected = "251241BE4F9C8A2641BD80B10BB828E0";
    String encryptedHex = "DB38E7C03C539B77";
    assertEquals(encryptedHex, new App().encrypt(data, key));
  }

  @Test
  public void testEncrypt_withPadding_shouldOk() throws Exception {
    String key = "0C 0F 07 0C 04 0E 0E 06".replace(" ", "");
    String data = new String(new char[]{0x01, 0x02, 0x03});

    String paddedHex = new String(App.padding(data.getBytes()));
    assertEquals("251241BE4F9C8A26",
      new App().encrypt(paddedHex, key).substring(0, paddedHex.length() * 2));
  }


  @Test
  public void testDecrypt_shouldOk() throws Exception {
    String key = "0C 0F 07 0C 04 0E 0E 06".replace(" ", "");

    String data = "251241BE4F9C8A26" +
      "41BD80B10BB828E0";

    String expected = (
      "01 02 03 00 00 00 00 00").replace(" ", "");
    assertEquals(expected, new App().decrypt(data, key));
  }


  //  @Test
  public void testDecrypt2_shouldOk() throws Exception {
    String key = "0C 0F 07 0C 04 0E 0E 06".replace(" ", "");

    String data = "D768671120252F8B" +
      "33C046BD2C6FE93C" +
      "FB45C78249F836B7" +
      "071261BBC79D88B9" +
      "1CB016866DC5069F" +
      "E7AA2287F1A225CF";

    String expected = (
      "65 22 3C FA 38 C6 F8 B4 " +
        "1D 68 74 74 70 73 3A 2F " +
        "2F 7A 79 74 78 38 38 38 " +
        "2E 63 6E 3A 39 32 33 34 " +
        "2F 3F 4D 41 43 3D 00 00 " +
        "00 00 00 00 00 00 00 00 " +
        "00 00 00 00 00 00 00 00").replace(" ", "");
    assertEquals(expected, new App().decrypt(data, key));
  }


  //  @Test
  public void testEncrypt2_shouldOk() throws Exception {
    String key = "0C 0F 07 0C 04 0E 0E 06".replace(" ", "");

    String expected = "D768671120252F8B" +
      "33C046BD2C6FE93C" +
      "FB45C78249F836B7" +
      "071261BBC79D88B9" +
      "1CB016866DC5069F" +
      "E7AA2287F1A225CF" +
      "E7AA2287F1A225CF";

    String data = new String(BaseEncoding.base16().decode((
      "65 22 3C FA 38 C6 F8 B4 " +
        "1D 68 74 74 70 73 3A 2F " +
        "2F 7A 79 74 78 38 38 38 " +
        "2E 63 6E 3A 39 32 33 34 " +
        "2F 3F 4D 41 43 3D 00 00 " +
        "00 00 00 00 00 00 00 00 " +
        "00 00 00 00 00 00 00 00"
    ).replace(" ", "")));
    System.out.println(data);
    assertEquals(expected, new App().encrypt(data, key));
  }
}
