/*
 * Copyright (C) 2018 Peter Kehl
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
package tree;

/** It intentionally doesn't have a constructor that would set this.parent, neither it has setParent(). Why?
 *  Because setParent() wouldn't know which child (left or right) this is. Hence it would leave them connected one way only. To connect the parent and child both ways, use setLeft(), setRight() instead.
 */
public class Node<T> {
	public final T value;
	private Node<T> parent, leftNode, rightNode;
	
	public Node( T givenValue) {
		value= givenValue;	
	}
	
	/* Not final, so that subclasses can override it with a more specific return type. */
	public Node<T> parent() { return parent; }
	public Node<T> left() { return leftNode; }
	public Node<T> right() { return rightNode; }
	
	/*public void setParent( Node<T> node ) {
		setParent( node, false );
	}
	public void setParent( Node<T> node, boolean override ) {
		assert (parent!=null) == override;
		if( parent!=null ) {//cleanup
			if( parent.leftNode==this ) {
				parent.leftNode= null;
			}
			if( parent.leftNode==this ) {
				parent.leftNode= null;
			}
		}
	}*/
	public void setLeft( Node<T> node ) {	setLeft(node, false); }
	public void setLeft( Node<T> node, boolean override ) {
		assert (leftNode!=null) == override;
		if( leftNode!=null) {//cleanup
			leftNode.parent= null;
		}
		leftNode= node;
		if( leftNode!=null ) {//connect
			leftNode.parent= this;
		}
	}
	public void setRight( Node<T> node ) { setRight( node, false ); }
	public void setRight( Node<T> node, boolean override ) {
		assert (rightNode!=null) == override;
		if( rightNode!=null) {//cleanup
			rightNode.parent= null;
		}
		rightNode= node;
		if( rightNode!=null ) {
			rightNode.parent= this;
		}
	}
}