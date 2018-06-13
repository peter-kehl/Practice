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

/** O(N^2)
 */
public class EngineSimpleRecursive extends Engine {
	/** Push the given node to the right branch under the tree with the given existing root.
	 *  Given node must be known to fit there - i.e. with value higher than root's own value.
	 */
	static <T extends Comparable<T>> void pushRight( CartesianNode<T> root, CartesianNode<T> node ) {
		assert node.value.compareTo(root.value) >=0;
		if( root.right()==null ) {
			root.setRight( node );
		}
		else {
			root.setRight( join(root.right(), node), true );
		}
	}
	
	/** Add a given node as the rightmost node to the tree with given existing root.
	 @return New root (which is either root or node). */
	public static <T extends Comparable<T>> CartesianNode<T> join( CartesianNode<T> root, CartesianNode<T> node ) {
		assert node.left()==null && node.right()==null;
		if( node.value.compareTo(root.value) <=0 ) {
			node.setLeft( root );
			return node;
		}
		else {
			pushRight( root, node );
			return root;
		}
	}
	
	protected <T extends Comparable<T>> CartesianNode<T> build( T... values ) {
		CartesianNode<T> root= null;
		for( T value: values ) {
			CartesianNode<T> node= new CartesianNode<T>(value);
			if( root==null ) {
				root= node;
			}
			else {
				root= join( root, node );
			}
		}
		return root;
	}

	public static void main( String... args ) {
		new EngineSimpleRecursive().run(args);
	}

}
