/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.action;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * Implements the methods of the {@link java.util.AbstractList Abstractlist}
 * interface and the action generator methods of the
 * {@pobs.action.PAbstractList}interface analogue to the
 * {@link java.util.Vector}class.
 * 
 * @author Martijn W. van der Lee
 * @author Martijn W. van der Lee
 */
public class PVector extends PAbstractList {
    protected java.util.Vector vector;

    /**
     * Insert the method's description here.
     */
    public PVector() {
        super();

        vector = new java.util.Vector();
    }

    /**
     * Insert the method's description here.
     */
    public PVector(java.util.Collection c) {
        super();

        this.vector = new java.util.Vector(c);
    }

    /**
     * @see pobs.action.PAbstractList
     */
    protected void addToContainer(int index, Object object) {
        vector.add(index, object);
    }

    /**
     * @see pobs.action.PAbstractCollection
     */
    protected void addToContainer(Object object) {
        vector.add(object);
    }

    /**
     * Insert the method's description here.
     * 
     * @return java.util.Vector
     */
    public java.util.Vector getCollection() {
        return vector;
    }

    /**
     * @see pobs.action.PCollection
     */
    public java.util.Iterator iterator() {
        return vector.iterator();
    }

    /**
     * Insert the method's description here.
     * 
     * @return int
     */
    public int size() {
        return vector.size();
    }

    public void clear() {
        vector.clear();
    }

    public boolean isEmpty() {
        return vector.isEmpty();
    }

    public Object[] toArray() {
        return vector.toArray();
    }

    public boolean add(Object o) {
        return vector.add(o);
    }

    public boolean contains(Object o) {
        return vector.contains(o);
    }

    public boolean remove(Object o) {
        return vector.remove(o);
    }

    public boolean addAll(Collection c) {
        return vector.addAll(c);
    }

    public boolean containsAll(Collection c) {
        return vector.containsAll(c);
    }

    public boolean removeAll(Collection c) {
        return vector.removeAll(c);
    }

    public boolean retainAll(Collection c) {
        return vector.retainAll(c);
    }

    public Object[] toArray(Object[] o) {
        return toArray(o);
    }

    public Object get(int i) {
        return vector.get(i);
    }

    public Object remove(int i) {
        return vector.remove(i);
    }

    public void add(int i, Object o) {
        vector.add(i, o);
    }

    public int indexOf(Object o) {
        return vector.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return lastIndexOf(o);
    }

    public boolean addAll(int i, Collection c) {
        return addAll(i, c);
    }

    public List subList(int fromIndex, int toIndex) {
        return vector.subList(fromIndex, toIndex);
    }

    public ListIterator listIterator() {
        return vector.listIterator();
    }

    public ListIterator listIterator(int i) {
        return vector.listIterator(i);
    }

    public Object set(int i, Object o) {
        return vector.set(i, o);
    }
}