/*
 * Copyright (C) 2018 pkehl
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package util;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;

public class Varargs<T> implements Iterable<T> {
	protected final boolean usesExtra;
	protected final T extra;
	protected final T items[];
	
	protected Varargs(boolean usesExtraItem, T extraItem, T more[]) {
		usesExtra= usesExtraItem;
		extra= extraItem;
		items= more;
	}

	public Varargs(T first, T... more) {
		this(true, first, more);
	}

	public Varargs(T items[] ) {
		this(false, null, items);
	}
	
	public int length() {
		return items.length+ (usesExtra ? 1 : 0);
	}
	
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int index= usesExtra
				? -1
				: 0;
			public boolean hasNext() {
				return index<items.length;
			}
			public T next() {
				if( index<0 ) {
					index= 0;
					return extra;
				}
				if( index>=items.length ) {
					throw new NoSuchElementException();
				}
				return items[index++];
			}
		};
	}
	
	public static <A, B> void dualIterate( Iterable<A> aSource, Iterable<B> bSource, BiConsumer<A,B> action ) {
		final Iterator<B> bIterator= bSource.iterator();
		for( A a: aSource ) {
			B b= bIterator.next();
			action.accept( a, b);
		}
		if( bIterator.hasNext() ) {
			throw new NoSuchElementException("aSource had fewer items.");
		}
	}
}
