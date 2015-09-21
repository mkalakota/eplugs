/**
 * 
 */
package com.mkalakota.eplugs.openin.handler;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * @author Mouli Kalakota
 */
public class CommandPromptHandler extends AbstractHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
		Object firstElement = selection.getFirstElement();
		if (null == firstElement) {
			return null;
		}
		String resourceLocation = null;
		boolean isFile = firstElement instanceof IFile;
		if (firstElement instanceof IResource) {
			IResource resource = (IResource) firstElement;
			resource.getFileExtension();
			resourceLocation = resource.getLocation().toOSString();
		} else if (firstElement instanceof IJavaElement) {
			IJavaElement javaElement = (IJavaElement) firstElement;
			resourceLocation = javaElement.getResource().getLocation().toOSString();
		}
		try {
			if (isFile) {
				resourceLocation = resourceLocation.replace(((IFile) firstElement).getName(), "");
			}
			Runtime.getRuntime().exec("cmd.exe /k start cd " + resourceLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
