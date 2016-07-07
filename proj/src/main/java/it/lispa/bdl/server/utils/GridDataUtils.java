package it.lispa.bdl.server.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;


/**
 * Class GridDataUtils.
 */
public class GridDataUtils {



	/**
	 * Match filter.
	 *
	 * @param c  c
	 * @param s  s
	 * @return true, se vero
	 * @throws Exception exception
	 */
	public static boolean matchFilter(Object c, FilterConfig s) throws Exception {

		String filterComparison = s.getComparison();
		String filterType = s.getType();
		String field = s.getField();
		String filterValue = s.getValue();
		

		String methodName = "get"+StringUtils.capitalize(field);
		java.lang.reflect.Method method = c.getClass().getMethod(methodName);
		
		if("string".equalsIgnoreCase(filterType)){
			String val = (String) method.invoke(c);
					
			if("contains".equals(filterComparison)){
				if (val!=null && val.toLowerCase().contains(filterValue.toLowerCase())) {
					return true;
				}
			}else if("gt".equals(filterComparison)){
				throw new Exception("filterComparison "+filterComparison+" for filterType "+filterType+" is not handled!");
			}else if("lt".equals(filterComparison)){
				throw new Exception("filterComparison "+filterComparison+" for filterType "+filterType+" is not handled!");
			}else if("eq".equals(filterComparison)){
				if (val!=null && val.toLowerCase().equalsIgnoreCase(filterValue)) {
					return true;
				}			
			}else if("on".equals(filterComparison)){
				throw new Exception("filterComparison "+filterComparison+" for filterType "+filterType+" is not handled!");
			}else if("after".equals(filterComparison)){
				throw new Exception("filterComparison "+filterComparison+" for filterType "+filterType+" is not handled!");
			}else if("before".equals(filterComparison)){
				throw new Exception("filterComparison "+filterComparison+" for filterType "+filterType+" is not handled!");
			}else{
				throw new Exception("filterComparison "+filterComparison+" for filterType "+filterType+" is not handled!");
			}
				
		}else if("list".equalsIgnoreCase(filterType)){
			String val = (String) method.invoke(c);
			String[] parts = filterValue.split("::");
			for(String part:parts){
				if(part.equals(val)){
					return true;
				}
			}	
		} else{
			throw new Exception("filterType "+filterType+" is not handled!");
		}	
		return false;
	}
	
	/**
	 * Compare.
	 *
	 * @param c1  c1
	 * @param c2  c2
	 * @param field  field
	 * @return int
	 * @throws Exception exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int compare(Object c1, Object c2, String field) throws Exception {
		
		String methodName = "get"+StringUtils.capitalize(field);
		java.lang.reflect.Method methodC1 = c1.getClass().getMethod(methodName);
		java.lang.reflect.Method methodC2 = c2.getClass().getMethod(methodName);
		
		Object val1Obj = methodC1.invoke(c1);		
		Object val2Obj = methodC2.invoke(c2);

	    if(val1Obj instanceof String){
	    	val1Obj = val1Obj.toString().toLowerCase();
	    }
	    if(val2Obj instanceof String){
	    	val2Obj = val2Obj.toString().toLowerCase();
	    }

		Comparable val1 = (Comparable) val1Obj;
		Comparable val2 = (Comparable) val2Obj;
		
	    if (val1 == null) {
	        return (val2 == null || val2.equals("")) ? 0 : 1;
	    }
	    if (val2 == null) {
	        return -1;
	    }
	    
		return val1.compareTo(val2);	
	}
	

	/**
	 * Grid apply filters sorting pagination.
	 *
	 * @param <T> the generic type
	 * @param items  items
	 * @param filters  filters
	 * @param orderField  order field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param orderFieldDefault  order field default
	 * @param orderDirDefault  order dir default
	 * @return paging load result bean
	 */
	public static <T> PagingLoadResultBean<T> gridApplyFiltersSortingPagination(List<T> items, List<FilterConfig> filters, String orderField,
			String orderDir, Integer myOffset, Integer myLimit,String orderFieldDefault,
			String orderDirDefault) {
		
		try {
			if (filters != null && !filters.isEmpty() && items != null) {
				itemCycle: for (int i = items.size() - 1; i >= 0; i--) {
					T item = items.get(i);
					for (FilterConfig s : filters) {
						try {
							if (!GridDataUtils.matchFilter(item, s)) {
								items.remove(i);
								continue itemCycle;
							}
						} catch (Exception e) {
							// do nothing
						}
					}
				}
			}
			
			if (orderField == null) {
				orderField = "nome";
			}
			if (orderDir == null) {
				orderDir = "ASC";
			}
			SortDir sortDir = SortDir.ASC;
			if (orderDir.equalsIgnoreCase("ASC")) {
				sortDir = SortDir.ASC;
			} else {
				sortDir = SortDir.DESC;
			}
			if (orderField != null && items != null) {
				final String sortFieldForCmp = orderField;
				Collections.sort(items, sortDir.comparator(new Comparator<T>() {
					@Override
					public int compare(T c1, T c2) {
						try {
							return GridDataUtils.compare(c1, c2, sortFieldForCmp);
						} catch (Exception e) {
							return 0;
						}
					}
				}));
			}

			// Create a sublist and add data to list according
			// to the limit and offset value of the config
			List<T> sublist = new ArrayList<T>();
			int start = myOffset;
			int limit = items.size();
			if(start>limit){
				// This means that we are asking for something that does not exists!
				// We force the dataset to begin at offset 0
				start = 0;
			}
			if (myLimit != null && myLimit > 0) {
				limit = Math.min(start + myLimit, limit);
			}
			for (int i = start; i < limit; i++) {
				sublist.add(items.get(i));
			}
			
			return new PagingLoadResultBean<T>(sublist, items.size(), start);
			
		} catch(NullPointerException npe) {
			
			List<T> emptyList = new ArrayList<T>();
			return new PagingLoadResultBean<T>(emptyList, emptyList.size(), myOffset);
		}
	}
}
