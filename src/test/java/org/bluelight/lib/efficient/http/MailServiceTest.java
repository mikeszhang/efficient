package org.bluelight.lib.efficient.http;

import org.junit.Test;

/**
 * mail service test.
 * Created by mikes on 15-3-20.
 */
public class MailServiceTest {
    @Test
    public void testNoExceptionOut() {
        MailService mailService=MailServiceBuilder.newBuilder("****@***.com","*****")
                .addRecipient("****@***.com")
                .ssl(true)
                .port(465)
                .build();

        mailService.send("test","this is test mail.");
        mailService.sendAsync("test async","test async mail.");
    }
}
