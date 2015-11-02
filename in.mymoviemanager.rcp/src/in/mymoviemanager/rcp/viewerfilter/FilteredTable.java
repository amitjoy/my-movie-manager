package in.mymoviemanager.rcp.viewerfilter;

import in.mymoviemanager.rcp.model.VideoFile;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.misc.StringMatcher;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.WorkbenchJob;

/**
 * This class is originally implemented in FilteredTree component. I modified it
 * to form a table viewer with ability to filter its elements. FilterMatcher is
 * used instead of PatternFilter because the implementation of PatternFilter is
 * tightly coupled with the FilteredTree. To operate correctly, the label
 * provider of the table viewer must implement {@link ILabelProvider} and return
 * a portion of text that is used for filter matching.
 * 
 * @since 3.2
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 */
@SuppressWarnings("restriction")
public class FilteredTable extends Composite {

	/**
	 * The FilterMatcher is the interface used to check filtering criteria.
	 */
	public static class FilterMatcher extends ViewerFilter {
		private StringMatcher fMatcher;
		private String searchString;

		public void setSearchText(String s) {
			// Search must be a substring of the existing value
			this.searchString = ".*" + s + ".*";
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			IBaseLabelProvider labelProvider = ((TableViewer) viewer)
					.getLabelProvider();
			if (!(labelProvider instanceof ILabelProvider)) {
				throw new RuntimeException(
						"Please implement ILabelProvider for your"
								+ " label provider in order for FilterTable to work properly.");
			}
			Object text = ((ILabelProvider) labelProvider).getText(element);
			VideoFile file = (VideoFile) element;
			if (searchString != null) {
				return file.getFileNameWithoutExtension().toLowerCase()
						.matches(searchString.toLowerCase());
			}
			return true;
		}

		public void setPattern(Object object) {
			if (object instanceof String) {
				fMatcher = new StringMatcher('*' + object.toString() + '*',
						true, false);
			} else {
				fMatcher = null;
			}
		}
	}

	/**
	 * The filter text widget to be used by this tree. This value may be
	 * <code>null</code> if there is no filter widget, or if the controls have
	 * not yet been created.
	 */
	protected Text filterText;

	/**
	 * The control representing the clear button for the filter text entry. This
	 * value may be <code>null</code> if no such button exists, or if the
	 * controls have not yet been created.
	 * <p>
	 * <strong>Note:</strong> As of 3.5, this is not used if the new look is
	 * chosen.
	 * </p>
	 */
	protected ToolBarManager filterToolBar;

	/**
	 * The control representing the clear button for the filter text entry. This
	 * value may be <code>null</code> if no such button exists, or if the
	 * controls have not yet been created.
	 * <p>
	 * <strong>Note:</strong> This is only used if the new look is chosen.
	 * </p>
	 * 
	 * @since 3.5
	 */
	protected Control clearButtonControl;

	/**
	 * The viewer for the filtered tree. This value should never be
	 * <code>null</code> after the widget creation methods are complete.
	 */
	protected TableViewer tableViewer;

	/**
	 * The Composite on which the filter controls are created. This is used to
	 * set the background color of the filter controls to match the surrounding
	 * controls.
	 */
	protected Composite filterComposite;

	/**
	 * The pattern filter for the tree. This value must not be <code>null</code>
	 * .
	 */
	private FilterMatcher patternFilter;

	/**
	 * The text to initially show in the filter text control.
	 */
	protected String initialText = ""; //$NON-NLS-1$

	/**
	 * The job used to refresh the tree.
	 */
	private Job refreshJob;

	/**
	 * The parent composite of the filtered tree.
	 * 
	 * @since 3.3
	 */
	protected Composite parent;

	/**
	 * Whether or not to show the filter controls (text and clear button). The
	 * default is to show these controls. This can be overridden by providing a
	 * setting in the product configuration file. The setting to add to not show
	 * these controls is:
	 * 
	 * org.eclipse.ui/SHOW_FILTERED_TEXTS=false
	 */
	protected boolean showFilterControls;

