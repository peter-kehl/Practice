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
package tree.construct;

import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import tree.Node;

/** See https://www.geeksforgeeks.org/?p=6633
 */
public class Engine {
	public static <T> Node<T> build( List<T> inOrder, List<T> preOrder ) {
		return build( inOrder, preOrder, 0, 0 ).node;
	}
	
	static class Result<T> {
		final Node<T> node;
		final int nextInOrderStart, nextPreOrderStart;
		Result( Node<T> givenNode, int givenNextInOrderStart, int givenNextPreOrderStart ) {
			node= givenNode;
			nextInOrderStart= givenNextInOrderStart;
			nextPreOrderStart= givenNextPreOrderStart;
		}
	}
	
	public static <T> Result<T> build( List<T> inOrder, List<T> preOrder, final int inOrderStart, final int preOrderStart ) {
		final T value= preOrder.get( preOrderStart );
		final Node<T> node= new Node<T>(value);
		
		if( inOrderStart<inOrder.size() ) {
			final T firstValue= inOrder.get(inOrderStart);
			final int secondValueInOrderStart;
			final int secondValuePreOrderStart;
			if( !firstValue.equals(value) ) {
				Result<T> leftResult= build( inOrder, preOrder, inOrderStart, preOrderStart+1 );
				node.setLeft( leftResult.node );
				secondValueInOrderStart= leftResult.nextInOrderStart+1;
				secondValuePreOrderStart= leftResult.nextPreOrderStart;
			}
			else {
				secondValueInOrderStart= inOrderStart+1;
				secondValuePreOrderStart= preOrderStart+1;
			}
			if( secondValueInOrderStart<inOrder.size() ) {
				final T secondValueInOrder= inOrder.get( secondValueInOrderStart );
				final T secondValuePreOrder= preOrder.get( secondValuePreOrderStart );
				if( secondValueInOrder.equals(secondValuePreOrder) ) {
					Result<T> rightResult= build(inOrder, preOrder, secondValueInOrderStart, secondValuePreOrderStart);
					node.setRight(  rightResult.node );
					return new Result<T>( node, rightResult.nextInOrderStart, rightResult.nextPreOrderStart );
				}
				else { // no right subtree
					return new Result<T>( node, secondValueInOrderStart, secondValuePreOrderStart );
				}
			}
		}
		return new Result<T>( node, -1, -1/*end*/ );		
	}
	
	public static void main(String... args) {
		assert args.length%2 ==0;
		if( args.length==0 ) {
			System.err.println( "Run with inOrder sequence first, followed by preOrder sequence. Each sequence must contain unique words. Hence pass an even number of words, each word repeated once: the first time in inOrder sequence, the second time in preOrder sequence." );
			return;
		}
		List<String> both= Arrays.asList(args);
		List<String> inOrder= both.subList(0, both.size()/2 );
		List<String> preOrder= both.subList(both.size()/2, both.size() );
		assert inOrder.containsAll( preOrder ) : "Both sequences must contain same entries (in any order).";
		assert new HashSet<String>( inOrder ).size() ==inOrder.size() : "Entries (within each sequence) must be unique.";
		
		Node<String> tree= build( inOrder, preOrder );
		System.out.println( Ops.print( tree ) );
	}
}
