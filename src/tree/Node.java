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

public class Node<T> {
	public final T value;
	private Node<T> leftNode, rightNode;
	
	public Node(T given) {
		value= given;	
	}
	
	/* Not final, so that subclasses can override it with a more specific return type. */
	public Node<T> left() { return leftNode; }
	public Node<T> right() { return rightNode; }
	
	public void setLeft( Node<T> node ) {	setLeft(node, false); }
	public void setLeft( Node<T> node, boolean override ) {
		assert (leftNode!=null) == override;
		leftNode= node;
	}
	public void setRight( Node<T> node ) { setRight( node, false ); }
	public void setRight( Node<T> node, boolean override ) {
		assert (rightNode!=null) == override;
		rightNode= node;
	}
}