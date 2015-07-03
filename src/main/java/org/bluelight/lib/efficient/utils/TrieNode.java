package org.bluelight.lib.efficient.utils;

import java.util.*;

/**
 * trie node.
 * @param <E> trie node type.
 * Created by mikes on 15-3-27.
 */
public class TrieNode<E extends CharSequence> {
    private E string;
    private byte begin;
    private byte end;
    private List<TrieNode<E>> subNodeList;
    private boolean nonLeafTerminated=false;
    public TrieNode(E string, int begin, int end){
        this.begin=(byte)begin;
        this.end=(byte)end;
        if (this.end<=this.begin || string.length()<this.end || this.begin<0){
            throw new RuntimeException("Error for string bound.");
        }
        this.string=string;
    }
    public void put(E str, int begin){
        int minSize=Math.min(end-this.begin,str.length()-begin);
        int i;
        for (i=0;i<minSize;i++){
            if (string.charAt(this.begin+i)!=str.charAt(begin+i)){
                break;
            }
        }
        if (i==0){
            throw new RuntimeException("Not proper for the trie node.");
        }
        if (end==this.begin+i && str.length()==begin+i){
            nonLeafTerminated=true;
        }
        else if (end==this.begin+i){
            boolean containFirst=false;
            if (subNodeList!=null) {
                for (int j=0;j<subNodeList.size();j++) {
                    TrieNode<E> node=subNodeList.get(j);
                    if (node.getFirstChar() == str.charAt(begin+i)) {
                        node.put(str,begin+i);
                        containFirst = true;
                        break;
                    }
                }
            }
            if (!containFirst){
                if (subNodeList==null) {
                    subNodeList = new ArrayList<TrieNode<E>>();
                }
                if (subNodeList.isEmpty()){
                    nonLeafTerminated=true;
                }
                subNodeList.add(new TrieNode<E>(str,begin+i,str.length()));
            }
            this.string=str;    // shorter string is replaced by longer str for sharing.
        }
        else if (str.length()==begin+i){
            TrieNode<E> node=new TrieNode<E>(string,this.begin+i,end);
            node.subNodeList=this.subNodeList;
            end=(byte)(this.begin+i);
            nonLeafTerminated=true;
            this.subNodeList=new ArrayList<TrieNode<E>>();
            this.subNodeList.add(node);
        }
        else {
            TrieNode<E> suflastNode=new TrieNode<E>(str,begin+i,str.length());
            TrieNode<E> prelastNode=new TrieNode<E>(string,this.begin+i,end);
            prelastNode.nonLeafTerminated=this.nonLeafTerminated;
            prelastNode.subNodeList=this.subNodeList;
            this.end=(byte)(this.begin+i);
            this.nonLeafTerminated=false;
            this.subNodeList=new ArrayList<TrieNode<E>>();
            this.subNodeList.add(prelastNode);
            this.subNodeList.add(suflastNode);
        }
    }
    public int size(){
        if (subNodeList==null || subNodeList.isEmpty()){
            return 1;
        }
        int size=nonLeafTerminated?1:0;
        for (int i=0;i<subNodeList.size();i++){
            TrieNode<E> node=subNodeList.get(i);
            size+=node.size();
        }
        return size;
    }
    public boolean contains(CharSequence str, int begin){
        int minSize=Math.min(end-this.begin,str.length()-begin);
        for (int i=0;i<minSize;i++){
            if (string.charAt(this.begin+i)!=str.charAt(begin+i)){
                return false;
            }
        }
        if (str.length()-begin<end-this.begin) {
            return false;
        }
        else if (str.length()-begin==end-this.begin){
            return nonLeafTerminated || subNodeList==null || subNodeList.isEmpty();
        }
        else {
            if (subNodeList==null || subNodeList.isEmpty()){
                return false;
            }
            for (int i=0;i<subNodeList.size();i++){
                TrieNode<E> node=subNodeList.get(i);
                if (node.getFirstChar()==str.charAt(begin+this.end-this.begin)){
                    return node.contains(str,begin+this.end-this.begin);
                }
            }
            return false;
        }
    }
    public List<TrieNode<E>> getTerminatedNodeList(){
        if (nonLeafTerminated || subNodeList==null || subNodeList.isEmpty()){
            return Arrays.asList(this);
        }
        List<TrieNode<E>> terminatedNodeList=null;
        for (int i=0;i<subNodeList.size();i++){
            List<TrieNode<E>> list=subNodeList.get(i).getTerminatedNodeList();
            if (terminatedNodeList==null){
                terminatedNodeList=list;
            }
            else {
                terminatedNodeList.addAll(list);
            }
        }
        if (nonLeafTerminated){
            if (terminatedNodeList==null){
                terminatedNodeList=new ArrayList<TrieNode<E>>();
            }
            terminatedNodeList.add(this);
        }
        return  terminatedNodeList;
    }
    public char getFirstChar(){
        return string.charAt(begin);
    }

    public <T extends CharSequence> boolean remove(T str, int begin){
        int minSize=Math.min(end-this.begin,str.length()-begin);
        for (int i=0;i<minSize;i++){
            if (string.charAt(this.begin+i)!=str.charAt(begin+i)){
                return false;
            }
        }
        if (str.length()-begin<end-this.begin) {
            return false;
        }
        else if (str.length()-begin==end-this.begin){
            nonLeafTerminated=false;
            return subNodeList==null || subNodeList.isEmpty();
        }
        else {
            if (subNodeList==null || subNodeList.isEmpty()){
                return false;
            }
            for (int i=0;i<subNodeList.size();i++){
                TrieNode<E> node=subNodeList.get(i);
                if (node.getFirstChar()==str.charAt(begin+this.end-this.begin)){
                    if (node.remove(str,begin+this.end-this.begin)) {
                        subNodeList.remove(i);
                        break;
                    }
                }
            }
            return !nonLeafTerminated && subNodeList.isEmpty();
        }
    }
    public <T extends CharSequence> boolean hasPrefix(T prefix, int begin){
        int minSize=Math.min(end-this.begin,prefix.length()-begin);
        for (int i=0;i<minSize;i++){
            if (string.charAt(this.begin+i)!=prefix.charAt(begin+i)){
                return false;
            }
        }
        if (prefix.length()-begin<=end-this.begin) {
            return true;
        }
        else {
            if (subNodeList==null || subNodeList.isEmpty()){
                return false;
            }
            for (int i=0;i<subNodeList.size();i++){
                TrieNode<E> node=subNodeList.get(i);
                if (node.getFirstChar()==prefix.charAt(begin+this.end-this.begin)){
                    return node.hasPrefix(prefix, begin + this.end - this.begin);
                }
            }
            return false;
        }
    }
    public <T extends CharSequence> List<TrieNode<E>> prefixAs(T prefix, int begin){
        int minSize=Math.min(end-this.begin,prefix.length()-begin);
        for (int i=0;i<minSize;i++){
            if (string.charAt(this.begin+i)!=prefix.charAt(begin+i)){
                return Collections.emptyList();
            }
        }
        if (prefix.length()-begin<=end-this.begin) {
            return this.getTerminatedNodeList();
        }
        else {
            if (subNodeList==null || subNodeList.isEmpty()){
                return Collections.emptyList();
            }
            for (int i=0;i<subNodeList.size();i++){
                TrieNode<E> node=subNodeList.get(i);
                if (node.getFirstChar()==prefix.charAt(begin+this.end-this.begin)){
                    return node.prefixAs(prefix, begin + this.end - this.begin);
                }
            }
            return Collections.emptyList();
        }
    }
    public E getCompleteCharSequence(){
        return string;
    }
}
