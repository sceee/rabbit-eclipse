/*
 * Copyright 2010 The Rabbit Eclipse Plug-in Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package rabbit.ui.tests.pages.mylyn;

import rabbit.ui.internal.pages.mylyn.MylynCategory;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.lang.reflect.Field;

/**
 * @see MylynCategory
 */
@SuppressWarnings("restriction")
public class MylynCategoryTest {

  @Test
  public void testFields() throws Exception {
    Field[] fields = MylynCategory.class.getDeclaredFields();
    for (Field field : fields) {
      if (field.isAccessible()) {
        MylynCategory category = (MylynCategory) field.get(null);
        assertNotNull(category.getText());
        assertNotNull(category.getImageDescriptor());
      }
    }
  }
}