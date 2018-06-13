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

import java.util.List;
import java.util.ArrayList;
import tree.Node;

public class Ops {
	static int maxLength( List<StringBuilder> lines ) {
		int max= 0;
		for( StringBuilder line: lines ) {
			max= Math.max( max, line.length() );
		}
		return max;
	}
	
	static String print( final Node<?> node ) {
		List<StringBuilder> lines= printLines( node );
		StringBuilder result= new StringBuilder();
		for( StringBuilder line: lines ) {
			if( result.length()>0 ) {
				result.append( "\n" );
			}
			result.append( line );
		}
		return result.toString();
	}
	
	/** Not the most space-efficient, but simple.
	 * @return Line parts, shifted in both dimensions. */
	static <T> List<StringBuilder> printLines( final Node<T> node/*, final int initialMaxLineLength*/ ) {
		final List<StringBuilder> lines;
		final StringBuilder firstLine, secondLine;
		if( node.left()!=null ) {
			lines= printLines( node.left() );
			final int leftLength= maxLength(lines); // Not efficient, but that doesn't matter
			secondLine= lines.get(0);
			// print '~' filler character(s) right of the left node.
			// Following assumes that lines have no trailing spaces.
			final int numberOfFillers= Math.max( leftLength-secondLine.length(), 0 )+1;
			secondLine.append( "~".repeat(numberOfFillers) );
			secondLine.append('^');
			
			firstLine= new StringBuilder( " ".repeat( secondLine.length()-1) );
			lines.add( 0, firstLine );
		}
		else {
			lines= new ArrayList<StringBuilder>();
			firstLine= new StringBuilder();
			secondLine= new StringBuilder();
			lines.add( firstLine );
			lines.add( secondLine );
		}
		firstLine.append( node.value );
		
		if( node.right()!=null ) {
			if( node.left()==null ) {
				secondLine.append('^');
			}
			List<StringBuilder> rightLines= printLines( node.right() );
			final StringBuilder rightFirstLine= rightLines.get(0);
			if( rightFirstLine.charAt(0)!=' ' ) {
				secondLine.append('~');
			}
			for( int i=0; i<rightFirstLine.length(); i++ ) {
				if( rightFirstLine.charAt(i)==' ' ) {
					rightFirstLine.setCharAt(i, '~');
				}
				else {
					break;
				}
			}
			
			final int leftLength= Math.max( firstLine.length(), secondLine.length() );
			int mergedLength= 0;
			for( int i=1; i<lines.size(); i++ ) { // skip line #0, which is the root
				StringBuilder line= lines.get(i);
				line.append( " ".repeat( leftLength-line.length() ) );
				if( i<=rightLines.size() ) {
					line.append( rightLines.get(i-1) );
					mergedLength= Math.max( mergedLength, line.length() );
				} // otherwise do nothing, as we don't need to add trailing spaces
			}
			for( int i=lines.size(); i<rightLines.size(); i++ ) {
				StringBuilder line= new StringBuilder( " ".repeat(leftLength) );
				line.append( rightLines.get(i) );
				lines.add( line );
				mergedLength= Math.max( mergedLength, line.length() );
			}
		}
		return lines;
	}
}
