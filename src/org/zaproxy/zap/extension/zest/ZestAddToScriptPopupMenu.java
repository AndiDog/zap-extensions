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
package org.zaproxy.zap.extension.zest;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.zest.core.v1.ZestConditional;
import org.parosproxy.paros.Constant;
import org.parosproxy.paros.extension.ExtensionPopupMenuItem;
import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.model.SiteNode;
import org.parosproxy.paros.view.View;
import org.zaproxy.zap.view.PopupMenuSiteNode;

public class ZestAddToScriptPopupMenu extends PopupMenuSiteNode {

	private static final long serialVersionUID = 2282358266003940700L;

	private ExtensionZest extension;
    private List<ExtensionPopupMenuItem> subMenus = new ArrayList<>();

	/**
	 * This method initializes 
	 * 
	 */
	public ZestAddToScriptPopupMenu(ExtensionZest extension) {
		super("AddToZestX", true);
		this.extension = extension;
	}
	
	/**/
    @Override
    public String getParentMenuName() {
    	return Constant.messages.getString("zest.addto.popup");
    }
    
    @Override
    public boolean isSubMenu() {
    	return true;
    }
    @Override
    public boolean isDummyItem () {
    	return true;
    }
	    
	@Override
	public void performAction(SiteNode sn) throws Exception {
		// Do nothing
	}

	@Override
    public void performActions (List<HistoryReference> hrefs) throws Exception {
	}

	@Override
	public boolean isEnableForInvoker(Invoker invoker) {
		return true;
	}

	@Override
    public boolean isEnabledForSiteNode (SiteNode sn) {
		reCreateSubMenu();
		
    	return false;
    }
	
    private void reCreateSubMenu() {
    	for (ExtensionPopupMenuItem menu : subMenus) {
			View.getSingleton().getPopupMenu().removeMenu(menu);
			
		}
		subMenus.clear();
		ZestNode selNode = extension.getSelectedScriptsNode();
		if (selNode != null) {
			if (selNode.getZestElement() instanceof ZestConditional) {
	        	ExtensionPopupMenuItem piicm = createPopupAddToScriptMenu(selNode);
	        	piicm.setMenuIndex(this.getMenuIndex());
				View.getSingleton().getPopupMenu().addMenu(piicm);
				this.subMenus.add(piicm);
			}
		}
		
		for (ZestNode node : extension.getScriptNodes()) {
        	ExtensionPopupMenuItem piicm = createPopupAddToScriptMenu(node);
        	piicm.setMenuIndex(this.getMenuIndex());
			View.getSingleton().getPopupMenu().addMenu(piicm);
			this.subMenus.add(piicm);
		}
        // Add the 'new zest' menu
        ExtensionPopupMenuItem piicm = createPopupAddToScriptMenu();
		View.getSingleton().getPopupMenu().addMenu(piicm);
		this.subMenus.add(piicm);
	}

    private ExtensionPopupMenuItem createPopupAddToScriptMenu() {
    	return new ZestAddToScriptMenu(extension);
	}

    private ExtensionPopupMenuItem createPopupAddToScriptMenu(ZestNode node) {
    	return new ZestAddToScriptMenu(extension, node);
	}

	@Override
    public boolean isSafe() {
    	return true;
    }
}