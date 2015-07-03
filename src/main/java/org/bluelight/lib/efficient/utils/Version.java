package org.bluelight.lib.efficient.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * simple three phase version of software.
 * Created by mikes on 14-7-5.
 */
public class Version implements Comparable<Version>, Cloneable, Serializable{
    private int major=0;
    private int middle=0;
    private int minor=0;
    public Version(String versionStr){
        if (StringUtils.isNotEmpty(versionStr)){
            try{
                String[] verSplit=versionStr.split("\\.");
                this.major=Integer.parseInt(verSplit[0]);
                this.middle=Integer.parseInt(verSplit[1]);
                this.minor=Integer.parseInt(verSplit[2]);
            }
            catch (Exception e){}
        }
    }
    public Version(){

    }
    public Version(int major){
        this.major=major;
    }
    public Version(int major, int middle){
        this.major=major;
        this.middle=middle;
    }
    public Version(int major, int middle, int minor){
        this.major=major;
        this.middle=middle;
        this.minor=minor;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMiddle() {
        return middle;
    }

    public void setMiddle(int middle) {
        this.middle = middle;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    /**
     * the version of middle and minor is two positions for max.
     * */
    public static int convertInt(String versionStr){
        if (StringUtils.isEmpty(versionStr)){
            throw new IllegalArgumentException("string is empty");
        }
        String[] verSplit=versionStr.split("\\.");
        int major=Integer.parseInt(verSplit[0]);
        int middle=verSplit.length>1?Integer.parseInt(verSplit[1]):0;
        int minor=verSplit.length>2?Integer.parseInt(verSplit[2]):0;
        if (major<0 || middle<0 || minor<0){
            throw new IllegalArgumentException("version part should be positive.");
        }
        minor=minor%100;
        middle=middle%100;
        return minor+middle*100+major*10000;
    }
    public static int convertInt(String versionStr, int defaultInt){
        try {
            return convertInt(versionStr);
        }
        catch (Exception e){
            return defaultInt;
        }
    }
    public static String covertString(int versionInt){
        int minor=versionInt%100;
        int middle=(versionInt/100)%100;
        int major=versionInt/10000;
        return new StringBuilder().append(major).append('.').append(middle).append('.').append(minor).toString();
    }
    @Override
    public String toString(){
        StringBuilder versionStr=new StringBuilder();
        versionStr.append(this.major).append('.').append(this.middle).append('.').append(this.minor);
        return versionStr.toString();
    }
    @Override
    public boolean equals(Object object){
        if (object==null){
            return false;
        }
        if (this==object){
            return true;
        }
        if (object instanceof Version){
            Version another=(Version)object;
            if (this.major==another.major&&this.middle==another.middle&&this.minor==another.minor){
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(Version version) {
        if (version==null){
            version=new Version("0.0.0");
        }
        if (this.major>version.major){
            return 1;
        } else if (this.major<version.major){
            return -1;
        } else if (this.middle>version.middle){
            return 1;
        } else if (this.middle<version.middle){
            return -1;
        } else if (this.minor>version.minor){
            return 1;
        } else if (this.minor<version.minor){
            return -1;
        } else {
            return 0;
        }
    }
    @Override
    public Version clone() throws CloneNotSupportedException {
        return (Version)super.clone();
    }
    public boolean before(Version version){
        return this.compareTo(version)<0;
    }
    public boolean after(Version version){
        return this.compareTo(version)>0;
    }
}
