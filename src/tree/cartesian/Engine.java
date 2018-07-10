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

public abstract class Engine {
	protected abstract <T extends Comparable<T>> CartesianNode<T> build( T... values );
	
	protected static Integer[] parseArgs( String... numbersAsStrings ) {
		Integer nums[]= new Integer[numbersAsStrings.length];
		int i=0;
		for( String numberString: numbersAsStrings ) {
			Integer num= Integer.parseInt(numberString);
			nums[i++]= num;
		}
		return nums;
	}
	
	public String buildAndFormat( String... numbersAsStrings ) {
		assert numbersAsStrings.length>0: "Pass at least one value.";
		Integer nums[]= parseArgs(numbersAsStrings);
		CartesianNode<Integer> root= build(nums);
		return Ops.format( root );
	}
	
	/** Invoke from static main(String...). */
	protected void run( String... numbersAsStrings ) {
		System.out.println( buildAndFormat(numbersAsStrings) );
	}
	
}
