package me.alanx.ecomer.bag;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import me.alanx.ecomer.json.JsonUtils;

public class BeanWrapperTestMain {
	
	public static class A {
		String name;
		String[] alias;
		public String[] getAlias() {
			return alias;
		}
		public void setAlias(String[] alias) {
			this.alias = alias;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public static class B {
		String name;
		String[] alias;
		A a;
		public String getName() {
			return name;
		}
		public String[] getAlias() {
			return alias;
		}
		public A getA() {
			return a;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setAlias(String[] alias) {
			this.alias = alias;
		}
		public void setA(A a) {
			this.a = a;
		}
	}
	
	public static void main(String[] args) {
		
		BeanWrapper bw = new BeanWrapperImpl(new B());
		bw.setAutoGrowNestedPaths(true);
		
		bw.setPropertyValue("name", "Haha");
		bw.setPropertyValue("a.name", "a.Haha");
		
		//JsonUtils.printJson(bw.getWrappedInstance());
		
		bw = new BeanWrapperImpl(new B());
		bw.setAutoGrowNestedPaths(true);
		PropertyEditor editor = new PropertyEditorSupport(){
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				A a = new A();
				a.setName(text);
				super.setValue(a);
			}

			@Override
			public String getAsText() {
				Object o = getValue();
				if (o == null) {
					return null;
				} else if (o instanceof A) {
					return ((A)o).getName();
				} else {
					throw new IllegalStateException("Value is not a A. ");
				}
			}
		};
		bw.registerCustomEditor(A.class, editor);
		
		bw.setPropertyValue("name", "Haha");
		bw.setPropertyValue("a", "a.Haha");
		bw.setPropertyValue("a.alias[0]", "x");
		bw.setPropertyValue("a.alias[1]", "y");
		bw.setPropertyValue("a.alias[2]", "z");
		JsonUtils.printJson(bw.getWrappedInstance());
	}
}
