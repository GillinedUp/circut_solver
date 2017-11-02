package com.gillinedup.graph;

import java.awt.Point;

public class Vertex {
	final private String label;
	final private Point data;
	private int id;

	public Vertex(String id, Point data) {
		this.label = id;
		this.data = data;
	}

	public Vertex(int id) {
	    this.id = id;
	    this.label = Integer.toString(id);
	    data = null;
    }

	public String getLabel() {
		return label;
	}

	public int getId() {
	    return this.id;
    }

	@Override
	public String toString() {
		return "(" + id + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		return id == other.id;
	}

}