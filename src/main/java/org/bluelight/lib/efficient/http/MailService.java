package org.bluelight.lib.efficient.http;

import org.bluelight.lib.efficient.utils.CollectionPlus;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * mail service.
 * Created by mikes on 15-3-19.
 */
public class MailService {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    private String host;
    private boolean ssl=false;
    private int port=25;
    private Log log=LogFactory.getLog(MailService.class);
    private ExecutorService executorService;
    private int threadNum=2;

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum>0?threadNum:1;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        if (log!=null) {
            this.log = log;
        }
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private Session session;
    private List<String> recipientList;
    private List<String> ccList;

    public List<String> getRecipientList() {
        return recipientList;
    }

    public void setRecipientList(List<String> recipientList) {
        this.recipientList = recipientList;
    }

    public List<String> getCcList() {
        return ccList;
    }

    public void setCcList(List<String> ccList) {
        this.ccList = ccList;
    }

    public void init(){
        this.executorService=Executors.newFixedThreadPool(threadNum);
        if (StringUtils.isBlank(host)){
            host="smtp."+username.split("@")[1];
        }
        Properties properties=System.getProperties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port",String.valueOf(port));
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.host", host);
        if (ssl){
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback","false");
            properties.put("mail.smtp.socketFactory.port",String.valueOf(port));
        }
        session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });
    }
    public void send(String subject, String content, Object[] recipients, Object[] ccAddresses) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(username);
            message.setSubject(subject);
            message.setText(content);
            final List<InternetAddress> addressList = new ArrayList<InternetAddress>();
            for (Object recipient : recipients) {
                addressList.add(new InternetAddress(recipient.toString()));
            }
            message.addRecipients(Message.RecipientType.TO, CollectionPlus.toArray(Address.class, addressList));
            final List<InternetAddress> ccAddressList = new ArrayList<InternetAddress>();
            for (Object cc : ccAddresses) {
                ccAddressList.add(new InternetAddress(cc.toString()));
            }
            message.addRecipients(Message.RecipientType.CC, CollectionPlus.toArray(Address.class, ccAddressList));
            message.setSentDate(new Date());
            Transport.send(message);
        }
        catch (Exception e){
            log.error("error for send email: "+e.getMessage());
        }
    }
    public void send(String subject, String content, String... recipients) {
        this.send(subject,content,recipients,new String[0]);
    }
    public void send(String subject, String content) {
        if (recipientList==null){
            recipientList=new ArrayList<String>();
        }
        if (ccList==null){
            ccList=new ArrayList<String>();
        }
        this.send(subject,content,recipientList.toArray(),ccList.toArray());
    }
    public void sendAsync(final String subject, final String content, final Object[] recipients, final Object[] ccAddresses){
        try {
            this.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    send(subject, content, recipients, ccAddresses);
                }
            });
        }
        catch (Exception e){
            log.error("error for send mail async: "+e.getMessage());
        }
    }
    public void sendAsync(final String subject, final String content, final String... recipients){
        try {
            this.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    send(subject, content, recipients);
                }
            });
        }
        catch (Exception e){
            log.error("error for send mail async: "+e.getMessage());
        }
    }
    public void sendAsync(final String subject, final String content){
        try {
            this.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    send(subject, content);
                }
            });
        }
        catch (Exception e){
            log.error("error for send mail async: "+e.getMessage());
        }
    }
}
