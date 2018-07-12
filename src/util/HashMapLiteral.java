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

import java.util.HashMap;
import java.util.function.Supplier;

public class HashMapLiteral<K, V> extends HashMap<K, V> implements MapLiteral<K, V> {
	public HashMapLiteral() {}
	public HashMapLiteral(int initialCapacity) { super(initialCapacity); }
	public HashMapLiteral(int initialCapacity, float loadFactor) { super(initialCapacity, loadFactor); }
	
	// TODO V values[]
	// V firstValue, V... moreValues
	public HashMapLiteral( SettableKeys<K> keys, V firstValue, V... moreValues) {
		super( keys.length() );
		set( keys, firstValue, moreValues );
	}
	
	public HashMapLiteral( SettableKeys<K> keys, V values[]) {
		super( values.length );
		set( keys, values );
	}
	
	/*public HashMapLiteral( Supplier<SettableKeys<K>> keys, V... values) {
		this( keys.get(), values );
	}*/
}
