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
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public interface MapLiteral<K, V> extends Map<K, V> {
	public class SettableKeys<KK> {
		final MapLiteral<KK, ?> map;
		final KK keys[];
		
		public SettableKeys(MapLiteral<KK, ?> givenMap, KK... givenKeys) {
			map= givenMap!=null
				? givenMap
				: (MapLiteral<KK, ?>)newMap();
			keys=givenKeys;
		}
		SettableKeys(KK... givenKeys) { this( null, givenKeys); }
		
		public <VV, RESULT extends MapLiteral<KK,VV> > RESULT as(VV values[]) {
			//MapLiteral.setAs( map, this, values );
			if(true) throw new Error("TODO");
			return (RESULT)map;
		}
		
		public <VV, RESULT extends MapLiteral<KK,VV> > RESULT to(VV... values) {
			return as( values );
		}
		
		public <VV, RESULT extends MapLiteral<KK,VV> > RESULT allTo(VV oneValue) {
			throw new Error("TODO");
		}
		
		protected Map<?, ?> newMap() {
			return new HashMapLiteral<Object, Object>();
		}
	}
	
	default SettableKeys<K> set(K... givenKeys) {
		return new SettableKeys(this, givenKeys);
	}
	
	static <K, V, MAP extends Map<K,V>> MAP setAs( MAP map, SettableKeys<K> keys, V values[] ) {
		assert keys.keys.length==values.length;
		for( int i=0; i<keys.keys.length; i++ ) {
			map.put( keys.keys[i], values[i] );
		}
		return map;
	}
	
	static <K, V, MAP extends Map<K,V>> MAP set( MAP map, SettableKeys<K> keys, V... values ) {
		return setAs( map, keys, values );
	}
	
	static <K, V, MAP extends Map<K,V>> MAP set( MAP map, Supplier<SettableKeys<K>> keys, V... values ) {
		return set( map, keys.get(), values );
	}
	
	default <RESULT extends MapLiteral<K,V>> RESULT set( SettableKeys<K> keys, V... values ) {
		return (RESULT)set( this, keys, values );
	}
	
	default <RESULT extends MapLiteral<K,V>> RESULT set( Supplier<SettableKeys<K>> keys, V... values ) {
		return (RESULT)set( this, keys.get(), values );
	}
	
	static <KK> SettableKeys<KK> keys(KK... givenKeys) {
		return new SettableKeys<KK>(givenKeys);
	}	

}
