/*
 * Zed Attack Proxy (ZAP) and its related class files.
 * 
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 * 
 * Copyright 2013 The ZAP Development Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package org.zaproxy.zap.extension.scripts;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Custom renderer for {@link ScriptsListPanel} to set custom icons
 * and tooltips. If you want tooltips you have to enable them via:
 * <code>ToolTipManager.sharedInstance().registerComponent(tree);</code>
 */
public class ScriptsTreeCellRenderer extends DefaultTreeCellRenderer {
	
	private static final String RESOURCE_ROOT = "/org/zaproxy/zap/extension/scripts/resource/icons/";
	
	private static final ImageIcon ASCAN_ICON = 
			new ImageIcon(ScriptsTreeCellRenderer.class.getResource(RESOURCE_ROOT + "script-ascan.png"));
	private static final ImageIcon PSCAN_ICON = 
			new ImageIcon(ScriptsTreeCellRenderer.class.getResource(RESOURCE_ROOT + "script-pscan.png"));
	private static final ImageIcon INLINE_ICON = 
			new ImageIcon(ScriptsTreeCellRenderer.class.getResource(RESOURCE_ROOT + "script-proxy.png"));
	private static final ImageIcon STANDALONE_ICON =
			new ImageIcon(ScriptsTreeCellRenderer.class.getResource(RESOURCE_ROOT + "script-standalone.png"));
	private static final ImageIcon TARGETED_ICON = 
			new ImageIcon(ScriptsTreeCellRenderer.class.getResource(RESOURCE_ROOT + "script-target.png"));
	private static final ImageIcon LIBRARY_ICON =
			new ImageIcon(ScriptsTreeCellRenderer.class.getResource(RESOURCE_ROOT + "script-library.png"));

	private static final ImageIcon CROSS_OVERLAY_ICON = 
			new ImageIcon(ScriptsTreeCellRenderer.class.getResource(RESOURCE_ROOT + "cross-overlay.png"));
	private static final ImageIcon PENCIL_OVERLAY_ICON = 
			new ImageIcon(ScriptsTreeCellRenderer.class.getResource(RESOURCE_ROOT + "pencil-overlay.png"));
	private static final ImageIcon TICK_OVERLAY_ICON = 
			new ImageIcon(ScriptsTreeCellRenderer.class.getResource(RESOURCE_ROOT + "tick-overlay.png"));
	private static final ImageIcon WARNING_OVERLAY_ICON = 
			new ImageIcon(ScriptsTreeCellRenderer.class.getResource(RESOURCE_ROOT + "exclamation-overlay.png"));

	private ExtensionScripts extension = null;
	
	private static final long serialVersionUID = -4278691012245035225L;

	public ScriptsTreeCellRenderer(ExtensionScripts ext) {
		this.extension = ext;
	}

	/**
	 * Sets custom tree node logos.
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		ScriptNode node = null;
		Object userObject = null;
		if (value instanceof ScriptNode) {
			node = (ScriptNode) value;
			userObject = node.getUserObject();
		}
		
		if (node != null) {
			// folder / file icons with scope 'target' if relevant
			if (node.isRoot()) {
				setIcon(ExtensionScripts.ICON);
				
			} else if (userObject != null && userObject instanceof ScriptWrapper) {
				OverlayIcon icon;
				ScriptWrapper script = (ScriptWrapper) userObject;
				ScriptEngineWrapper engine = script.getEngine();
				if (script.getEngine() == null) {
					// Scripts loaded from the configs my have loaded before all of the engines
					script.setEngine(extension.getEngineWrapper(script.getEngineName()));
				}
				
				if (engine != null && engine.getIcon() != null) {
					icon = new OverlayIcon(engine.getIcon());
				} else {
					// Default to the blank script
					icon = new OverlayIcon(ExtensionScripts.ICON);
				}
				if (script.isChanged()) {
					icon.add(PENCIL_OVERLAY_ICON);
				}
				if (script.isError()) {
					icon.add(WARNING_OVERLAY_ICON);
				}
				if (! script.getType().equals(ScriptWrapper.Type.STANDALONE)) {
					if (script.isEnabled()) {
						icon.add(TICK_OVERLAY_ICON);
					} else {
						icon.add(CROSS_OVERLAY_ICON);
					}
				}
				setIcon(icon);
				
			} else if (node.getType() != null) {
				switch (node.getType()) {
				case ACTIVE:		setIcon(ASCAN_ICON); break;
				case PASSIVE:		setIcon(PSCAN_ICON); break;
				case INLINE:		setIcon(INLINE_ICON); break;
				case LIBRARY:		setIcon(LIBRARY_ICON); break;
				case STANDALONE:	setIcon(STANDALONE_ICON); break;
				case TARGETED:		setIcon(TARGETED_ICON); break;
				}
			} else {
			}
		}

		return this;
	}
}