	/**
	 * @since 3.3
	 */
	protected Composite treeComposite;

	/**
	 * Tells whether to use the pre 3.5 or the new look.
	 * 
	 * @since 3.5
	 */
	private boolean useNewLook = false;

	/**
	 * Image descriptor for enabled clear button.
	 */
	private static final String CLEAR_ICON = "org.eclipse.ui.internal.dialogs.CLEAR_ICON"; //$NON-NLS-1$

	/**
	 * Image descriptor for disabled clear button.
	 */
	private static final String DISABLED_CLEAR_ICON = "org.eclipse.ui.internal.dialogs.DCLEAR_ICON"; //$NON-NLS-1$

	/**
	 * Get image descriptors for the clear button.
	 */
	static {
		ImageDescriptor descriptor = AbstractUIPlugin
				.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID,
						"$nl$/icons/full/etool16/clear_co.gif"); //$NON-NLS-1$
		if (descriptor != null) {
			JFaceResources.getImageRegistry().put(CLEAR_ICON, descriptor);
		}
		descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
				PlatformUI.PLUGIN_ID, "$nl$/icons/full/dtool16/clear_co.gif"); //$NON-NLS-1$
		if (descriptor != null) {
			JFaceResources.getImageRegistry().put(DISABLED_CLEAR_ICON,
					descriptor);
		}
	}

	/**
	 * Create a new instance of the receiver.
	 * 
	 * @param parent
	 *            the parent <code>Composite</code>
	 * @param treeStyle
	 *            the style bits for the <code>Table</code>
	 * @param filter
	 *            the filter to be used
	 * @param useNewLook
	 *            <code>true</code> if the new 3.5 look should be used
	 * @since 3.5
	 */
	public FilteredTable(Composite parent, int treeStyle, FilterMatcher filter,
			boolean useNewLook) {
		super(parent, SWT.NONE);
		this.parent = parent;
		this.useNewLook = useNewLook;
		init(treeStyle, filter);
	}

	/**
	 * Create a new instance of the receiver. Subclasses that wish to override
	 * the default creation behavior may use this constructor, but must ensure
	 * that the <code>init(composite, int, PatternFilter)</code> method is
	 * called in the overriding constructor.
	 * 
	 * @param parent
	 *            the parent <code>Composite</code>
	 * @param useNewLook
	 *            <code>true</code> if the new 3.5 look should be used
	 * @see #init(int, FilterMatcher)
	 * 
	 * @since 3.5
	 */
	protected FilteredTable(Composite parent, boolean useNewLook) {
		super(parent, SWT.NONE);
		this.parent = parent;
		this.useNewLook = useNewLook;
	}

	/**
	 * Create the filtered tree.
	 * 
	 * @param treeStyle
	 *            the style bits for the <code>Table</code>
	 * @param filter
	 *            the filter to be used
	 * 
	 * @since 3.3
	 */
	protected void init(int treeStyle, FilterMatcher filter) {
		patternFilter = filter;
		showFilterControls = PlatformUI.getPreferenceStore().getBoolean(
				IWorkbenchPreferenceConstants.SHOW_FILTERED_TEXTS);
		createControl(parent, treeStyle);
		createRefreshJob();
		setInitialText("Type Movie Name");
		setFont(parent.getFont());

	}

	/**
	 * Create the filtered tree's controls. Subclasses should override.
	 * 
	 * @param parent
	 * @param treeStyle
	 */
	protected void createControl(Composite parent, int treeStyle) {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		if (showFilterControls) {
			if (!useNewLook || useNativeSearchField(parent)) {
				filterComposite = new Composite(this, SWT.NONE);
			} else {
				filterComposite = new Composite(this, SWT.BORDER);
				filterComposite.setBackground(getDisplay().getSystemColor(
						SWT.COLOR_LIST_BACKGROUND));
			}
			GridLayout filterLayout = new GridLayout(2, false);
			filterLayout.marginHeight = 0;
			filterLayout.marginWidth = 0;
			filterComposite.setLayout(filterLayout);
			filterComposite.setFont(parent.getFont());

			createFilterControls(filterComposite);
			filterComposite.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING,
					true, false));
		}

		treeComposite = new Composite(this, SWT.NONE);
		GridLayout treeCompositeLayout = new GridLayout();
		treeCompositeLayout.marginHeight = 0;
		treeCompositeLayout.marginWidth = 0;
		treeComposite.setLayout(treeCompositeLayout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		treeComposite.setLayoutData(data);
		createTableControl(treeComposite, treeStyle);
	}

	private static Boolean useNativeSearchField;

	private static boolean useNativeSearchField(Composite composite) {
		if (useNativeSearchField == null) {
			useNativeSearchField = Boolean.FALSE;
			Text testText = null;
			try {
				testText = new Text(composite, SWT.SEARCH | SWT.ICON_CANCEL);
				useNativeSearchField = new Boolean(
						(testText.getStyle() & SWT.ICON_CANCEL) != 0);
			} finally {
				if (testText != null) {
					testText.dispose();
				}
			}

		}
		return useNativeSearchField.booleanValue();
	}

	/**
	 * Create the filter controls. By default, a text and corresponding tool bar
	 * button that clears the contents of the text is created. Subclasses may
	 * override.
	 * 
	 * @param parent
	 *            parent <code>Composite</code> of the filter controls
	 * @return the <code>Composite</code> that contains the filter controls
	 */
	protected Composite createFilterControls(Composite parent) {
		createFilterText(parent);
		if (useNewLook)
			createClearTextNew(parent);
		else
			createClearTextOld(parent);
		if (clearButtonControl != null) {
			// initially there is no text to clear
			clearButtonControl.setVisible(false);
		}
		if (filterToolBar != null) {
			filterToolBar.update(false);
			// initially there is no text to clear
			filterToolBar.getControl().setVisible(false);
		}
		return parent;
	}

	/**
	 * Creates and set up the tree and tree viewer. This method calls
	 * {@link #doCreateTableViewer(Composite, int)} to create the tree viewer.
	 * Subclasses should override {@link #doCreateTableViewer(Composite, int)}
	 * instead of overriding this method.
	 * 
	 * @param parent
	 *            parent <code>Composite</code>
	 * @param style
	 *            SWT style bits used to create the tree
	 * @return the tree
	 */
	protected Control createTableControl(Composite parent, int style) {
		tableViewer = doCreateTableViewer(parent, style);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		tableViewer.getControl().setLayoutData(data);
		tableViewer.getControl().addDisposeListener(new DisposeListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse
			 * .swt.events.DisposeEvent)
			 */
			public void widgetDisposed(DisposeEvent e) {
				refreshJob.cancel();
			}
		});

		tableViewer.addFilter(patternFilter);
		return tableViewer.getControl();
	}

	/**
	 * Creates the tree viewer. Subclasses may override.
	 * 
	 * @param parent
	 *            the parent composite
	 * @param style
	 *            SWT style bits used to create the tree viewer
	 * @return the tree viewer
	 * 
	 * @since 3.3
	 */
	protected TableViewer doCreateTableViewer(Composite parent, int style) {
		return new TableViewer(parent, style);
	}

	/**
	 * Return the first item in the tree that matches the filter pattern.
	 * 
	 * @param items
	 * @return the first matching TableItem
	 */
	private TableItem getFirstMatchingItem(TableItem[] items) {
		for (int i = 0; i < items.length; i++) {
			if (patternFilter.select(tableViewer, null, items[i].getData())) {
				return items[i];
			}
		}
		return null;
	}

	/**
	 * Create the refresh job for the receiver.
	 * 
	 */
	private void createRefreshJob() {
		refreshJob = doCreateRefreshJob();
		refreshJob.setSystem(true);
	}

	/**
	 * Creates a workbench job that will refresh the tree based on the current
	 * filter text. Subclasses may override.
	 * 
	 * @return a workbench job that can be scheduled to refresh the tree
	 * 
	 * @since 3.4
	 */
	protected WorkbenchJob doCreateRefreshJob() {
		return new WorkbenchJob("Refresh Filter") {//$NON-NLS-1$
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (tableViewer.getControl().isDisposed()) {
					return Status.CANCEL_STATUS;
				}

				String text = getFilterString();
				if (text == null) {
					return Status.OK_STATUS;
				}

				boolean initial = initialText != null
						&& initialText.equals(text);
				if (initial) {
					patternFilter.setPattern(null);
				} else {
					patternFilter.setPattern(text);
				}

				Control redrawFalseControl = treeComposite != null ? treeComposite
						: tableViewer.getControl();
				try {
					// don't want the user to see updates that will be made to
					// the tree
					// we are setting redraw(false) on the composite to avoid
					// dancing scrollbar
					redrawFalseControl.setRedraw(false);

					tableViewer.refresh(true);

					if (text.length() > 0 && !initial) {
						/*
						 * Expand elements one at a time. After each is
						 * expanded, check to see if the filter text has been
						 * modified. If it has, then cancel the refresh job so
						 * the user doesn't have to endure expansion of all the
						 * nodes.
						 */
						TableItem[] items = getViewer().getTable().getItems();
						boolean cancel = false;
						if (items.length > 0) {
							cancel = true;
						}

						// enabled toolbar - there is text to clear
						// and the list is currently being filtered
						updateToolbar(true);

						if (cancel) {
							return Status.CANCEL_STATUS;
						}
					} else {
						// disabled toolbar - there is no text to clear
						// and the list is currently not filtered
						updateToolbar(false);
					}
				} finally {
					// done updating the tree - set redraw back to true
					redrawFalseControl.setRedraw(true);
				}
				return Status.OK_STATUS;
			}
		};
	}

	protected void updateToolbar(boolean visible) {
		if (clearButtonControl != null) {
			clearButtonControl.setVisible(visible);
		}
		if (filterToolBar != null) {
			filterToolBar.getControl().setVisible(visible);
		}
	}

	/**
	 * Creates the filter text and adds listeners. This method calls
	 * {@link #doCreateFilterText(Composite)} to create the text control.
	 * Subclasses should override {@link #doCreateFilterText(Composite)} instead
	 * of overriding this method.
	 * 
	 * @param parent
	 *            <code>Composite</code> of the filter text
	 */
	protected void createFilterText(Composite parent) {
		filterText = doCreateFilterText(parent);
		filterText.getAccessible().addAccessibleListener(
				new AccessibleAdapter() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see
					 * org.eclipse.swt.accessibility.AccessibleListener#getName
					 * (org.eclipse.swt.accessibility.AccessibleEvent)
					 */
					public void getName(AccessibleEvent e) {
						String filterTextString = filterText.getText();
						if (filterTextString.length() == 0
								|| filterTextString.equals(initialText)) {
							e.result = initialText;
						} else {
							e.result = NLS
									.bind(WorkbenchMessages.FilteredTree_AccessibleListenerFiltered,
											new String[] {
													filterTextString,
													String.valueOf(getViewer()
															.getTable()
															.getItemCount()) });
						}
					}
				});

		filterText.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt
			 * .events.FocusEvent)
			 */
			public void focusGained(FocusEvent e) {
				if (!useNewLook) {
					/*
					 * Running in an asyncExec because the selectAll() does not
					 * appear to work when using mouse to give focus to text.
					 */
					Display display = filterText.getDisplay();
					display.asyncExec(new Runnable() {
						public void run() {
							if (!filterText.isDisposed()) {
								if (getInitialText().equals(
										filterText.getText().trim())) {
									filterText.selectAll();
								}
							}
						}
					});
					return;
				}
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt
			 * .events.FocusEvent)
			 */
			public void focusLost(FocusEvent e) {
				if (!useNewLook) {
					return;
				}
				if (filterText.getText().equals(initialText)) {
					setFilterText(""); //$NON-NLS-1$
					textChanged();
				}
			}
		});

		if (useNewLook) {
			filterText.addMouseListener(new MouseAdapter() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse
				 * .swt.events.MouseEvent)
				 */
				public void mouseDown(MouseEvent e) {
					if (filterText.getText().equals(initialText)) {
						// XXX: We cannot call clearText() due to
						// https://bugs.eclipse.org/bugs/show_bug.cgi?id=260664
						setFilterText(""); //$NON-NLS-1$
						textChanged();
					}
				}
			});
		}

		filterText.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				patternFilter.setSearchText(filterText.getText());
				tableViewer.refresh();
			}
		});

		// enter key set focus to tree
		filterText.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
					if (getViewer().getTable().getItemCount() == 0) {
						Display.getCurrent().beep();
					} else {
						// if the initial filter text hasn't changed, do not try
						// to match
						boolean hasFocus = getViewer().getTable().setFocus();
						boolean textChanged = !getInitialText().equals(
								filterText.getText().trim());
						if (hasFocus && textChanged
								&& filterText.getText().trim().length() > 0) {
							TableItem item = getFirstMatchingItem(getViewer()
									.getTable().getItems());
							if (item != null) {
								getViewer().getTable().setSelection(
										new TableItem[] { item });
								ISelection sel = getViewer().getSelection();
								getViewer().setSelection(sel, true);
							}
						}
					}
				}
			}
		});

		filterText.addModifyListener(new ModifyListener() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.
			 * swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				textChanged();
			}
		});

		// if we're using a field with built in cancel we need to listen for
		// default selection changes (which tell us the cancel button has been
		// pressed)
		if ((filterText.getStyle() & SWT.ICON_CANCEL) != 0) {
			filterText.addSelectionListener(new SelectionAdapter() {
				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.swt.events.SelectionAdapter#widgetDefaultSelected
				 * (org.eclipse.swt.events.SelectionEvent)
				 */
				public void widgetDefaultSelected(SelectionEvent e) {
					if (e.detail == SWT.ICON_CANCEL){
						clearText();
						// CANCEL BUTTON TO BE CHANGED
						patternFilter.setSearchText(filterText.getText());
						tableViewer.refresh();
					}
				}
			});
		}

		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		// if the text widget supported cancel then it will have it's own
		// integrated button. We can take all of the space.
		if ((filterText.getStyle() & SWT.ICON_CANCEL) != 0)
			gridData.horizontalSpan = 2;
		filterText.setLayoutData(gridData);
	}

	/**
	 * Creates the text control for entering the filter text. Subclasses may
	 * override.
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the text widget
	 * 
	 * @since 3.3
	 */
	protected Text doCreateFilterText(Composite parent) {
		if (!useNewLook || useNativeSearchField(parent)) {
			return new Text(parent, SWT.SINGLE | SWT.BORDER | SWT.SEARCH
					| SWT.ICON_SEARCH | SWT.ICON_CANCEL);
		}
		return new Text(parent, SWT.SINGLE);
	}

	/**
	 * Update the receiver after the text has changed.
	 */
	protected void textChanged() {
		// cancel currently running job first, to prevent unnecessary redraw
		refreshJob.cancel();
		refreshJob.schedule(getRefreshJobDelay());
	}

	/**
	 * Return the time delay that should be used when scheduling the filter
	 * refresh job. Subclasses may override.
	 * 
	 * @return a time delay in milliseconds before the job should run
	 * 
	 * @since 3.5
	 */
	protected long getRefreshJobDelay() {
		return 200;
	}

	/**
	 * Set the background for the widgets that support the filter text area.
	 * 
	 * @param background
	 *            background <code>Color</code> to set
	 */
	public void setBackground(Color background) {
		super.setBackground(background);
		if (filterComposite != null
				&& (!useNewLook || useNativeSearchField(filterComposite))) {
			filterComposite.setBackground(background);
		}
		if (filterToolBar != null && filterToolBar.getControl() != null) {
			filterToolBar.getControl().setBackground(background);
		}
	}

	/**
	 * Create the button that clears the text.
	 * 
	 * @param parent
	 *            parent <code>Composite</code> of toolbar button
	 */
	private void createClearTextOld(Composite parent) {
		// only create the button if the text widget doesn't support one
		// natively
		if ((filterText.getStyle() & SWT.ICON_CANCEL) == 0) {
			filterToolBar = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
			filterToolBar.createControl(parent);

			IAction clearTextAction = new Action("", IAction.AS_PUSH_BUTTON) {//$NON-NLS-1$
				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.action.Action#run()
				 */
				public void run() {
					clearText();
				}
			};

			clearTextAction
					.setToolTipText(WorkbenchMessages.FilteredTree_ClearToolTip);
			clearTextAction.setImageDescriptor(JFaceResources
					.getImageRegistry().getDescriptor(CLEAR_ICON));
			clearTextAction.setDisabledImageDescriptor(JFaceResources
					.getImageRegistry().getDescriptor(DISABLED_CLEAR_ICON));

			filterToolBar.add(clearTextAction);
		}
	}

	/**
	 * Create the button that clears the text.
	 * 
	 * @param parent
	 *            parent <code>Composite</code> of toolbar button
	 */
	private void createClearTextNew(Composite parent) {
		// only create the button if the text widget doesn't support one
		// natively
		if ((filterText.getStyle() & SWT.ICON_CANCEL) == 0) {
			final Image inactiveImage = JFaceResources.getImageRegistry()
					.getDescriptor(DISABLED_CLEAR_ICON).createImage();
			final Image activeImage = JFaceResources.getImageRegistry()
					.getDescriptor(CLEAR_ICON).createImage();
			final Image pressedImage = new Image(getDisplay(), activeImage,
					SWT.IMAGE_GRAY);

			final Label clearButton = new Label(parent, SWT.NONE);
			clearButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,
					false, false));
			clearButton.setImage(inactiveImage);
			clearButton.setBackground(parent.getDisplay().getSystemColor(
					SWT.COLOR_LIST_BACKGROUND));
			clearButton
					.setToolTipText(WorkbenchMessages.FilteredTree_ClearToolTip);
			clearButton.addMouseListener(new MouseAdapter() {
				private MouseMoveListener fMoveListener;

				public void mouseDown(MouseEvent e) {
					clearButton.setImage(pressedImage);
					fMoveListener = new MouseMoveListener() {
						private boolean fMouseInButton = true;

						public void mouseMove(MouseEvent e) {
							boolean mouseInButton = isMouseInButton(e);
							if (mouseInButton != fMouseInButton) {
								fMouseInButton = mouseInButton;
								clearButton
										.setImage(mouseInButton ? pressedImage
												: inactiveImage);
							}
						}
					};
					clearButton.addMouseMoveListener(fMoveListener);
				}

				public void mouseUp(MouseEvent e) {
					if (fMoveListener != null) {
						clearButton.removeMouseMoveListener(fMoveListener);
						fMoveListener = null;
						boolean mouseInButton = isMouseInButton(e);
						clearButton.setImage(mouseInButton ? activeImage
								: inactiveImage);
						if (mouseInButton) {
							clearText();
							filterText.setFocus();
						}
					}
				}

				private boolean isMouseInButton(MouseEvent e) {
					Point buttonSize = clearButton.getSize();
					return 0 <= e.x && e.x < buttonSize.x && 0 <= e.y
							&& e.y < buttonSize.y;
				}
			});
			clearButton.addMouseTrackListener(new MouseTrackListener() {
				public void mouseEnter(MouseEvent e) {
					clearButton.setImage(activeImage);
				}

				public void mouseExit(MouseEvent e) {
					clearButton.setImage(inactiveImage);
				}

				public void mouseHover(MouseEvent e) {
				}
			});
			clearButton.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					inactiveImage.dispose();
					activeImage.dispose();
					pressedImage.dispose();
				}
			});
			clearButton.getAccessible().addAccessibleListener(
					new AccessibleAdapter() {
						public void getName(AccessibleEvent e) {
							e.result = WorkbenchMessages.FilteredTree_AccessibleListenerClearButton;
						}
					});
			clearButton.getAccessible().addAccessibleControlListener(
					new AccessibleControlAdapter() {
						public void getRole(AccessibleControlEvent e) {
							e.detail = ACC.ROLE_PUSHBUTTON;
						}
					});
			this.clearButtonControl = clearButton;
		}
	}

	/**
	 * Clears the text in the filter text widget.
	 */
	protected void clearText() {
		setFilterText(""); //$NON-NLS-1$
		textChanged();
		tableViewer.refresh();
	}

	/**
	 * Set the text in the filter control.
	 * 
	 * @param string
	 */
	protected void setFilterText(String string) {
		if (filterText != null) {
			filterText.setText(string);
			selectAll();
		}
	}

	/**
	 * Returns the pattern filter used by this tree.
	 * 
	 * @return The pattern filter; never <code>null</code>.
	 */
	public final FilterMatcher getPatternFilter() {
		return patternFilter;
	}

	/**
	 * Get the tree viewer of the receiver.
	 * 
	 * @return the tree viewer
	 */
	public TableViewer getViewer() {
		return tableViewer;
	}

	/**
	 * Get the filter text for the receiver, if it was created. Otherwise return
	 * <code>null</code>.
	 * 
	 * @return the filter Text, or null if it was not created
	 */
	public Text getFilterControl() {
		return filterText;
	}

	/**
	 * Convenience method to return the text of the filter control. If the text
	 * widget is not created, then null is returned.
	 * 
	 * @return String in the text, or null if the text does not exist
	 */
	protected String getFilterString() {
		return filterText != null ? filterText.getText() : null;
	}

	/**
	 * Set the text that will be shown until the first focus. A default value is
	 * provided, so this method only need be called if overriding the default
	 * initial text is desired.
	 * 
	 * @param text
	 *            initial text to appear in text field
	 */
	public void setInitialText(String text) {
		initialText = text;
		if (useNewLook) {
			filterText.setMessage(text);
			if (filterText.isFocusControl()) {
				setFilterText(initialText);
				textChanged();
			} else {
				getDisplay().asyncExec(new Runnable() {
					public void run() {
						if (!filterText.isDisposed()
								&& filterText.isFocusControl()) {
							setFilterText(initialText);
							textChanged();
						}
					}
				});
			}
		} else {
			setFilterText(initialText);
			textChanged();
		}
	}

	/**
	 * Select all text in the filter text field.
	 * 
	 */
	protected void selectAll() {
		if (filterText != null) {
			filterText.selectAll();
		}
	}

	/**
	 * Get the initial text for the receiver.
	 * 
	 * @return String
	 */
	protected String getInitialText() {
		return initialText;
	}

	/**
	 * Return a bold font if the given element matches the given pattern.
	 * Clients can opt to call this method from a Viewer's label provider to get
	 * a bold font for which to highlight the given element in the tree.
	 * 
	 * @param element
	 *            element for which a match should be determined
	 * @param tree
	 *            FilteredTable in which the element resides
	 * @param filter
	 *            PatternFilter which determines a match
	 * 
	 * @return bold font
	 */
	public static Font getBoldFont(Object element, FilteredTable tree,
			FilterMatcher filter) {
		String filterText = tree.getFilterString();

		if (filterText == null) {
			return null;
		}

		// Do nothing if it's empty string
		String initialText = tree.getInitialText();
		if (!filterText.equals("") && !filterText.equals(initialText)) {//$NON-NLS-1$
			if (tree.getPatternFilter() != filter) {
				boolean initial = initialText != null
						&& initialText.equals(filterText);
				if (initial) {
					filter.setPattern(null);
				} else {
					filter.setPattern(filterText);
				}
			}
			if (filter.select(tree.getViewer(), null, element)) {
				return JFaceResources.getFontRegistry().getBold(
						JFaceResources.DIALOG_FONT);
			}
		}
		return null;
	}
}