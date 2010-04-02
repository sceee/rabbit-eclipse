/*
 * Copyright 2010 The Rabbit Eclipse Plug-in Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rabbit.core.internal.storage.xml;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;

import rabbit.core.internal.storage.xml.schema.events.EventGroupType;
import rabbit.core.internal.storage.xml.schema.events.EventListType;

/**
 * @see AbstractIdToValueAccessor
 */
public abstract class AbstractIdToValueAccessorTest<E, S extends EventGroupType>
		extends AbstractAccessorTest<Map<String, Long>, E, S> {

	private AbstractIdToValueAccessor<E, S> accessor = create();

	@Test
	public void testGetId() {
		String id = "2983jncjdkf";
		E type = createXmlType();
		setId(type, id);
		Assert.assertEquals(id, accessor.getId(type));
	}

	@Test
	public void testGetUsage() {
		long usage = 100193;
		E type = createXmlType();
		setUsage(type, usage);
		Assert.assertEquals(usage, accessor.getUsage(type));
	}

	@Test
	public void testGetXmlTypes() {
		int size = 5;
		S list = createListType();
		for (int i = 0; i < size; i++) {
			accessor.getXmlTypes(list).add(createXmlType());
		}
		Assert.assertEquals(size, accessor.getXmlTypes(list).size());
	}

	@Override
	protected void assertValues(Map<String, Long> data, EventListType events) {
		Map<String, Long> map = new HashMap<String, Long>();
		for (S list : accessor.getCategories(events)) {
			for (E e : accessor.getXmlTypes(list)) {
				Long usage = map.get(accessor.getId(e));
				if (usage == null) {
					map.put(accessor.getId(e), accessor.getUsage(e));
				} else {
					map.put(accessor.getId(e), accessor.getUsage(e) + usage);
				}
			}
		}

		assertEquals(map.size(), data.size());
		for (Entry<String, Long> entry : map.entrySet()) {
			assertEquals(entry.getValue(), data.get(entry.getKey()));
		}
	}

	@Override
	protected abstract AbstractIdToValueAccessor<E, S> create();
}