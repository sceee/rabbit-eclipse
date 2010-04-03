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
package rabbit.ui.internal.pages;

import rabbit.ui.internal.AbstractTreeContentProvider;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

import java.util.Collection;

public class ResourcePageContentProvider extends AbstractTreeContentProvider {

  private ResourcePage page;

  public ResourcePageContentProvider(ResourcePage parent) {
    this.page = parent;
  }

  @Override
  public Object[] getChildren(Object parent) {
    if (parent instanceof IProject) {
      return page.getResources((IProject) parent);
    } else if (parent instanceof IFolder) {
      return page.getFiles((IFolder) parent);
    } else {
      return EMPTY_ARRAY;
    }
  }

  public Object[] getElements(Object inputElement) {
    return ((Collection<?>) inputElement).toArray();
  }

  @Override
  public boolean hasChildren(Object element) {
    switch (page.getShowMode()) {
      case FILE:
        return element instanceof IContainer;
      case FOLDER:
        return element instanceof IProject;
      default: // Project
        return false;
    }
  }
}