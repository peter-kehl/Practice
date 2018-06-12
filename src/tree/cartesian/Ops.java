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

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.function.Consumer;

public class Ops {
	public static <T extends Comparable<T>> String print( CartesianNode<T> node ) {
		SortedMap<T, String> valuesToLines= new TreeMap<T, String>();
		print( null, node, valuesToLines, -1, 0 );
		Consumer<String> consumer= new Consumer<String>() {
			private String result= "";
			public void accept( String s ) {
				if( !result.isEmpty() ) {
					result+= "\n";
				}
				result+= s;
			}
			public String toString() { return result; }
		};
		valuesToLines.values().forEach( consumer );
		return ""+consumer;
	}
	/** Use dot '~' instead of dash '-', to support printing negative numbers.
	 *  @param int indent Number of spaces to print left of the next node to print.
	 *  @return new indent
	 */
	static <T extends Comparable<T>> int print( CartesianNode<T> parent, CartesianNode<T> node, SortedMap<T, String> valuesToLines, final int parentIndent, final int originalIndent ) {
		final int nodeIndent;
		if( node.left()!=null ) {
			nodeIndent= print( node, node.left(), valuesToLines, -1, originalIndent );
		}
		else {
			nodeIndent= originalIndent;
		}
		
		final String prefix; // In format "^~~...~". It will be added left of node, if node is the *right* child of its parent. The parent was already printed, hence this is easy.
		final int prefixOutOfIndent; // What lenght of prefix was taken out of node's indent.
		if( parent!=null && node==parent.right() ) {
			assert nodeIndent>=parentIndent+2; // Hence following generates at least one '~'
			prefix= "^" +"~".repeat(nodeIndent-parentIndent -1/*-1 is for ~ char.*/);
			if( node.left()==null ) {
				prefixOutOfIndent= 0;
			}
			else {
				prefixOutOfIndent= nodeIndent-originalIndent;
			}
		}
		else {
			prefix= "";
			prefixOutOfIndent= 0;
		}
		final String nodeIndentAndValue= " ".repeat(nodeIndent-prefix.length()) +prefix+ node.value;
		// We can't generate postfix yet, because that will depend on indent shift from printing of node.right().
		
		final int afterCartesianNodeIndent= nodeIndentAndValue.length()+1;
		final int afterRightIndent;
		if( node.right()!=null ) {
			afterRightIndent= print( node, node.right(), valuesToLines, nodeIndent, afterCartesianNodeIndent );
		}
		else {
			afterRightIndent= afterCartesianNodeIndent;
		}
		
		final String postfix; // String in format "~~~...~^". It will be added right of node, if node is the *left* child of its parent.
		if( parent!=null && node==parent.left() ) {
			if( node.right()==null ) {
				postfix= "~^";
			}
			else {
				postfix= "~".repeat(afterRightIndent-afterCartesianNodeIndent+1)+ "^";
			}
		}
		else {
			postfix= "";
		}
		valuesToLines.put( node.value, nodeIndentAndValue+postfix );
		
		assert afterRightIndent>=originalIndent+2; // at least 1 digit + 1 space
		return afterRightIndent;
	}	
}
