/*
 * Copyright 2005 FBK-irst (http://www.fbk.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package org.itc.irst.tcc.sre.util;


import java.util.Iterator;

/**
 * Interface for vectors holding double elements. 
 *
 * A vector has a growable number of cells (its size). Elements
 * are accessed via zero based indexes. Legal indexes are of the
 * form [0..size()-1]. Any attempt to access an element at a
 * coordinate index<0 || index>=size() will throw an
 * IndexOutOfBoundsException.
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public interface Vector
{
	/**
	 * Inserts the specified element at the specified position
	 * in this vector. Shifts the element currently at that
	 * position (if any) and any subsequent elements to the
	 * right (adds one to their indices).
	 *
	 * @param index		index at which the specified element
	 *								is to be inserted.
	 * @param value		value to be inserted.
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *																		(index < 0).
	 */
	public abstract void add(int index, double value) throws IndexOutOfBoundsException;
	
	/**
	 * Returns the element at the specified position in this vector.
	 * 
	 * @param index index of element to return.
	 * @return the element at the specified position in this vector.
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *																		(index < 0 || index > size()).
	 */
	public abstract double get(int index) throws IndexOutOfBoundsException;

	/**
	 * Returns a boolean denoting whether this index already
	 * exists in the vector.
	 * 
	 * @param index index of element to return.
	 * @return <code>true</code> if and only if the index exists; <code>false</code> otherwise
	 */
	public abstract boolean existsIndex(int index) throws IndexOutOfBoundsException;

	/**
	 * Replaces the element at the specified position in this vectro
	 * with the specified element (optional operation).
	 * 
	 * @param index index of element to return.
	 * @param value		value to be inserted.
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *																		(index < 0 || index > size()).
	 */
	public abstract void set(int index, double value) throws IndexOutOfBoundsException;
	
	/**
	 * Returns the size of this vector.
	 *
	 * @return the size of this vector.
	 */
	public abstract int size();

	/**
	 * Returns the number of elements in this vector
	 *
	 * @return the number of elements in this vector.
	 */
	public abstract int elementCount();
	
	/**
	 * Returns an iterator over the elements in this vector in
	 * proper sequence.
	 *
	 * @return an iterator over the elements in this vector
	 *						in proper sequence.
	 */
	public abstract Iterator iterator();
	
	/**
	 * Returns an iterator over the elements in this vector in
	 * proper sequence (optional operation).
	 *
	 * @return an iterator over the elements in this vector
	 *						in proper sequence.
	 */
	public abstract double dotProduct(Vector v);

	/**
	 * Returns the norm of this vector (optional operation).
	 *
	 * @return norm of this vector;
	 */
	public abstract double norm();
	
	/**
	 * Normalizes this vector (optional operation).
	 */
	public abstract void normalize();

	//
	public abstract Node[] toArray();
	
} // end interface Vector