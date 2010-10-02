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

import static rabbit.ui.internal.util.DurationFormat.format;

import rabbit.data.access.model.LaunchConfigurationDescriptor;
import rabbit.data.access.model.LaunchDataDescriptor;
import rabbit.ui.internal.pages.Category;
import rabbit.ui.internal.pages.LaunchPageContentProvider;
import rabbit.ui.internal.pages.LaunchPageLabelProvider;
import rabbit.ui.internal.pages.ResourcePageLabelProvider;
import rabbit.ui.internal.util.Pair;
import rabbit.ui.internal.util.UndefinedLaunchConfigurationType;
import rabbit.ui.internal.util.UndefinedLaunchMode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchMode;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * @see LaunchPageLabelProvider
 */
public class LaunchPageLabelProviderTest {

  private static Shell shell;
  private static TreeViewer viewer;
  private static LaunchPageContentProvider contents;
  private static LaunchPageLabelProvider labels;

  // Objects to help testing:

  private static LocalDate date;
  private static ILaunchConfigurationType configType;
  private static ILaunchMode launchMode;
  private static Pair<String, String> launchData;
  private static LaunchConfigurationDescriptor configDescriptor;
  private static IProject project;
  private static IFolder folder;
  private static IFile file;

  private static TreeNode projNode;
  private static TreeNode dirNode;
  private static TreeNode fileNode;
  private static TreeNode dateNode;
  private static TreeNode confNode;
  private static TreeNode modeNode;
  private static TreeNode dataNode;

  @BeforeClass
  public static void beforeClass() {
    shell = new Shell(PlatformUI.getWorkbench().getDisplay());
    viewer = new TreeViewer(shell);
    contents = new LaunchPageContentProvider(viewer);
    labels = new LaunchPageLabelProvider(contents);

    viewer.setContentProvider(contents);
    viewer.setLabelProvider(labels);

    project = ResourcesPlugin.getWorkspace().getRoot().getProject("p");
    folder = project.getFolder("folder");
    file = project.getFile("file.txt");

    date = new LocalDate();

    ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
    launchMode = manager.getLaunchMode(ILaunchManager.RUN_MODE);

    String typeId = "org.eclipse.pde.ui.RuntimeWorkbench";
    configType = manager.getLaunchConfigurationType(typeId);

    configDescriptor = new LaunchConfigurationDescriptor("Name",
        launchMode.getIdentifier(), typeId);

    launchData = Pair.create(configDescriptor.getLaunchName(),
        configDescriptor.getLaunchTypeId());

    projNode = new TreeNode(project);
    dirNode = new TreeNode(folder);
    fileNode = new TreeNode(file);
    dateNode = new TreeNode(date);
    confNode = new TreeNode(configType);
    modeNode = new TreeNode(launchMode);
    dataNode = new TreeNode(launchData);

  }

  @AfterClass
  public static void afterClass() {
    labels.dispose();
    shell.dispose();
  }

  @Test(expected = NullPointerException.class)
  public void testConstructor_contentProviderNull() {
    new LaunchPageLabelProvider(null);
  }

  @Test
  public void testGetColumnImage() {
    assertNotNull(labels.getColumnImage(projNode, 0));
    assertNotNull(labels.getColumnImage(dirNode, 0));
    assertNotNull(labels.getColumnImage(fileNode, 0));
    assertNotNull(labels.getColumnImage(dateNode, 0));
    assertNotNull(labels.getColumnImage(modeNode, 0));
    assertNotNull(labels.getColumnImage(confNode, 0));
    assertNotNull(labels.getColumnImage(dataNode, 0));
  }

  @Test
  public void testGetColumnText_0_returnElementLabels() {
    ResourcePageLabelProvider resourceLabels = new ResourcePageLabelProvider();

    assertEquals(resourceLabels.getText(projNode), labels.getColumnText(
        projNode, 0));
    assertEquals(resourceLabels.getText(dirNode), labels.getColumnText(dirNode,
        0));
    assertEquals(resourceLabels.getText(fileNode), labels.getColumnText(
        fileNode, 0));
    assertEquals(resourceLabels.getText(dateNode), labels.getColumnText(
        dateNode, 0));
    assertEquals(launchData.getFirst(), labels.getColumnText(dataNode, 0));
    assertEquals(configType.getName(), labels.getColumnText(confNode, 0));
    assertEquals(launchMode.getLabel().replace("&", ""), labels.getColumnText(
        modeNode, 0));

    resourceLabels.dispose();
  }

