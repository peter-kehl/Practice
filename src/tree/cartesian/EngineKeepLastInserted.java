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
package tree.cartesian;

/** O(N) as per http://wcipeg.com/wiki/Cartesian_tree
 */
public class EngineKeepLastInserted extends Engine {
	
	protected <T extends Comparable<T>> CartesianNode<T> build( T... values ) {
		CartesianNode<T> lastInserted= null;
		valuesLoop: for( final T value: values ) {
			final CartesianNode<T> created= new CartesianNode<T>(value);
			if( lastInserted!=null ) {
				//CartesianNode<T> root;
				for( CartesianNode<T> parent= lastInserted; parent!=null; parent=parent.parent() ) {
					if( parent.value.compareTo(value)<0 ) {
						CartesianNode<T> previousRightChild= parent.right();
						parent.setRight( created, previousRightChild!=null );
						created.setLeft(previousRightChild);
						//lastInserted= created;
						//continue valuesLoop;
						break;
					}
					if( parent.parent()==null ) {
						created.setLeft( parent ); // This would make for() loop iterate again
						break;
					}
				}
			}
			lastInserted= created;
		}
		
		for( CartesianNode<T> parent= lastInserted; parent!=null; parent=parent.parent() ) {
			if( parent.parent()==null ) {
				return parent;
			}
		}
		throw new IllegalStateException();
	}

	public static void main( String... args ) {
		new EngineKeepLastInserted().run(args);
	}
}
