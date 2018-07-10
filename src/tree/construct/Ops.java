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

import java.util.stream.Collectors;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import tree.Node;

public class Ops {
	static int maxLength( List<StringBuilder> lines ) {
		if( false ) {//before Lambda
			int max= 0;
			for( StringBuilder line: lines ) {
				max= Math.max( max, line.length() );
			}
			return max;
		}
		else {
			//Optional<String> max= lines.stream().map( sb->sb.toString() ).collect( Collectors.maxBy(String.CASE_INSENSITIVE_ORDER) );
			Optional<Integer> max= lines.stream().map( /*line->line.length()*/StringBuilder::length ).collect( Collectors.maxBy(Integer::compare) );
			return max.get();
		}
	}
	
	static String format( final Node<?> node ) {
		List<StringBuilder> lines= formatLines( node );
		if( false ) {//before Lambda
			StringBuilder result= new StringBuilder();
			for( StringBuilder line: lines ) {
				if( result.length()>0 ) {
					result.append( "\n" );
				}
				result.append( line );
			}
			return result.toString();
		}
		else {
			return lines.stream().collect( Collectors.joining("\n") );
		}
	}
	
	/** Not the most space-efficient, but simple.
	 * @return Line parts, shifted in both dimensions. */
	static <T> List<StringBuilder> formatLines( final Node<T> node/*, final int initialMaxLineLength*/ ) {
		final List<StringBuilder> lines;
		final StringBuilder firstLine, secondLine;
		if( node.left()!=null ) {
			lines= formatLines( node.left() );
			final int leftLength= maxLength(lines); // Not efficient, but that doesn't matter. Otherwise formatLines() could return a compound {List<StringBuilder>, int maxLength}
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
			if( node.right()!=null ) {
				lines.add( secondLine );
			}
		}
		firstLine.append( node.value );
		
		if( node.right()!=null ) {
			if( node.left()==null ) {
				secondLine.append('^');
			}
			List<StringBuilder> rightLines= formatLines( node.right() );
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
			for( int i=1; i<lines.size(); i++ ) { // skip line #0, which is the root
				StringBuilder line= lines.get(i);
				if( i-1<rightLines.size() ) {
					// Add trailing spaces to the output of the left subtree, so that the right subtree is shifted evenly
					line.append( " ".repeat( leftLength-line.length() ) );
					line.append( rightLines.get(i-1) );
				}
			}
			for( int i=lines.size()-1; i<rightLines.size(); i++ ) {
				StringBuilder line= new StringBuilder( " ".repeat(leftLength) );
				line.append( rightLines.get(i) );
				lines.add( line );
			}
		}
		return lines;
	}
}
