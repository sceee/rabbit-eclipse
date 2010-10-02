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
package rabbit.data.internal.xml.access;

import rabbit.data.access.model.FileDataDescriptor;
import rabbit.data.internal.xml.DataStore;
import rabbit.data.internal.xml.merge.FileEventTypeMerger;
import rabbit.data.internal.xml.schema.events.FileEventListType;
import rabbit.data.internal.xml.schema.events.FileEventType;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * Test for {@link FileDataAccessor}
 */
public class FileDataAccessorTest
    extends
    AbstractDataNodeAccessorTest<FileDataDescriptor, FileEventType, FileEventListType> {

  @Override
  protected FileDataAccessor create() {
    return new FileDataAccessor(
        DataStore.FILE_STORE, new FileEventTypeMerger());
  }

  @Override
  protected FileEventListType createCategory() {
    return objectFactory.createFileEventListType();
  }

  @Override
  protected FileEventType createElement() {
    FileEventType type = objectFactory.createFileEventType();
    type.setDuration(1000);
    type.setFilePath("/ab");
    return type;
  }

  @Override
  protected List<FileEventType> getElements(FileEventListType list) {
    return list.getFileEvent();
  }

  @Override
  protected void setId(FileEventType type, String id) {
    type.setFilePath(id);
  }

  @Override
  protected void setValue(FileEventType type, long usage) {
    type.setDuration(usage);
  }

  @Override
  public void testCreateDataNode() throws Exception {
    LocalDate date = new LocalDate();
    FileEventType type = createElement();
    FileDataDescriptor des = accessor.createDataNode(date, type);

    assertEquals(date, des.getDate());
    assertEquals(type.getDuration(), des.getDuration().getMillis());
    assertEquals(type.getFilePath(), des.getFilePath().toString());
  }

  @Override
  protected boolean areEqual(FileDataDescriptor expected,
      FileDataDescriptor actual) {
    return expected.getDate().equals(actual.getDate())
        && expected.getDuration().equals(actual.getDuration())
        && expected.getFilePath().equals(actual.getFilePath());
  }

}
