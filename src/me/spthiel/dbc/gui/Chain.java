package me.spthiel.dbc.gui;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Chain<T> {
	
	private List<Consumer<T>> then = new LinkedList<>();
	
	public Chain<T> then(Consumer<T> consumer) {
		this.then.add(consumer);
		return this;
	}
	
	public void trigger(T thing) {
		then.forEach(consumer -> consumer.accept(thing));
	}
	
}
