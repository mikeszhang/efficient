package org.bluelight.lib.efficient.http;

import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * builder for mail service.
 * Created by mikes on 15-3-20.
 */
public class MailServiceBuilder {
    private MailService mailService;
    private MailServiceBuilder(String username, String password){
        mailService=new MailService();
        mailService.setUsername(username);
        mailService.setPassword(password);
    }
    public static MailServiceBuilder newBuilder(String username, String password){
        return new MailServiceBuilder(username,password);
    }
    public MailServiceBuilder host(String host){
        mailService.setHost(host);
        return this;
    }
    public MailServiceBuilder addRecipient(String recipient){
        List<String> recipientList=mailService.getRecipientList();
        if (recipientList==null){
            recipientList=new ArrayList<String>();
        }
        recipientList.add(recipient);
        mailService.setRecipientList(recipientList);
        return this;
    }
    public MailServiceBuilder recipients(List<String> recipientList){
        mailService.setRecipientList(recipientList);
        return this;
    }
    public MailServiceBuilder addCc(String cc){
        List<String> ccList=mailService.getCcList();
        if (ccList==null){
            ccList=new ArrayList<String>();
        }
        ccList.add(cc);
        mailService.setRecipientList(ccList);
        return this;
    }
    public MailServiceBuilder cc(List<String> ccList){
        mailService.setCcList(ccList);
        return this;
    }
    public MailServiceBuilder ssl(boolean ssl){
        mailService.setSsl(ssl);
        return this;
    }
    public MailServiceBuilder port(int port){
        mailService.setPort(port);
        return this;
    }
    public MailServiceBuilder thread(int num){
        mailService.setThreadNum(num);
        return this;
    }
    public MailServiceBuilder log(Log log){
        mailService.setLog(log);
        return this;
    }
    public MailService build(){
        mailService.init();
        MailService forReturn=mailService;
        mailService=null;
        return forReturn;
    }
}
