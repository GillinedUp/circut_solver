package com.gillinedup.graph;

public class Vertex {
	private int id;

	Vertex(int id) {
	    this.id = id;
    }

	int getId() {
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