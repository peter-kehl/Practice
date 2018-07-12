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
import java.util.function.Supplier;

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
		MapLiteral.SettableKeys<Character> keys= new MapLiteral.SettableKeys('a', 'b', 'c');
		final String ACCEPT= "Accept", BE="Be", COMMUNICATE="Communicate";
		String values[]= {ACCEPT, BE, COMMUNICATE};
		Map<Character, String> checked= new HashMap<>();
		MapLiteral.set( checked, keys, values);
		assertTrue( checked.entrySet().size()==values.length );
		assertTrue( checked.get('a')==ACCEPT );
		assertTrue( checked.get('b')==BE );
		assertTrue( checked.get('c')==COMMUNICATE );
				
		{
			HashMapLiteral<Character, String> map= new HashMapLiteral();
			map.set(keys, values);
			assertTrue( map.equals(checked) );
		}
		// 1. key:value, {key, value}, ... with static typing
		// class::function
		// class::new??
		
		// 2. simplify: don't [repeat] type generic type params
		// Even easier with: import static MapLiteral.keys;
		{
			Map<Character, String> map= new HashMap();
			MapLiteral.set( map, ()->keys, values );
			assertTrue( map.equals(checked) );
		}
		// CAN WE DO ()->{...}
		
		{
			HashMapLiteral<Character, String> map= new HashMapLiteral( keys, values );
			assertTrue( map.equals(checked) );
		}
		{
			HashMapLiteral<Character, String> map= new HashMapLiteral( ()->keys, values );
			assertTrue( map.equals(checked) );
		}
		//new HashMapLiteral().set(...).to(...);
		
		
		
		// Can't use a diamond operator for generics inference in function calls: aFunction( new HashMap<>);
//		array( new int[]{0, 1} );
//		supplied( ()->new String[]{"Hi"} );
	}
	
//	void array( int[] p ) {}
//	void supplied( Supplier<String[]> s ){}
}
