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
import java.util.function.Supplier;

public interface MapLiteral<K, V> extends Map<K, V> {
	public class SettableKeys<KK> extends Varargs<KK> {
		final MapLiteral<KK, ?> map;
		
		protected MapLiteral<KK, ?> mapOrNew(MapLiteral<KK, ?> givenMap) {
			return givenMap!=null
				? givenMap
				: (MapLiteral<KK, ?>)newMap();
		}
		
		public SettableKeys(MapLiteral<KK, ?> givenMap, KK firstKey, KK... moreKeys) {
			super( firstKey, moreKeys );
			map= mapOrNew(givenMap);
		}
		
		public SettableKeys(MapLiteral<KK, ?> givenMap, KK givenKeys[] ) {
			super( givenKeys );
			map= mapOrNew(givenMap);
		}
		SettableKeys(KK... givenKeys) { this( null, givenKeys); }
		
		public <VV, RESULT extends MapLiteral<KK,VV> > RESULT as(VV values[]) {
			return MapLiteral.set( (RESULT)map, this, values );
		}
		
		public <VV, RESULT extends MapLiteral<KK,VV> > RESULT to(VV... values) {
			return as( values );
		}
		
		public <VV, RESULT extends MapLiteral<KK,VV> > RESULT allTo(VV oneValue) {
			throw new Error("TODO");
		}
		
		protected MapLiteral<?, ?> newMap() {
			return new HashMapLiteral<Object, Object>();
		}
	}
	
	default SettableKeys<K> set(K givenKeys[]) {
		return new SettableKeys(this, givenKeys);
	}
	
	default SettableKeys<K> set(K firstKey, K... moreKeys) {
		return new SettableKeys(this, firstKey, moreKeys);
	}
	
	static <K, V, MAP extends Map<K,V>> MAP set( MAP map, SettableKeys<? extends K> keys, Varargs<V> values ) {//@TODO ? extends V
		assert keys.length()==values.length(); // Even though dualIterate() will also check, let's assert early
		Varargs.dualIterate(keys, values, (k,v)->map.put(k, v) );
		return map;
	}
	static <K, V, MAP extends Map<K,V>> MAP set( MAP map, SettableKeys<? extends K> keys, V values[] ) {
		return set( map, keys, new Varargs<V>(values) );
		/*int i=0;
		for( K key: keys ) {//@TODO DualIterate
			map.put( key, values[i++] );
		}
		return map;*/
	}
	
	static <K, V, MAP extends Map<K,V>> MAP set( MAP map, SettableKeys<? extends K> keys, V firstValue, V... moreValues ) {
		return set( map, keys, new Varargs<V>(firstValue, moreValues) );
	}
	
	/*static <K, V, MAP extends Map<K,V>> MAP set( MAP map, Supplier<SettableKeys<? extends K>> keys, V... values ) {
		return set( map, keys.get(), values );
	}*/
	
	default <RESULT extends MapLiteral<K,V>> RESULT set( SettableKeys<? extends K> keys, Varargs<V> values ) {
		return set( (RESULT)this, keys, values );
	}
	default <RESULT extends MapLiteral<K,V>> RESULT set( SettableKeys<? extends K> keys, V firstValue, V... moreValues ) {
		return set( (RESULT)this, keys, new Varargs<V>( firstValue, moreValues ) );
	}
	default <RESULT extends MapLiteral<K,V>> RESULT set( SettableKeys<? extends K> keys, V... values ) {
		return set( (RESULT)this, keys, new Varargs<V>( values ) );
	}
	
	/*default <RESULT extends MapLiteral<K,V>> RESULT set( Supplier<SettableKeys<? extends K>> keys, V... values ) {
		return (RESULT)set( this, keys.get(), values );
	}*/
	
	static <KK> SettableKeys<KK> keys(KK... givenKeys) {
		return new SettableKeys<KK>(givenKeys);
	}	

}