  @Test
  public void testGetColumnText_1_returnsLaunchCounts() throws Exception {
    /*
     * Build a tree that look like the following, then check the value of each
     * node against the data:
     * 
     * Date +-- LaunchType +-- Launch +-- RunMode | +-- DebugMode
     */

    LaunchConfigurationDescriptor runMode = new LaunchConfigurationDescriptor(
        configDescriptor.getLaunchName(), ILaunchManager.RUN_MODE,
        configDescriptor.getLaunchTypeId());
    LaunchConfigurationDescriptor debugMode = new LaunchConfigurationDescriptor(
        configDescriptor.getLaunchName(), ILaunchManager.DEBUG_MODE,
        configDescriptor.getLaunchTypeId());

    LaunchDataDescriptor d1 = new LaunchDataDescriptor(date, runMode, 1,
        new Duration(1), Collections.<IPath> emptySet());
    LaunchDataDescriptor d2 = new LaunchDataDescriptor(date, debugMode, 19,
        new Duration(1200), Collections.<IPath> emptySet());

    viewer.setInput(Arrays.asList(d1, d2));
    contents.setSelectedCategories(Category.DATE, Category.LAUNCH_TYPE,
        Category.LAUNCH, Category.LAUNCH_MODE);

    TreeNode root = contents.getRoot();
    TreeNode dateNode = root.getChildren()[0];
    TreeNode launchTypeNode = dateNode.getChildren()[0];
    TreeNode launchNode = launchTypeNode.getChildren()[0];
    TreeNode runModeNode;
    TreeNode debugModeNode;
    runModeNode = launchNode.getChildren()[0];
    debugModeNode = launchNode.getChildren()[1];

    contents.setPaintCategory(Category.DATE);
    assertEquals(d1.getLaunchCount() + d2.getLaunchCount() + "",
        labels.getColumnText(dateNode, 1));
    assertNull(labels.getColumnText(runModeNode, 1));
    assertNull(labels.getColumnText(debugModeNode, 1));
    assertNull(labels.getColumnText(launchTypeNode, 1));
    assertNull(labels.getColumnText(launchNode, 1));

    contents.setPaintCategory(Category.LAUNCH_TYPE);
    assertNull(labels.getColumnText(dateNode, 1));
    assertNull(labels.getColumnText(runModeNode, 1));
    assertNull(labels.getColumnText(debugModeNode, 1));
    assertEquals(d1.getLaunchCount() + d2.getLaunchCount() + "",
        labels.getColumnText(launchTypeNode, 1));
    assertNull(labels.getColumnText(launchNode, 1));

    contents.setPaintCategory(Category.LAUNCH);
    assertNull(labels.getColumnText(dateNode, 1));
    assertNull(labels.getColumnText(runModeNode, 1));
    assertNull(labels.getColumnText(debugModeNode, 1));
    assertNull(labels.getColumnText(launchTypeNode, 1));
    assertEquals(d1.getLaunchCount() + d2.getLaunchCount() + "",
        labels.getColumnText(launchNode, 1));

    contents.setPaintCategory(Category.LAUNCH_MODE);
    assertNull(labels.getColumnText(dateNode, 1));
    assertEquals(d1.getLaunchCount() + "", labels.getColumnText(runModeNode, 1));
    assertEquals(d2.getLaunchCount() + "", labels.getColumnText(debugModeNode,
        1));
    assertNull(labels.getColumnText(launchTypeNode, 1));
    assertNull(labels.getColumnText(launchNode, 1));
  }

  @Test
  public void testGetColumnText_2_returnsNull() {
    assertNull(labels.getColumnText(dateNode, 2));
    assertNull(labels.getColumnText(modeNode, 2));
    assertNull(labels.getColumnText(confNode, 2));
    assertNull(labels.getColumnText(dataNode, 2));
  }

