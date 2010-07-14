package org.jreform.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A map that returns a default value instead of null when a key is not found.
 * 
 * @author armandino (at) gmail.com
 */
public class DefaultValueMap<K, V> implements Map<K, V>
{
    private Map<K, V> map;
    private V defaultValue;
    
    public DefaultValueMap(Map<K, V> map, V defaultValue)
    {
        this.map = map;
        this.defaultValue = defaultValue;
    }
    
    public void clear()
    {
        map.clear();
    }

    public boolean containsKey(Object key)
    {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        return map.containsValue(value);
    }

    public Set<Entry<K, V>> entrySet()
    {
        return map.entrySet();
    }

    public boolean equals(Object o)
    {
        return map.equals(o);
    }

    public V get(Object key)
    {
        // NOTE: key.toString() is a workaround for jsp EL which allows
        //       passing arbitrary objects (e.g. Integer) as keys.
        
        V value = map.get(key.toString());
        return value == null ? defaultValue : value;
    }

    public int hashCode()
    {
        return map.hashCode();
    }

    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    public Set<K> keySet()
    {
        return map.keySet();
    }

    public V put(K key, V value)
    {
        return map.put(key, value);
    }

    public void putAll(Map<? extends K, ? extends V> t)
    {
        map.putAll(t);
    }

    public V remove(Object key)
    {
        return map.remove(key);
    }

    public int size()
    {
        return map.size();
    }

    public Collection<V> values()
    {
        return map.values();
    }
    
    @Override
    public String toString()
    {
        return map.toString();
    }
    
}
