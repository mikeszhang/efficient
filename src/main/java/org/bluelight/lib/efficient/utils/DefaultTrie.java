package org.bluelight.lib.efficient.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import java.util.*;

/**
 * default implement for trie.
 * Created by mikes on 15-3-29.
 * @param <E> element type for default trie.
 */
public class DefaultTrie<E extends CharSequence> extends AbstractTrie<E> {
    private List<TrieNode<E>> rootNodeList=new ArrayList<TrieNode<E>>();
    @Override
    public <T extends CharSequence> boolean hasPrefix(T prefix) {
        for (int i=0;i<rootNodeList.size();i++){
            TrieNode<E> root=rootNodeList.get(i);
            if (root.getFirstChar()==prefix.charAt(0)){
                return root.hasPrefix(prefix, 0);
            }
        }
        return false;
    }

    @Override
    public <T extends CharSequence> Iterable<E> prefixAs(T prefix) {
        List<TrieNode<E>> terminatedNodeList=null;
        for (int i=0;i<rootNodeList.size();i++){
            TrieNode<E> root=rootNodeList.get(i);
            if (root.getFirstChar()==prefix.charAt(0)){
                terminatedNodeList=root.prefixAs(prefix, 0);
                break;
            }
        }
        if (terminatedNodeList==null){
            return Collections.emptyList();
        }
        return CollectionUtils.collect(terminatedNodeList, new Transformer<TrieNode<E>, E>() {
            @Override
            public E transform(TrieNode<E> trieNode) {
                return trieNode.getCompleteCharSequence();
            }
        });
    }

    @Override
    public boolean contains(Object o) {
        String str=o.toString();
        for (int i=0;i<rootNodeList.size();i++){
            TrieNode<E> root=rootNodeList.get(i);
            if (root.getFirstChar()==str.charAt(0)){
                return root.contains(str,0);
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        final List<TrieNode<E>> terminatedNodeList=new ArrayList<TrieNode<E>>();
        for (int i=0;i<rootNodeList.size();i++){
            terminatedNodeList.addAll(rootNodeList.get(i).getTerminatedNodeList());
        }
        return new Iterator<E>() {
            int current=0;
            @Override
            public boolean hasNext() {
                return current<terminatedNodeList.size();
            }

            @Override
            public E next() {
                TrieNode<E> terminatedNode=terminatedNodeList.get(current);
                current++;
                return terminatedNode.getCompleteCharSequence();
            }

            @Override
            public void remove() {
                TrieNode<E> terminatedNode=terminatedNodeList.get(current-1);
                DefaultTrie.this.remove(terminatedNode.getCompleteCharSequence());
            }
        };
    }

    @Override
    public boolean add(E e) {
        boolean added=false;
        for (int i=0; i<rootNodeList.size();i++){
            TrieNode<E> root=rootNodeList.get(i);
            if (root.getFirstChar()==e.charAt(0)){
                root.put(e,0);
                added=true;
                break;
            }
        }
        if (!added){
            TrieNode<E> root=new TrieNode<E>(e,0,e.length());
            this.rootNodeList.add(root);
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        String str=o.toString();
        if (!contains(o)){
            return false;
        }
        for (int i=0;i<rootNodeList.size();i++){
            TrieNode<E> root=rootNodeList.get(i);
            if (root.getFirstChar()==str.charAt(0)){
                if(root.remove(str,0)){
                    rootNodeList.remove(i);
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public int size(){
        int size=0;
        for (int i=0;i<rootNodeList.size();i++){
            TrieNode<E> root=rootNodeList.get(i);
            size+=root.size();
        }
        return size;
    }
}
