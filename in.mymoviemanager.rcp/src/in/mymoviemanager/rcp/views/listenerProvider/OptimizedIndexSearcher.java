package in.mymoviemanager.rcp.views.listenerProvider;

import org.eclipse.swt.widgets.TableItem;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class OptimizedIndexSearcher {
	private int lastIndex = 0;
	
	public boolean isEven(TableItem item) {
		TableItem[] items = item.getParent().getItems();
		
		// 1. Search the next ten items
		for( int i = lastIndex; i < items.length && lastIndex + 10 > i; i++ ) {
			if( items[i] == item ) {
				lastIndex = i;
				return lastIndex % 2 == 0;
			}
		}
		
		// 2. Search the previous ten items
		for( int i = lastIndex; i < items.length && lastIndex - 10 > i; i-- ) {
			if( items[i] == item ) {
				lastIndex = i;
				return lastIndex % 2 == 0;
			}
		}
		
		// 3. Start from the beginning
		for( int i = 0; i < items.length; i++ ) {
			if( items[i] == item ) {
				lastIndex = i;
				return lastIndex % 2 == 0;
			}
		}
	
		return false;
	}
}