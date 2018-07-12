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
package util;

import java.util.Map;
import java.util.HashMap;
//import java.util.function.Supplier;
import static util.MapLiteral.SettableKeys;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapLiteralTest {
	
	public MapLiteralTest() {
	}
	
	@Test
	public void test() {
		final String ACCEPT= "Accept", BE="Be", COMMUNICATE="Communicate";
		final String keysArray[]= {ACCEPT, BE, COMMUNICATE};
		MapLiteral.SettableKeys<String> settableKeys= new MapLiteral.SettableKeys(ACCEPT, BE, COMMUNICATE);
		Character valuesArray[]= {'a', 'b', 'c'}; // Values are characters, rather than words, to save typing
		
		Map<String, Character> checked= new HashMap<>();
		MapLiteral.set( checked, settableKeys, valuesArray);
		assertTrue( checked.entrySet().size()==valuesArray.length );
		assertTrue( checked.get(ACCEPT).equals('a') );
		assertTrue( checked.get(BE).equals('b') );
		assertTrue( checked.get(COMMUNICATE).equals('c') );
				
		{
			HashMapLiteral<String, Character> map= new HashMapLiteral();
			map.set(keysArray, valuesArray);
			assertTrue( map.equals(checked) );
		}
		{
			HashMapLiteral<String, Character> map= new HashMapLiteral();
			map.set(keysArray, 'a', 'b', 'c');
			assertTrue( map.equals(checked) );
		}
		
		{
			HashMapLiteral<String, Character> map= new HashMapLiteral();
			map.set(settableKeys, valuesArray);
			assertTrue( map.equals(checked) );
		}
		{
			HashMapLiteral<String, Character> map= new HashMapLiteral();
			map.set(settableKeys, 'a', 'b', 'c');
			assertTrue( map.equals(checked) );
		}
		// 1. key:value, {key, value}, ... with static typing
		// class::function
		// class::new??
		
		// 2. simplify: don't [repeat] type generic type params
		// Even easier with: import static MapLiteral.keys;
		{
			Map<String, Character> map= new HashMap();
			//MapLiteral.set( map, ()->keys, values );
			MapLiteral.set( map, settableKeys, valuesArray );
			assertTrue( map.equals(checked) );
		}
		{
			Map<String, Character> map= new HashMap();
			//MapLiteral.set( map, ()->keys, values );
			MapLiteral.set( map, settableKeys, 'a', 'b', 'c' );
			assertTrue( map.equals(checked) );
		}
		{
			Map<String, Character> map= new HashMap();
			//MapLiteral.set( map, ()->keys, values );
			MapLiteral.set( map, Varargs.of(keysArray), valuesArray );
			assertTrue( map.equals(checked) );
		}
		{
			Map<String, Character> map= new HashMap();
			//MapLiteral.set( map, ()->keys, values );
			MapLiteral.set( map, Varargs.of(ACCEPT, BE, COMMUNICATE), valuesArray );
			assertTrue( map.equals(checked) );
		}
		// We can't do lambda: ()->{firstArrayItem,secondArrayItem...}
		
		{
			HashMapLiteral<Character, String> map= new HashMapLiteral( settableKeys, valuesArray );
			assertTrue( map.equals(checked) );
		}
		{
			HashMapLiteral<Character, String> map= new HashMapLiteral( settableKeys, 'a', 'b', 'c' );
			assertTrue( map.equals(checked) );
		}
		{
			/*HashMapLiteral<Character, String> map= new HashMapLiteral( ()->keys, values );*/
			//assertTrue( map.equals(checked) );
		}
		//new HashMapLiteral().set(...).to(...);
		
		
		
		// Can't use a diamond operator for generics inference in function calls: aFunction( new HashMap<>);
//		array( new int[]{0, 1} );
//		supplied( ()->new String[]{"Hi"} );
	}
	
//	void array( int[] p ) {}
//	void supplied( Supplier<String[]> s ){}
}