  @Test
  public void testGetColumnText_3_returnsDurations() {
    /*
     * Build a tree that look like the following, then check the value of each
     * node against the data:
     * 
     * Date +-- LaunchType +-- Launch +-- RunMode | +-- DebugMode
     */

    LaunchConfigurationDescriptor runMode = new LaunchConfigurationDescriptor(
        configDescriptor.getLaunchName(), ILaunchManager.RUN_MODE,
        configDescriptor.getLaunchTypeId());
    LaunchConfigurationDescriptor debugMode = new LaunchConfigurationDescriptor(
        configDescriptor.getLaunchName(), ILaunchManager.DEBUG_MODE,
        configDescriptor.getLaunchTypeId());

    LaunchDataDescriptor d1 = new LaunchDataDescriptor(date, runMode, 1,
        new Duration(10), Collections.<IPath> emptySet());
    LaunchDataDescriptor d2 = new LaunchDataDescriptor(date, debugMode, 11,
        new Duration(1200), Collections.<IPath> emptySet());

    viewer.setInput(Arrays.asList(d1, d2));
    contents.setSelectedCategories(Category.DATE, Category.LAUNCH_TYPE,
        Category.LAUNCH, Category.LAUNCH_MODE);

    TreeNode root = contents.getRoot();
    TreeNode dateNode = root.getChildren()[0];
    TreeNode launchTypeNode = dateNode.getChildren()[0];
    TreeNode launchNode = launchTypeNode.getChildren()[0];
    TreeNode runModeNode;
    TreeNode debugModeNode;
    runModeNode = launchNode.getChildren()[0];
    debugModeNode = launchNode.getChildren()[1];

    contents.setPaintCategory(Category.DATE);
    assertEquals(format(d1.getDuration().getMillis()
        + d2.getDuration().getMillis()), labels.getColumnText(dateNode, 3));
    assertNull(labels.getColumnText(runModeNode, 3));
    assertNull(labels.getColumnText(debugModeNode, 3));
    assertNull(labels.getColumnText(launchTypeNode, 3));
    assertNull(labels.getColumnText(launchNode, 3));

    contents.setPaintCategory(Category.LAUNCH_TYPE);
    assertNull(labels.getColumnText(dateNode, 3));
    assertNull(labels.getColumnText(runModeNode, 3));
    assertNull(labels.getColumnText(debugModeNode, 3));
    assertEquals(format(d1.getDuration().getMillis()
        + d2.getDuration().getMillis()),
        labels.getColumnText(launchTypeNode, 3));
    assertNull(labels.getColumnText(launchNode, 3));

    contents.setPaintCategory(Category.LAUNCH);
    assertNull(labels.getColumnText(dateNode, 3));
    assertNull(labels.getColumnText(runModeNode, 3));
    assertNull(labels.getColumnText(debugModeNode, 3));
    assertNull(labels.getColumnText(launchTypeNode, 3));
    assertEquals(format(d1.getDuration().getMillis()
        + d2.getDuration().getMillis()), labels.getColumnText(launchNode, 3));

    contents.setPaintCategory(Category.LAUNCH_MODE);
    assertNull(labels.getColumnText(dateNode, 1));
    assertEquals(format(d1.getDuration().getMillis()), labels.getColumnText(
        runModeNode, 3));
    assertEquals(format(d2.getDuration().getMillis()), labels.getColumnText(
        debugModeNode, 3));
    assertNull(labels.getColumnText(launchTypeNode, 3));
    assertNull(labels.getColumnText(launchNode, 3));
  }

  @Test
  public void testGetForeground() throws Exception {
    assertNotNull(labels.getForeground(new TreeNode(
        new UndefinedLaunchConfigurationType(""))));
    assertNotNull(labels.getForeground(new TreeNode(new UndefinedLaunchMode(""))));

    IProject proj = project.getWorkspace().getRoot().getProject(
        System.nanoTime() + "");
    assertNotNull(labels.getForeground(new TreeNode(proj)));
    proj.create(null);
    assertNull(labels.getForeground(new TreeNode(proj)));
  }

  @Test
  public void testGetImage() {
    assertEquals(labels.getImage(projNode), labels.getColumnImage(projNode, 0));
    assertEquals(labels.getImage(dirNode), labels.getColumnImage(dirNode, 0));
    assertEquals(labels.getImage(fileNode), labels.getColumnImage(fileNode, 0));
    assertEquals(labels.getImage(dateNode), labels.getColumnImage(dateNode, 0));
    assertEquals(labels.getImage(modeNode), labels.getColumnImage(modeNode, 0));
    assertEquals(labels.getImage(confNode), labels.getColumnImage(confNode, 0));
    assertEquals(labels.getImage(dataNode), labels.getColumnImage(dataNode, 0));
  }

  @Test
  public void testGetText() {
    assertEquals(labels.getText(projNode), labels.getColumnText(projNode, 0));
    assertEquals(labels.getText(dirNode), labels.getColumnText(dirNode, 0));
    assertEquals(labels.getText(fileNode), labels.getColumnText(fileNode, 0));
    assertEquals(labels.getText(dateNode), labels.getColumnText(dateNode, 0));
    assertEquals(labels.getText(modeNode), labels.getColumnText(modeNode, 0));
    assertEquals(labels.getText(confNode), labels.getColumnText(confNode, 0));
    assertEquals(labels.getText(dataNode), labels.getColumnText(dataNode, 0));
  }
}