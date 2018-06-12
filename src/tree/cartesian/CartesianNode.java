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

package tree.cartesian;

import tree.Node;
import tree.ComparableNode;

public class CartesianNode<T extends Comparable<T> > extends ComparableNode<T> {
	public CartesianNode(T given) {
		super(given);	
	}
	
	public CartesianNode<T> left() {
		return (CartesianNode<T>)super.left();
	}
	public CartesianNode<T> right() {
		return (CartesianNode<T>)super.right();
	}
	// setLeft() and setRight() ensure that node.value is always the lowest value in the subtree
	public void setLeft( Node<T> node, boolean override ) {
		assert node instanceof CartesianNode;
		assert node.value.compareTo(value) >=0;
		super.setLeft(node, override);
	}
	public void setRight( Node<T> node, boolean override ) {
		assert node instanceof CartesianNode;
		assert node.value.compareTo(value) >=0;
		super.setRight( node, override );
	}
}