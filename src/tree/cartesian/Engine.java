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

import java.util.List;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public abstract class Engine {
	protected abstract <T extends Comparable<T>> CartesianNode<T> build( List<T> values );
	
	protected static List<Integer> parseArgs( String... numbersAsStrings ) {
		if( false ) { // before Lambda
			Integer nums[]= new Integer[numbersAsStrings.length];
			int i=0;
			for( String numberString: numbersAsStrings ) {
				Integer num= Integer.parseInt(numberString);
				nums[i++]= num;
			}
			return Arrays.asList(nums);
		}
		else {
			return Arrays.asList(numbersAsStrings).stream().map( s -> Integer.parseInt(s) ).collect( Collectors.toList() );
		}
	}
	
	public String buildAndFormat( String... numbersAsStrings ) {
		assert numbersAsStrings.length>0: "Pass at least one value.";
		List<Integer> nums= parseArgs(numbersAsStrings);
		CartesianNode<Integer> root= build(nums);
		return Ops.format( root );
	}
	
	/** Invoke from static main(String...). */
	protected void run( String... numbersAsStrings ) {
		System.out.println( buildAndFormat(numbersAsStrings) );
	}
	
}
