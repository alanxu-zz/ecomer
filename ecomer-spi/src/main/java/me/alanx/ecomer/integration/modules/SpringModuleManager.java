package me.alanx.ecomer.integration.modules;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

public abstract class SpringModuleManager <M extends Module> implements ModuleManager<M>{

	private final Logger log = LoggerFactory.getLogger(SpringModuleManager.class);
	
	@Autowired(required = false)
	M[] modules;
	
	private final Map<String, SortedSet<M>> indexedModules = new HashMap<>();
	
	private final Comparator<M> comparator = new Comparator<M>(){
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public int compare(M o1, M o2) {
			if (o1 instanceof Ordered && o2 instanceof Ordered) {
				return ((Ordered)o1).getOrder() - ((Ordered)o2).getOrder();
			}
			
			if (o1 instanceof Comparable && o2 instanceof Comparable) {
				return ((Comparable)o1).compareTo(o2);
			}
			
			return 0;
		}};
	
	@Override
	public M findOne(String name) {
		SortedSet<M> mSet = this.indexedModules.get(name);
		
		if (mSet == null || mSet.size() == 0)
			return null;
		
		if (mSet.size() > 1) {
			throw new IllegalStateException("More than one module found. ");
		}
		
		return mSet.first();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public M[] findAll(String name) {
		if (this.modules != null) {
			Set<M> mSet = this.indexedModules.get(name);
			if (mSet != null) {
				return (M[])mSet.toArray();
			}
		}
		return null;
	}

	@Override
	public M[] findAll() {
		return this.modules != null ? this.modules : null;
	}



	/*
	 * Default init function after the properties are constructed
	 */
	private void init() {
		
		if (this.modules != null) {
			
			Arrays.sort(this.modules, this.comparator);
			
			for (M m : this.modules) {
				
				String n = m.getName();
				if (n == null) {
					n = "";
				}
				
				/*
				 *  Get modules by the name, if no module for the name registered,
				 *  create a new set for the module.
				 */
				SortedSet<M> mSet = this.indexedModules.get(n);
				if (mSet == null) {
					mSet = new TreeSet<M>(comparator);
					indexedModules.put(n, mSet);
				}
				
				mSet.add(m);
				
			}
		}
	}

}
